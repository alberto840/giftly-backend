package com.diplomado.diplomado.referidos;

import com.diplomado.diplomado.roles.RolEntity;
import com.diplomado.diplomado.roles.RolesRepository;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReferidoService {
    private static final Logger logger = LoggerFactory.getLogger(ReferidoService.class);
    private final ReferidoRepository referidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public ReferidoService(ReferidoRepository referidoRepository, UsuarioRepository usuarioRepository,
            RolesRepository rolesRepository) {
        this.referidoRepository = referidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
    }

    public ReferidoDto crearReferido(ReferidoDto referidoDto) {
        logger.info("Creando referido: {}", referidoDto.getCodigo());

        UsuarioEntity usuario = usuarioRepository.findById(referidoDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + referidoDto.getUsuarioId()));

        RolEntity rol = rolesRepository.findById(referidoDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + referidoDto.getRolId()));

        ReferidoEntity referidoEntity = new ReferidoEntity();
        referidoEntity.setCodigo(referidoDto.getCodigo());
        referidoEntity.setCantidadInvita(referidoDto.getCantidadInvita());
        referidoEntity.setUsuario(usuario);
        referidoEntity.setRol(rol);

        ReferidoEntity nuevoReferido = referidoRepository.save(referidoEntity);
        logger.info("Referido creado con ID: {}", nuevoReferido.getId());

        return convertirReferidoEntityADto(nuevoReferido);
    }

    public List<ReferidoDto> obtenerTodosLosReferidos() {
        logger.info("Obteniendo todos los referidos");

        List<ReferidoEntity> referidos = referidoRepository.findAll();
        return referidos.stream()
                .map(this::convertirReferidoEntityADto)
                .collect(Collectors.toList());
    }

    public ReferidoDto obtenerReferidoPorId(Integer id) {
        logger.info("Obteniendo referido con ID: {}", id);

        ReferidoEntity referido = referidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Referido no encontrado con ID: " + id));

        return convertirReferidoEntityADto(referido);
    }

    public ReferidoDto actualizarReferido(Integer id, ReferidoDto referidoDto) {
        logger.info("Actualizando referido con ID: {}", id);

        ReferidoEntity referidoEntity = referidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Referido no encontrado con ID: " + id));

        UsuarioEntity usuario = usuarioRepository.findById(referidoDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + referidoDto.getUsuarioId()));

        RolEntity rol = rolesRepository.findById(referidoDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + referidoDto.getRolId()));

        referidoEntity.setCodigo(referidoDto.getCodigo());
        referidoEntity.setCantidadInvita(referidoDto.getCantidadInvita());
        referidoEntity.setUsuario(usuario);
        referidoEntity.setRol(rol);

        ReferidoEntity referidoActualizado = referidoRepository.save(referidoEntity);

        return convertirReferidoEntityADto(referidoActualizado);
    }

    public void eliminarReferido(Integer id) {
        logger.info("Eliminando referido con ID: {}", id);

        if (!referidoRepository.existsById(id)) {
            throw new RuntimeException("Referido no encontrado con ID: " + id);
        }

        referidoRepository.deleteById(id);
    }

    private ReferidoDto convertirReferidoEntityADto(ReferidoEntity referidoEntity) {
        return new ReferidoDto(
                referidoEntity.getId(),
                referidoEntity.getCodigo(),
                referidoEntity.getCantidadInvita(),
                referidoEntity.getUsuario().getId(),
                referidoEntity.getRol().getId());
    }
}
