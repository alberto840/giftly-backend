package com.diplomado.diplomado.reseñas;

import com.diplomado.diplomado.producto.ProductoEntity;
import com.diplomado.diplomado.producto.ProductoRepository;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResenaService {
    private static final Logger logger = LoggerFactory.getLogger(ResenaService.class);
    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public ResenaService(ResenaRepository resenaRepository, UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository) {
        this.resenaRepository = resenaRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    public ResenaDto crearResena(ResenaDto resenaDto) {
        logger.info("Creando resena");

        UsuarioEntity usuario = usuarioRepository.findById(resenaDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + resenaDto.getUsuarioId()));

        ProductoEntity producto = productoRepository.findById(resenaDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + resenaDto.getProductoId()));

        ResenaEntity resenaEntity = new ResenaEntity();
        resenaEntity.setCalificacion(resenaDto.getCalificacion());
        resenaEntity.setComentario(resenaDto.getComentario());
        resenaEntity.setUsuario(usuario);
        resenaEntity.setProducto(producto);

        ResenaEntity nuevaResena = resenaRepository.save(resenaEntity);
        logger.info("Resena creada con ID: {}", nuevaResena.getId());

        return convertirResenaEntityADto(nuevaResena);
    }

    public List<ResenaDto> obtenerTodasLasResenas() {
        logger.info("Obteniendo todas las resenas");

        List<ResenaEntity> resenas = resenaRepository.findAll();
        return resenas.stream()
                .map(this::convertirResenaEntityADto)
                .collect(Collectors.toList());
    }

    public ResenaDto obtenerResenaPorId(Integer id) {
        logger.info("Obteniendo resena con ID: {}", id);

        ResenaEntity resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resena no encontrada con ID: " + id));

        return convertirResenaEntityADto(resena);
    }

    public ResenaDto actualizarResena(Integer id, ResenaDto resenaDto) {
        logger.info("Actualizando resena con ID: {}", id);

        ResenaEntity resenaEntity = resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resena no encontrada con ID: " + id));

        UsuarioEntity usuario = usuarioRepository.findById(resenaDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + resenaDto.getUsuarioId()));

        ProductoEntity producto = productoRepository.findById(resenaDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + resenaDto.getProductoId()));

        resenaEntity.setCalificacion(resenaDto.getCalificacion());
        resenaEntity.setComentario(resenaDto.getComentario());
        resenaEntity.setUsuario(usuario);
        resenaEntity.setProducto(producto);

        ResenaEntity resenaActualizada = resenaRepository.save(resenaEntity);

        return convertirResenaEntityADto(resenaActualizada);
    }

    public void eliminarResena(Integer id) {
        logger.info("Eliminando resena con ID: {}", id);

        if (!resenaRepository.existsById(id)) {
            throw new RuntimeException("Resena no encontrada con ID: " + id);
        }

        resenaRepository.deleteById(id);
    }

    private ResenaDto convertirResenaEntityADto(ResenaEntity resenaEntity) {
        return new ResenaDto(
                resenaEntity.getId(),
                resenaEntity.getCalificacion(),
                resenaEntity.getComentario(),
                resenaEntity.getUsuario().getId(),
                resenaEntity.getProducto().getId());
    }
}
