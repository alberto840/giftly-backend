package com.diplomado.diplomado.pedido;

import com.diplomado.diplomado.tienda_premios.TiendaPremioEntity;
import com.diplomado.diplomado.tienda_premios.TiendaPremioRepository;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import com.diplomado.diplomado.detalle_pedido.DetallePedidoEntity;
import com.diplomado.diplomado.detalle_pedido.DetallePedidoRepository;
import com.diplomado.diplomado.detalle_pedido.DetallePedidoDto;
import com.diplomado.diplomado.pedido_producto.PedidoProductoDto;
import com.diplomado.diplomado.pedido_producto.PedidoProductoEntity;
import com.diplomado.diplomado.pedido_producto.PedidoProductoRepository;
import com.diplomado.diplomado.producto.ProductoEntity;
import com.diplomado.diplomado.producto.ProductoRepository;
import com.diplomado.diplomado.ubicacion.UbicacionEntity;
import com.diplomado.diplomado.ubicacion.UbicacionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TiendaPremioRepository tiendaPremioRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;
    private final ProductoRepository productoRepository;
    private final UbicacionRepository ubicacionRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository,
            TiendaPremioRepository tiendaPremioRepository,
            DetallePedidoRepository detallePedidoRepository,
            PedidoProductoRepository pedidoProductoRepository,
            ProductoRepository productoRepository,
            UbicacionRepository ubicacionRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tiendaPremioRepository = tiendaPremioRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.productoRepository = productoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public PedidoDto crearPedido(PedidoDto pedidoDto) {
        logger.info("Creando pedido");

        UsuarioEntity usuario = usuarioRepository.findById(pedidoDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + pedidoDto.getUsuarioId()));

        TiendaPremioEntity tiendaPremio = null;
        if (pedidoDto.getTiendaPremioId() != null) {
            tiendaPremio = tiendaPremioRepository.findById(pedidoDto.getTiendaPremioId())
                    .orElseThrow(() -> new RuntimeException(
                            "Tienda premio no encontrada con ID: " + pedidoDto.getTiendaPremioId()));
        }

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setFechaCreacion(pedidoDto.getFechaCreacion());
        pedidoEntity.setFechaEnvio(pedidoDto.getFechaEnvio());
        pedidoEntity.setTotal(pedidoDto.getTotal());
        pedidoEntity.setQrId(pedidoDto.getQrId());
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setTiendaPremio(tiendaPremio);
        pedidoEntity.setStatus(pedidoDto.getStatus());

        PedidoEntity nuevoPedido = pedidoRepository.save(pedidoEntity);
        logger.info("Pedido creado con ID: {}", nuevoPedido.getId());

        return convertirPedidoEntityADto(nuevoPedido);
    }

    @Transactional
    public PedidoRegistroRequestDto registrarPedidoCompleto(PedidoRegistroRequestDto request) {
        logger.info("Registrando pedido completo transaccionalmente");

        UsuarioEntity usuario = usuarioRepository.findById(request.getPedido().getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getPedido().getUsuarioId()));

        TiendaPremioEntity tiendaPremio = null;
        if (request.getPedido().getTiendaPremioId() != null) {
            tiendaPremio = tiendaPremioRepository.findById(request.getPedido().getTiendaPremioId())
                    .orElseThrow(() -> new RuntimeException("Tienda premio no encontrada con ID: " + request.getPedido().getTiendaPremioId()));
        }

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setFechaCreacion(request.getPedido().getFechaCreacion());
        pedidoEntity.setFechaEnvio(request.getPedido().getFechaEnvio());
        pedidoEntity.setTotal(request.getPedido().getTotal());
        pedidoEntity.setQrId(request.getPedido().getQrId());
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setTiendaPremio(tiendaPremio);
        pedidoEntity.setStatus(request.getPedido().getStatus());

        PedidoEntity pedidoGuardado = pedidoRepository.save(pedidoEntity);
        request.getPedido().setId(pedidoGuardado.getId());

        if (request.getDetallePedido() != null) {
            DetallePedidoEntity detalleEntity = new DetallePedidoEntity();
            detalleEntity.setMensaje(request.getDetallePedido().getMensaje());
            detalleEntity.setInstrucciones(request.getDetallePedido().getInstrucciones());
            detalleEntity.setReceptorEncarga(request.getDetallePedido().getReceptorEncarga());
            detalleEntity.setCelular1(request.getDetallePedido().getCelular1());
            detalleEntity.setCelular2(request.getDetallePedido().getCelular2());
            detalleEntity.setNombreObjetivo(request.getDetallePedido().getNombreObjetivo());
            detalleEntity.setNombreEmisor(request.getDetallePedido().getNombreEmisor());
            detalleEntity.setPedido(pedidoGuardado);
            
            if (request.getDetallePedido().getUbicacionId() != null) {
                UbicacionEntity ubicacion = ubicacionRepository.findById(request.getDetallePedido().getUbicacionId())
                        .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con ID: " + request.getDetallePedido().getUbicacionId()));
                detalleEntity.setUbicacion(ubicacion);
            }
            
            DetallePedidoEntity detalleGuardado = detallePedidoRepository.save(detalleEntity);
            request.getDetallePedido().setId(detalleGuardado.getId());
            request.getDetallePedido().setPedidoId(pedidoGuardado.getId());
        }

        if (request.getProductos() != null && !request.getProductos().isEmpty()) {
            for (PedidoProductoDto prodDto : request.getProductos()) {
                ProductoEntity producto = productoRepository.findById(prodDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + prodDto.getProductoId()));
                
                PedidoProductoEntity pedidoProducto = new PedidoProductoEntity();
                pedidoProducto.setPedido(pedidoGuardado);
                pedidoProducto.setProducto(producto);
                pedidoProducto.setCantidad(prodDto.getCantidad());
                
                PedidoProductoEntity prodGuardado = pedidoProductoRepository.save(pedidoProducto);
                prodDto.setId(prodGuardado.getId());
                prodDto.setPedidoId(pedidoGuardado.getId());
            }
        }

        logger.info("Pedido completo registrado con ID: {}", pedidoGuardado.getId());
        return request;
    }

    public PedidoRegistroRequestDto obtenerPedidoCompletoPorId(Integer id) {
        logger.info("Obteniendo pedido completo con ID: {}", id);
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        return convertirPedidoEntityARequestDto(pedido);
    }

    public List<PedidoRegistroRequestDto> obtenerTodosLosPedidosCompletos() {
        logger.info("Obteniendo todos los pedidos completos");
        return pedidoRepository.findAll().stream()
                .map(this::convertirPedidoEntityARequestDto)
                .collect(Collectors.toList());
    }

    private PedidoRegistroRequestDto convertirPedidoEntityARequestDto(PedidoEntity entity) {
        PedidoDto pedidoDto = convertirPedidoEntityADto(entity);
        
        DetallePedidoDto detalleDto = null;
        if (entity.getDetallePedido() != null) {
            detalleDto = new DetallePedidoDto(
                    entity.getDetallePedido().getId(),
                    entity.getDetallePedido().getMensaje(),
                    entity.getDetallePedido().getInstrucciones(),
                    entity.getDetallePedido().getReceptorEncarga(),
                    entity.getDetallePedido().getCelular1(),
                    entity.getDetallePedido().getCelular2(),
                    entity.getDetallePedido().getNombreObjetivo(),
                    entity.getDetallePedido().getNombreEmisor(),
                    entity.getId(),
                    entity.getDetallePedido().getUbicacion() != null ? entity.getDetallePedido().getUbicacion().getId() : null
            );
        }

        List<PedidoProductoDto> productosDto = null;
        if (entity.getPedidoProductos() != null) {
            productosDto = entity.getPedidoProductos().stream()
                    .map(pp -> new PedidoProductoDto(
                            pp.getId(),
                            pp.getCantidad(),
                            entity.getId(),
                            pp.getProducto().getId()
                    ))
                    .collect(Collectors.toList());
        }

        return new PedidoRegistroRequestDto(pedidoDto, detalleDto, productosDto);
    }

    public List<PedidoDto> obtenerTodosLosPedidos() {
        logger.info("Obteniendo todos los pedidos");

        List<PedidoEntity> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(this::convertirPedidoEntityADto)
                .collect(Collectors.toList());
    }

    public PedidoDto obtenerPedidoPorId(Integer id) {
        logger.info("Obteniendo pedido con ID: {}", id);

        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        return convertirPedidoEntityADto(pedido);
    }

    public PedidoDto actualizarPedido(Integer id, PedidoDto pedidoDto) {
        logger.info("Actualizando pedido con ID: {}", id);

        PedidoEntity pedidoEntity = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        UsuarioEntity usuario = usuarioRepository.findById(pedidoDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + pedidoDto.getUsuarioId()));

        TiendaPremioEntity tiendaPremio = null;
        if (pedidoDto.getTiendaPremioId() != null) {
            tiendaPremio = tiendaPremioRepository.findById(pedidoDto.getTiendaPremioId())
                    .orElseThrow(() -> new RuntimeException(
                            "Tienda premio no encontrada con ID: " + pedidoDto.getTiendaPremioId()));
        }

        pedidoEntity.setFechaCreacion(pedidoDto.getFechaCreacion());
        pedidoEntity.setFechaEnvio(pedidoDto.getFechaEnvio());
        pedidoEntity.setTotal(pedidoDto.getTotal());
        pedidoEntity.setQrId(pedidoDto.getQrId());
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setTiendaPremio(tiendaPremio);
        pedidoEntity.setStatus(pedidoDto.getStatus());

        PedidoEntity pedidoActualizado = pedidoRepository.save(pedidoEntity);

        return convertirPedidoEntityADto(pedidoActualizado);
    }

    public PedidoDto cambiarEstado(Integer id, String tipo) {
        logger.info("Cambiando estado del pedido con ID: {} a {}", id, tipo);

        PedidoEntity pedidoEntity = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        pedidoEntity.setStatus(tipo);
        PedidoEntity pedidoActualizado = pedidoRepository.save(pedidoEntity);

        return convertirPedidoEntityADto(pedidoActualizado);
    }

    public void eliminarPedido(Integer id) {
        logger.info("Eliminando pedido con ID: {}", id);

        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }

        pedidoRepository.deleteById(id);
    }

    private PedidoDto convertirPedidoEntityADto(PedidoEntity pedidoEntity) {
        return new PedidoDto(
                pedidoEntity.getId(),
                pedidoEntity.getFechaCreacion() != null ? new java.sql.Date(pedidoEntity.getFechaCreacion().getTime()) : null,
                pedidoEntity.getFechaEnvio() != null ? new java.sql.Date(pedidoEntity.getFechaEnvio().getTime()) : null,
                pedidoEntity.getTotal(),
                pedidoEntity.getQrId(),
                pedidoEntity.getUsuario() != null ? pedidoEntity.getUsuario().getId() : null,
                pedidoEntity.getTiendaPremio() != null ? pedidoEntity.getTiendaPremio().getId() : null,
                pedidoEntity.getStatus());
    }
}
