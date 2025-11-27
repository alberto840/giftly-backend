package com.diplomado.diplomado.roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolService {
    private static final Logger logger = LoggerFactory.getLogger(RolService.class);
    private final RolesRepository rolesRepository;

    @Autowired
    public RolService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public RolDto crearRol(RolDto rolDto) {
        logger.info("Creando rol: {}", rolDto.getDescripcion());

        RolEntity rolEntity = new RolEntity();
        rolEntity.setDescripcion(rolDto.getDescripcion());

        RolEntity nuevoRol = rolesRepository.save(rolEntity);
        logger.info("Rol creado con ID: {}", nuevoRol.getId());

        return convertirRolEntityADto(nuevoRol);
    }

    public List<RolDto> obtenerTodosLosRoles() {
        logger.info("Obteniendo todos los roles");

        List<RolEntity> roles = rolesRepository.findAll();
        return roles.stream()
                .map(this::convertirRolEntityADto)
                .collect(Collectors.toList());
    }

    public RolDto obtenerRolPorId(Integer id) {
        logger.info("Obteniendo rol con ID: {}", id);

        RolEntity rol = rolesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        return convertirRolEntityADto(rol);
    }

    public RolDto actualizarRol(Integer id, RolDto rolDto) {
        logger.info("Actualizando rol con ID: {}", id);

        RolEntity rolEntity = rolesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        rolEntity.setDescripcion(rolDto.getDescripcion());

        RolEntity rolActualizado = rolesRepository.save(rolEntity);

        return convertirRolEntityADto(rolActualizado);
    }

    public void eliminarRol(Integer id) {
        logger.info("Eliminando rol con ID: {}", id);

        if (!rolesRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }

        rolesRepository.deleteById(id);
    }

    private RolDto convertirRolEntityADto(RolEntity rolEntity) {
        return new RolDto(
                rolEntity.getId(),
                rolEntity.getDescripcion());
    }
}
