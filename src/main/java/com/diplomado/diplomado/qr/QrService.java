package com.diplomado.diplomado.qr;

import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.pedido.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QrService {
    private static final Logger logger = LoggerFactory.getLogger(QrService.class);
    private final QrRepository qrRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public QrService(QrRepository qrRepository, PedidoRepository pedidoRepository) {
        this.qrRepository = qrRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public QrDto crearQr(QrDto qrDto) {
        logger.info("Creando qr");

        PedidoEntity pedido = pedidoRepository.findById(qrDto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + qrDto.getPedidoId()));

        QrEntity qrEntity = new QrEntity();
        qrEntity.setFechaCreacion(qrDto.getFechaCreacion());
        qrEntity.setFechaExpiracion(qrDto.getFechaExpiracion());
        qrEntity.setPedido(pedido);

        QrEntity nuevoQr = qrRepository.save(qrEntity);
        logger.info("Qr creado con ID: {}", nuevoQr.getId());

        return convertirQrEntityADto(nuevoQr);
    }

    public List<QrDto> obtenerTodosLosQrs() {
        logger.info("Obteniendo todos los qrs");

        List<QrEntity> qrs = qrRepository.findAll();
        return qrs.stream()
                .map(this::convertirQrEntityADto)
                .collect(Collectors.toList());
    }

    public QrDto obtenerQrPorId(Integer id) {
        logger.info("Obteniendo qr con ID: {}", id);

        QrEntity qr = qrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Qr no encontrado con ID: " + id));

        return convertirQrEntityADto(qr);
    }

    public QrDto actualizarQr(Integer id, QrDto qrDto) {
        logger.info("Actualizando qr con ID: {}", id);

        QrEntity qrEntity = qrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Qr no encontrado con ID: " + id));

        PedidoEntity pedido = pedidoRepository.findById(qrDto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + qrDto.getPedidoId()));

        qrEntity.setFechaCreacion(qrDto.getFechaCreacion());
        qrEntity.setFechaExpiracion(qrDto.getFechaExpiracion());
        qrEntity.setPedido(pedido);

        QrEntity qrActualizado = qrRepository.save(qrEntity);

        return convertirQrEntityADto(qrActualizado);
    }

    public void eliminarQr(Integer id) {
        logger.info("Eliminando qr con ID: {}", id);

        if (!qrRepository.existsById(id)) {
            throw new RuntimeException("Qr no encontrado con ID: " + id);
        }

        qrRepository.deleteById(id);
    }

    private QrDto convertirQrEntityADto(QrEntity qrEntity) {
        return new QrDto(
                qrEntity.getId(),
                new java.sql.Date(qrEntity.getFechaCreacion().getTime()),
                new java.sql.Date(qrEntity.getFechaExpiracion().getTime()),
                qrEntity.getPedido().getId());
    }
}
