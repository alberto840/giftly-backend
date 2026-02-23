package com.diplomado.diplomado.detalle_pedido;

import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.pedido.PedidoRepository;
import com.diplomado.diplomado.ubicacion.UbicacionEntity;
import com.diplomado.diplomado.ubicacion.UbicacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoService {
    private static final Logger logger = LoggerFactory.getLogger(DetallePedidoService.class);
    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final UbicacionRepository ubicacionRepository;

    @Autowired
    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository, PedidoRepository pedidoRepository,
            UbicacionRepository ubicacionRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public DetallePedidoDto crearDetallePedido(DetallePedidoDto detallePedidoDto) {
        logger.info("Creando detalle pedido");

        PedidoEntity pedido = pedidoRepository.findById(detallePedidoDto.getPedidoId())
                .orElseThrow(
                        () -> new RuntimeException("Pedido no encontrado con ID: " + detallePedidoDto.getPedidoId()));

        UbicacionEntity ubicacion = ubicacionRepository.findById(detallePedidoDto.getUbicacionId())
                .orElseThrow(() -> new RuntimeException(
                        "Ubicacion no encontrada con ID: " + detallePedidoDto.getUbicacionId()));

        DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
        detallePedidoEntity.setMensaje(detallePedidoDto.getMensaje());
        detallePedidoEntity.setInstrucciones(detallePedidoDto.getInstrucciones());
        detallePedidoEntity.setReceptorEncarga(detallePedidoDto.getReceptorEncarga());
        detallePedidoEntity.setCelular1(detallePedidoDto.getCelular1());
        detallePedidoEntity.setCelular2(detallePedidoDto.getCelular2());
        detallePedidoEntity.setNombreObjetivo(detallePedidoDto.getNombreObjetivo());
        detallePedidoEntity.setNombreEmisor(detallePedidoDto.getNombreEmisor());
        detallePedidoEntity.setPedido(pedido);
        detallePedidoEntity.setUbicacion(ubicacion);

        DetallePedidoEntity nuevoDetallePedido = detallePedidoRepository.save(detallePedidoEntity);
        logger.info("Detalle pedido creado con ID: {}", nuevoDetallePedido.getId());

        return convertirDetallePedidoEntityADto(nuevoDetallePedido);
    }

    public List<DetallePedidoDto> obtenerTodosLosDetallePedidos() {
        logger.info("Obteniendo todos los detalle pedidos");

        List<DetallePedidoEntity> detallePedidos = detallePedidoRepository.findAll();
        return detallePedidos.stream()
                .map(this::convertirDetallePedidoEntityADto)
                .collect(Collectors.toList());
    }

    public DetallePedidoDto obtenerDetallePedidoPorId(Integer id) {
        logger.info("Obteniendo detalle pedido con ID: {}", id);

        DetallePedidoEntity detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle pedido no encontrado con ID: " + id));

        return convertirDetallePedidoEntityADto(detallePedido);
    }

    public DetallePedidoDto actualizarDetallePedido(Integer id, DetallePedidoDto detallePedidoDto) {
        logger.info("Actualizando detalle pedido con ID: {}", id);

        DetallePedidoEntity detallePedidoEntity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle pedido no encontrado con ID: " + id));

        PedidoEntity pedido = pedidoRepository.findById(detallePedidoDto.getPedidoId())
                .orElseThrow(
                        () -> new RuntimeException("Pedido no encontrado con ID: " + detallePedidoDto.getPedidoId()));

        UbicacionEntity ubicacion = ubicacionRepository.findById(detallePedidoDto.getUbicacionId())
                .orElseThrow(() -> new RuntimeException(
                        "Ubicacion no encontrada con ID: " + detallePedidoDto.getUbicacionId()));

        detallePedidoEntity.setMensaje(detallePedidoDto.getMensaje());
        detallePedidoEntity.setInstrucciones(detallePedidoDto.getInstrucciones());
        detallePedidoEntity.setReceptorEncarga(detallePedidoDto.getReceptorEncarga());
        detallePedidoEntity.setCelular1(detallePedidoDto.getCelular1());
        detallePedidoEntity.setCelular2(detallePedidoDto.getCelular2());
        detallePedidoEntity.setNombreObjetivo(detallePedidoDto.getNombreObjetivo());
        detallePedidoEntity.setNombreEmisor(detallePedidoDto.getNombreEmisor());
        detallePedidoEntity.setPedido(pedido);
        detallePedidoEntity.setUbicacion(ubicacion);

        DetallePedidoEntity detallePedidoActualizado = detallePedidoRepository.save(detallePedidoEntity);

        return convertirDetallePedidoEntityADto(detallePedidoActualizado);
    }

    public void eliminarDetallePedido(Integer id) {
        logger.info("Eliminando detalle pedido con ID: {}", id);

        if (!detallePedidoRepository.existsById(id)) {
            throw new RuntimeException("Detalle pedido no encontrado con ID: " + id);
        }

        detallePedidoRepository.deleteById(id);
    }

    private DetallePedidoDto convertirDetallePedidoEntityADto(DetallePedidoEntity detallePedidoEntity) {
        return new DetallePedidoDto(
                detallePedidoEntity.getId(),
                detallePedidoEntity.getMensaje(),
                detallePedidoEntity.getInstrucciones(),
                detallePedidoEntity.getReceptorEncarga(),
                detallePedidoEntity.getCelular1(),
                detallePedidoEntity.getCelular2(),
                detallePedidoEntity.getNombreObjetivo(),
                detallePedidoEntity.getNombreEmisor(),
                detallePedidoEntity.getPedido().getId(),
                detallePedidoEntity.getUbicacion().getId());
    }
}
