package com.diplomado.diplomado.pedido_producto;

import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.pedido.PedidoRepository;
import com.diplomado.diplomado.producto.ProductoEntity;
import com.diplomado.diplomado.producto.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoProductoService {
    private static final Logger logger = LoggerFactory.getLogger(PedidoProductoService.class);
    private final PedidoProductoRepository pedidoProductoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public PedidoProductoService(PedidoProductoRepository pedidoProductoRepository, PedidoRepository pedidoRepository,
            ProductoRepository productoRepository) {
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public PedidoProductoDto crearPedidoProducto(PedidoProductoDto pedidoProductoDto) {
        logger.info("Creando pedido producto");

        PedidoEntity pedido = pedidoRepository.findById(pedidoProductoDto.getPedidoId())
                .orElseThrow(
                        () -> new RuntimeException("Pedido no encontrado con ID: " + pedidoProductoDto.getPedidoId()));

        ProductoEntity producto = productoRepository.findById(pedidoProductoDto.getProductoId())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con ID: " + pedidoProductoDto.getProductoId()));

        PedidoProductoEntity pedidoProductoEntity = new PedidoProductoEntity();
        pedidoProductoEntity.setCantidad(pedidoProductoDto.getCantidad());
        pedidoProductoEntity.setPedido(pedido);
        pedidoProductoEntity.setProducto(producto);

        PedidoProductoEntity nuevoPedidoProducto = pedidoProductoRepository.save(pedidoProductoEntity);
        logger.info("Pedido producto creado con ID: {}", nuevoPedidoProducto.getId());

        return convertirPedidoProductoEntityADto(nuevoPedidoProducto);
    }

    public List<PedidoProductoDto> obtenerTodosLosPedidoProductos() {
        logger.info("Obteniendo todos los pedido productos");

        List<PedidoProductoEntity> pedidoProductos = pedidoProductoRepository.findAll();
        return pedidoProductos.stream()
                .map(this::convertirPedidoProductoEntityADto)
                .collect(Collectors.toList());
    }

    public PedidoProductoDto obtenerPedidoProductoPorId(Integer id) {
        logger.info("Obteniendo pedido producto con ID: {}", id);

        PedidoProductoEntity pedidoProducto = pedidoProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido producto no encontrado con ID: " + id));

        return convertirPedidoProductoEntityADto(pedidoProducto);
    }

    public PedidoProductoDto actualizarPedidoProducto(Integer id, PedidoProductoDto pedidoProductoDto) {
        logger.info("Actualizando pedido producto con ID: {}", id);

        PedidoProductoEntity pedidoProductoEntity = pedidoProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido producto no encontrado con ID: " + id));

        PedidoEntity pedido = pedidoRepository.findById(pedidoProductoDto.getPedidoId())
                .orElseThrow(
                        () -> new RuntimeException("Pedido no encontrado con ID: " + pedidoProductoDto.getPedidoId()));

        ProductoEntity producto = productoRepository.findById(pedidoProductoDto.getProductoId())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con ID: " + pedidoProductoDto.getProductoId()));

        pedidoProductoEntity.setCantidad(pedidoProductoDto.getCantidad());
        pedidoProductoEntity.setPedido(pedido);
        pedidoProductoEntity.setProducto(producto);

        PedidoProductoEntity pedidoProductoActualizado = pedidoProductoRepository.save(pedidoProductoEntity);

        return convertirPedidoProductoEntityADto(pedidoProductoActualizado);
    }

    public void eliminarPedidoProducto(Integer id) {
        logger.info("Eliminando pedido producto con ID: {}", id);

        if (!pedidoProductoRepository.existsById(id)) {
            throw new RuntimeException("Pedido producto no encontrado con ID: " + id);
        }

        pedidoProductoRepository.deleteById(id);
    }

    private PedidoProductoDto convertirPedidoProductoEntityADto(PedidoProductoEntity pedidoProductoEntity) {
        return new PedidoProductoDto(
                pedidoProductoEntity.getId(),
                pedidoProductoEntity.getCantidad(),
                pedidoProductoEntity.getPedido().getId(),
                pedidoProductoEntity.getProducto().getId());
    }
}
