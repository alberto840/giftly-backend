package com.diplomado.diplomado.misiones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MisionService {
    private static final Logger logger = LoggerFactory.getLogger(MisionService.class);
    private final MisionRepository misionRepository;

    @Autowired
    public MisionService(MisionRepository misionRepository) {
        this.misionRepository = misionRepository;
    }

    public MisionDto crearMision(MisionDto misionDto) {
        logger.info("Creando mision: {}", misionDto.getTitulo());

        MisionEntity misionEntity = new MisionEntity();
        misionEntity.setObjetivo(misionDto.getObjetivo());
        misionEntity.setTitulo(misionDto.getTitulo());
        misionEntity.setDescripcion(misionDto.getDescripcion());
        misionEntity.setFechaCreacion(misionDto.getFechaCreacion());
        misionEntity.setFechaFinal(misionDto.getFechaFinal());
        misionEntity.setPremioPuntos(misionDto.getPremioPunt());

        MisionEntity nuevaMision = misionRepository.save(misionEntity);
        logger.info("Mision creada con ID: {}", nuevaMision.getId());

        return convertirMisionEntityADto(nuevaMision);
    }

    public List<MisionDto> obtenerTodasLasMisiones() {
        logger.info("Obteniendo todas las misiones");

        List<MisionEntity> misiones = misionRepository.findAll();
        return misiones.stream()
                .map(this::convertirMisionEntityADto)
                .collect(Collectors.toList());
    }

    public MisionDto obtenerMisionPorId(Integer id) {
        logger.info("Obteniendo mision con ID: {}", id);

        MisionEntity mision = misionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mision no encontrada con ID: " + id));

        return convertirMisionEntityADto(mision);
    }

    public MisionDto actualizarMision(Integer id, MisionDto misionDto) {
        logger.info("Actualizando mision con ID: {}", id);

        MisionEntity misionEntity = misionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mision no encontrada con ID: " + id));

        misionEntity.setObjetivo(misionDto.getObjetivo());
        misionEntity.setTitulo(misionDto.getTitulo());
        misionEntity.setDescripcion(misionDto.getDescripcion());
        misionEntity.setFechaCreacion(misionDto.getFechaCreacion());
        misionEntity.setFechaFinal(misionDto.getFechaFinal());
        misionEntity.setPremioPuntos(misionDto.getPremioPunt());

        MisionEntity misionActualizada = misionRepository.save(misionEntity);

        return convertirMisionEntityADto(misionActualizada);
    }

    public void eliminarMision(Integer id) {
        logger.info("Eliminando mision con ID: {}", id);

        if (!misionRepository.existsById(id)) {
            throw new RuntimeException("Mision no encontrada con ID: " + id);
        }

        misionRepository.deleteById(id);
    }

    private MisionDto convertirMisionEntityADto(MisionEntity misionEntity) {
        return new MisionDto(
                misionEntity.getId(),
                misionEntity.getObjetivo(),
                misionEntity.getTitulo(),
                misionEntity.getDescripcion(),
                new java.sql.Date(misionEntity.getFechaCreacion().getTime()),
                new java.sql.Date(misionEntity.getFechaFinal().getTime()),
                misionEntity.getPremioPuntos());
    }
}
