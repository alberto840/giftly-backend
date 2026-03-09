package com.diplomado.diplomado.user;

import com.diplomado.diplomado.roles.RolEntity;
import com.diplomado.diplomado.roles.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        usuarioEntity.setPuntos(0);
        usuarioEntity.setExp(BigDecimal.ZERO);
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
        usuarioEntity.setExp(usuarioDto.getExp());
        usuarioEntity.setRol(rol);

        UsuarioEntity usuarioActualizado = usuarioRepository.save(usuarioEntity);

        return convertirUsuarioEntityADto(usuarioActualizado);
    }

    /**
     * Suma o resta exp y puntos a un usuario.
     * Pasar valores positivos para sumar, negativos para restar.
     */
    public UsuarioDto modificarExpYPuntos(Integer id, BigDecimal deltaExp, Integer deltaPuntos) {
        logger.info("Modificando exp y puntos del usuario con ID: {}", id);

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        BigDecimal expActual = usuario.getExp() != null ? usuario.getExp() : BigDecimal.ZERO;
        Integer puntosActual = usuario.getPuntos() != null ? usuario.getPuntos() : 0;

        usuario.setExp(expActual.add(deltaExp));
        usuario.setPuntos(puntosActual + deltaPuntos);

        UsuarioEntity usuarioActualizado = usuarioRepository.save(usuario);
        logger.info("Exp y puntos actualizados para usuario ID: {}", id);

        return convertirUsuarioEntityADto(usuarioActualizado);
    }

    /** Retorna solo los puntos actuales de un usuario. */
    public Integer obtenerPuntosDeUsuario(Integer id) {
        logger.info("Obteniendo puntos del usuario con ID: {}", id);

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return usuario.getPuntos() != null ? usuario.getPuntos() : 0;
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
                usuarioEntity.getExp(),
                usuarioEntity.getRol().getId());
    }
}
