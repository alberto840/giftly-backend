package com.diplomado.diplomado.misiones_usuarios;

import com.diplomado.diplomado.misiones.MisionEntity;
import com.diplomado.diplomado.misiones.MisionRepository;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MisionUsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(MisionUsuarioService.class);
    private final MisionUsuarioRepository misionUsuarioRepository;
    private final MisionRepository misionRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MisionUsuarioService(MisionUsuarioRepository misionUsuarioRepository, MisionRepository misionRepository,
            UsuarioRepository usuarioRepository) {
        this.misionUsuarioRepository = misionUsuarioRepository;
        this.misionRepository = misionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public MisionUsuarioDto crearMisionUsuario(MisionUsuarioDto misionUsuarioDto) {
        logger.info("Creando mision usuario");

        MisionEntity mision = misionRepository.findById(misionUsuarioDto.getMisionId())
                .orElseThrow(
                        () -> new RuntimeException("Mision no encontrada con ID: " + misionUsuarioDto.getMisionId()));

        UsuarioEntity usuario = usuarioRepository.findById(misionUsuarioDto.getUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Usuario no encontrado con ID: " + misionUsuarioDto.getUsuarioId()));

        MisionUsuarioEntity misionUsuarioEntity = new MisionUsuarioEntity();
        misionUsuarioEntity.setEstado(misionUsuarioDto.getEstado());
        misionUsuarioEntity.setMision(mision);
        misionUsuarioEntity.setUsuario(usuario);

        MisionUsuarioEntity nuevaMisionUsuario = misionUsuarioRepository.save(misionUsuarioEntity);
        logger.info("Mision usuario creada con ID: {}", nuevaMisionUsuario.getId());

        return convertirMisionUsuarioEntityADto(nuevaMisionUsuario);
    }

    public List<MisionUsuarioDto> obtenerTodasLasMisionesUsuarios() {
        logger.info("Obteniendo todas las misiones usuarios");

        List<MisionUsuarioEntity> misionesUsuarios = misionUsuarioRepository.findAll();
        return misionesUsuarios.stream()
                .map(this::convertirMisionUsuarioEntityADto)
                .collect(Collectors.toList());
    }

    public MisionUsuarioDto obtenerMisionUsuarioPorId(Integer id) {
        logger.info("Obteniendo mision usuario con ID: {}", id);

        MisionUsuarioEntity misionUsuario = misionUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mision usuario no encontrada con ID: " + id));

        return convertirMisionUsuarioEntityADto(misionUsuario);
    }

    public MisionUsuarioDto actualizarMisionUsuario(Integer id, MisionUsuarioDto misionUsuarioDto) {
        logger.info("Actualizando mision usuario con ID: {}", id);

        MisionUsuarioEntity misionUsuarioEntity = misionUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mision usuario no encontrada con ID: " + id));

        MisionEntity mision = misionRepository.findById(misionUsuarioDto.getMisionId())
                .orElseThrow(
                        () -> new RuntimeException("Mision no encontrada con ID: " + misionUsuarioDto.getMisionId()));

        UsuarioEntity usuario = usuarioRepository.findById(misionUsuarioDto.getUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Usuario no encontrado con ID: " + misionUsuarioDto.getUsuarioId()));

        misionUsuarioEntity.setEstado(misionUsuarioDto.getEstado());
        misionUsuarioEntity.setMision(mision);
        misionUsuarioEntity.setUsuario(usuario);

        MisionUsuarioEntity misionUsuarioActualizada = misionUsuarioRepository.save(misionUsuarioEntity);

        return convertirMisionUsuarioEntityADto(misionUsuarioActualizada);
    }

    public void eliminarMisionUsuario(Integer id) {
        logger.info("Eliminando mision usuario con ID: {}", id);

        if (!misionUsuarioRepository.existsById(id)) {
            throw new RuntimeException("Mision usuario no encontrada con ID: " + id);
        }

        misionUsuarioRepository.deleteById(id);
    }

    private MisionUsuarioDto convertirMisionUsuarioEntityADto(MisionUsuarioEntity misionUsuarioEntity) {
        return new MisionUsuarioDto(
                misionUsuarioEntity.getId(),
                misionUsuarioEntity.getEstado(),
                misionUsuarioEntity.getMision().getId(),
                misionUsuarioEntity.getUsuario().getId());
    }
}
