package com.diplomado.diplomado.niveles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NivelesService {
    private static final Logger logger = LoggerFactory.getLogger(NivelesService.class);
    private final NivelesRepository nivelesRepository;

    @Autowired
    public NivelesService(NivelesRepository nivelesRepository) {
        this.nivelesRepository = nivelesRepository;
    }

    public NivelesDto crearNivel(NivelesDto nivelesDto) {
        logger.info("Creando nivel: {}", nivelesDto.getNombre());

        NivelesEntity nivelesEntity = new NivelesEntity();
        nivelesEntity.setExp(nivelesDto.getExp());
        nivelesEntity.setNombre(nivelesDto.getNombre());

        NivelesEntity nuevoNivel = nivelesRepository.save(nivelesEntity);
        logger.info("Nivel creado con ID: {}", nuevoNivel.getId());

        return convertirEntityADto(nuevoNivel);
    }

    public List<NivelesDto> obtenerTodosLosNiveles() {
        logger.info("Obteniendo todos los niveles");

        List<NivelesEntity> niveles = nivelesRepository.findAll();
        return niveles.stream()
                .map(this::convertirEntityADto)
                .collect(Collectors.toList());
    }

    public NivelesDto obtenerNivelPorId(Integer id) {
        logger.info("Obteniendo nivel con ID: {}", id);

        NivelesEntity nivel = nivelesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));

        return convertirEntityADto(nivel);
    }

    public NivelesDto actualizarNivel(Integer id, NivelesDto nivelesDto) {
        logger.info("Actualizando nivel con ID: {}", id);

        NivelesEntity nivelesEntity = nivelesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));

        nivelesEntity.setExp(nivelesDto.getExp());
        nivelesEntity.setNombre(nivelesDto.getNombre());

        NivelesEntity nivelActualizado = nivelesRepository.save(nivelesEntity);

        return convertirEntityADto(nivelActualizado);
    }

    public void eliminarNivel(Integer id) {
        logger.info("Eliminando nivel con ID: {}", id);

        if (!nivelesRepository.existsById(id)) {
            throw new RuntimeException("Nivel no encontrado con ID: " + id);
        }

        nivelesRepository.deleteById(id);
    }

    private NivelesDto convertirEntityADto(NivelesEntity nivelesEntity) {
        return new NivelesDto(
                nivelesEntity.getId(),
                nivelesEntity.getExp(),
                nivelesEntity.getNombre()
        );
    }
}
