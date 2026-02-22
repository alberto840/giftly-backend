package com.diplomado.diplomado.ubicacion;

import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UbicacionService {
    private static final Logger logger = LoggerFactory.getLogger(UbicacionService.class);
    private final UbicacionRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UbicacionService(UbicacionRepository ubicacionRepository, UsuarioRepository usuarioRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public UbicacionDto crearUbicacion(UbicacionDto ubicacionDto) {
        logger.info("Creando ubicacion: {}, {}", ubicacionDto.getLatitud(), ubicacionDto.getLongitud());

        UbicacionEntity ubicacionEntity = new UbicacionEntity();
        ubicacionEntity.setLatitud(ubicacionDto.getLatitud());
        ubicacionEntity.setLongitud(ubicacionDto.getLongitud());
        ubicacionEntity.setDetalle(ubicacionDto.getDetalle());

        if (ubicacionDto.getUsuarioId() != null) {
            UsuarioEntity usuario = usuarioRepository.findById(ubicacionDto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + ubicacionDto.getUsuarioId()));
            ubicacionEntity.setUsuario(usuario);
        }

        UbicacionEntity nuevaUbicacion = ubicacionRepository.save(ubicacionEntity);
        logger.info("Ubicacion creada con ID: {}", nuevaUbicacion.getId());

        return convertirUbicacionEntityADto(nuevaUbicacion);
    }

    public List<UbicacionDto> obtenerTodasLasUbicaciones() {
        logger.info("Obteniendo todas las ubicaciones");

        List<UbicacionEntity> ubicaciones = ubicacionRepository.findAll();
        return ubicaciones.stream()
                .map(this::convertirUbicacionEntityADto)
                .collect(Collectors.toList());
    }

    public UbicacionDto obtenerUbicacionPorId(Integer id) {
        logger.info("Obteniendo ubicacion con ID: {}", id);

        UbicacionEntity ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con ID: " + id));

        return convertirUbicacionEntityADto(ubicacion);
    }

    public UbicacionDto actualizarUbicacion(Integer id, UbicacionDto ubicacionDto) {
        logger.info("Actualizando ubicacion con ID: {}", id);

        UbicacionEntity ubicacionEntity = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con ID: " + id));

        ubicacionEntity.setLatitud(ubicacionDto.getLatitud());
        ubicacionEntity.setLongitud(ubicacionDto.getLongitud());
        ubicacionEntity.setDetalle(ubicacionDto.getDetalle());

        if (ubicacionDto.getUsuarioId() != null) {
            UsuarioEntity usuario = usuarioRepository.findById(ubicacionDto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + ubicacionDto.getUsuarioId()));
            ubicacionEntity.setUsuario(usuario);
        } else {
            ubicacionEntity.setUsuario(null);
        }

        UbicacionEntity ubicacionActualizada = ubicacionRepository.save(ubicacionEntity);

        return convertirUbicacionEntityADto(ubicacionActualizada);
    }

    public void eliminarUbicacion(Integer id) {
        logger.info("Eliminando ubicacion con ID: {}", id);

        if (!ubicacionRepository.existsById(id)) {
            throw new RuntimeException("Ubicacion no encontrada con ID: " + id);
        }

        ubicacionRepository.deleteById(id);
    }

    private UbicacionDto convertirUbicacionEntityADto(UbicacionEntity ubicacionEntity) {
        return new UbicacionDto(
                ubicacionEntity.getId(),
                ubicacionEntity.getLongitud(),
                ubicacionEntity.getLatitud(),
                ubicacionEntity.getDetalle(),
                ubicacionEntity.getUsuario() != null ? ubicacionEntity.getUsuario().getId() : null);
    }
}
