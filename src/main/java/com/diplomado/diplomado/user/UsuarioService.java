package com.diplomado.diplomado.user;

import com.diplomado.diplomado.roles.RolEntity;
import com.diplomado.diplomado.roles.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolesRepository rolesRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
    }

    public UsuarioDto crearUsuario(UsuarioDto usuarioDto) {
        logger.info("Creando usuario: {}", usuarioDto.getNombreCompl());

        RolEntity rol = rolesRepository.findById(usuarioDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + usuarioDto.getRolId()));

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNombreCompl(usuarioDto.getNombreCompl());
        usuarioEntity.setEmail(usuarioDto.getEmail());
        usuarioEntity.setFechaNacimien(usuarioDto.getFechaNacimien());
        usuarioEntity.setPuntos(usuarioDto.getPuntos());
        usuarioEntity.setRol(rol);

        UsuarioEntity nuevoUsuario = usuarioRepository.save(usuarioEntity);
        logger.info("Usuario creado con ID: {}", nuevoUsuario.getId());

        return convertirUsuarioEntityADto(nuevoUsuario);
    }

    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        logger.info("Obteniendo todos los usuarios");

        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertirUsuarioEntityADto)
                .collect(Collectors.toList());
    }

    public UsuarioDto obtenerUsuarioPorId(Integer id) {
        logger.info("Obteniendo usuario con ID: {}", id);

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return convertirUsuarioEntityADto(usuario);
    }

    public UsuarioDto actualizarUsuario(Integer id, UsuarioDto usuarioDto) {
        logger.info("Actualizando usuario con ID: {}", id);

        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        RolEntity rol = rolesRepository.findById(usuarioDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + usuarioDto.getRolId()));

        usuarioEntity.setNombreCompl(usuarioDto.getNombreCompl());
        usuarioEntity.setEmail(usuarioDto.getEmail());
        usuarioEntity.setFechaNacimien(usuarioDto.getFechaNacimien());
        usuarioEntity.setPuntos(usuarioDto.getPuntos());
        usuarioEntity.setRol(rol);

        UsuarioEntity usuarioActualizado = usuarioRepository.save(usuarioEntity);

        return convertirUsuarioEntityADto(usuarioActualizado);
    }

    public void eliminarUsuario(Integer id) {
        logger.info("Eliminando usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    private UsuarioDto convertirUsuarioEntityADto(UsuarioEntity usuarioEntity) {
        return new UsuarioDto(
                usuarioEntity.getId(),
                usuarioEntity.getNombreCompl(),
                usuarioEntity.getEmail(),
                new java.sql.Date(usuarioEntity.getFechaNacimien().getTime()), // Conversion needed if Entity uses
                                                                               // java.util.Date and DTO uses
                                                                               // java.sql.Date, or vice versa.
                                                                               // MisionDto used java.sql.Date.
                                                                               // UsuarioEntity uses java.util.Date.
                usuarioEntity.getPuntos(),
                usuarioEntity.getRol().getId());
    }
}
