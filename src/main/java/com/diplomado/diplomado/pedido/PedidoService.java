package com.diplomado.diplomado.pedido;

import com.diplomado.diplomado.tienda_premios.TiendaPremioEntity;
import com.diplomado.diplomado.tienda_premios.TiendaPremioRepository;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
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

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository,
            TiendaPremioRepository tiendaPremioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tiendaPremioRepository = tiendaPremioRepository;
    }

    public PedidoDto crearPedido(PedidoDto pedidoDto) {
        logger.info("Creando pedido");

        UsuarioEntity usuario = usuarioRepository.findById(pedidoDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + pedidoDto.getUsuarioId()));

        TiendaPremioEntity tiendaPremio = tiendaPremioRepository.findById(pedidoDto.getTiendaPremioId())
                .orElseThrow(() -> new RuntimeException(
                        "Tienda premio no encontrada con ID: " + pedidoDto.getTiendaPremioId()));

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setFechaCreacion(pedidoDto.getFechaCreacion());
        pedidoEntity.setFechaEnvio(pedidoDto.getFechaEnvio());
        pedidoEntity.setTotal(pedidoDto.getTotal());
        pedidoEntity.setQrId(pedidoDto.getQrId());
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setTiendaPremio(tiendaPremio);

        PedidoEntity nuevoPedido = pedidoRepository.save(pedidoEntity);
        logger.info("Pedido creado con ID: {}", nuevoPedido.getId());

        return convertirPedidoEntityADto(nuevoPedido);
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

        TiendaPremioEntity tiendaPremio = tiendaPremioRepository.findById(pedidoDto.getTiendaPremioId())
                .orElseThrow(() -> new RuntimeException(
                        "Tienda premio no encontrada con ID: " + pedidoDto.getTiendaPremioId()));

        pedidoEntity.setFechaCreacion(pedidoDto.getFechaCreacion());
        pedidoEntity.setFechaEnvio(pedidoDto.getFechaEnvio());
        pedidoEntity.setTotal(pedidoDto.getTotal());
        pedidoEntity.setQrId(pedidoDto.getQrId());
        pedidoEntity.setUsuario(usuario);
        pedidoEntity.setTiendaPremio(tiendaPremio);

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
                new java.sql.Date(pedidoEntity.getFechaCreacion().getTime()),
                new java.sql.Date(pedidoEntity.getFechaEnvio().getTime()),
                pedidoEntity.getTotal(),
                pedidoEntity.getQrId(),
                pedidoEntity.getUsuario().getId(),
                pedidoEntity.getTiendaPremio().getId());
    }
}
