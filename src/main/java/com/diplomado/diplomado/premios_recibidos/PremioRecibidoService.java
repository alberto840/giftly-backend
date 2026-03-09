package com.diplomado.diplomado.premios_recibidos;

import com.diplomado.diplomado.tienda_premios.TiendaPremioEntity;
import com.diplomado.diplomado.tienda_premios.TiendaPremioRepository;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremioRecibidoService {
    private static final Logger logger = LoggerFactory.getLogger(PremioRecibidoService.class);

    private final PremioRecibidoRepository premioRecibidoRepository;
    private final TiendaPremioRepository tiendaPremioRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PremioRecibidoService(PremioRecibidoRepository premioRecibidoRepository,
            TiendaPremioRepository tiendaPremioRepository,
            UsuarioRepository usuarioRepository) {
        this.premioRecibidoRepository = premioRecibidoRepository;
        this.tiendaPremioRepository = tiendaPremioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PremioRecibidoDto crearPremioRecibido(PremioRecibidoDto dto) {
        logger.info("Creando premio recibido");

        TiendaPremioEntity tiendaPremio = tiendaPremioRepository.findById(dto.getTiendaPremioId())
                .orElseThrow(() -> new RuntimeException("TiendaPremio no encontrada con ID: " + dto.getTiendaPremioId()));

        UsuarioEntity usuario = usuarioRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUserId()));

        PremioRecibidoEntity entity = new PremioRecibidoEntity();
        entity.setRecibido(dto.getRecibido());
        entity.setFechaRecibido(new Date()); // Se establece automáticamente al momento de creación
        entity.setTipo(dto.getTipo());
        entity.setTiendaPremio(tiendaPremio);
        entity.setUsuario(usuario);

        PremioRecibidoEntity saved = premioRecibidoRepository.save(entity);
        logger.info("Premio recibido creado con ID: {}", saved.getId());

        return convertirADto(saved);
    }

    public List<PremioRecibidoDto> obtenerTodos() {
        logger.info("Obteniendo todos los premios recibidos");
        return premioRecibidoRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public PremioRecibidoDto obtenerPorId(Integer id) {
        logger.info("Obteniendo premio recibido con ID: {}", id);
        PremioRecibidoEntity entity = premioRecibidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Premio recibido no encontrado con ID: " + id));
        return convertirADto(entity);
    }

    public List<PremioRecibidoDto> obtenerPorUserId(Integer userId) {
        logger.info("Obteniendo premios recibidos del usuario con ID: {}", userId);
        return premioRecibidoRepository.findByUsuarioId(userId)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public PremioRecibidoDto actualizarPremioRecibido(Integer id, PremioRecibidoDto dto) {
        logger.info("Actualizando premio recibido con ID: {}", id);

        PremioRecibidoEntity entity = premioRecibidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Premio recibido no encontrado con ID: " + id));

        TiendaPremioEntity tiendaPremio = tiendaPremioRepository.findById(dto.getTiendaPremioId())
                .orElseThrow(() -> new RuntimeException("TiendaPremio no encontrada con ID: " + dto.getTiendaPremioId()));

        UsuarioEntity usuario = usuarioRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUserId()));

        entity.setRecibido(dto.getRecibido());
        entity.setTipo(dto.getTipo());
        entity.setTiendaPremio(tiendaPremio);
        entity.setUsuario(usuario);

        PremioRecibidoEntity updated = premioRecibidoRepository.save(entity);
        return convertirADto(updated);
    }

    public void eliminarPremioRecibido(Integer id) {
        logger.info("Eliminando premio recibido con ID: {}", id);
        if (!premioRecibidoRepository.existsById(id)) {
            throw new RuntimeException("Premio recibido no encontrado con ID: " + id);
        }
        premioRecibidoRepository.deleteById(id);
    }

    private PremioRecibidoDto convertirADto(PremioRecibidoEntity entity) {
        return new PremioRecibidoDto(
                entity.getId(),
                entity.getRecibido(),
                entity.getFechaRecibido(),
                entity.getTipo(),
                entity.getTiendaPremio().getId(),
                entity.getUsuario().getId());
    }
}
