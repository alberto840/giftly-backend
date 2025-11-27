package com.diplomado.diplomado.categoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaDto crearCategoria(CategoriaDto categoriaDto) {
        logger.info("Creando categoria: {}", categoriaDto.getNombre());

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre(categoriaDto.getNombre());

        CategoriaEntity nuevaCategoria = categoriaRepository.save(categoriaEntity);
        logger.info("Categoria creada con ID: {}", nuevaCategoria.getId());

        return convertirCategoriaEntityADto(nuevaCategoria);
    }

    public List<CategoriaDto> obtenerTodasLasCategorias() {
        logger.info("Obteniendo todas las categorias");

        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::convertirCategoriaEntityADto)
                .collect(Collectors.toList());
    }

    public CategoriaDto obtenerCategoriaPorId(Integer id) {
        logger.info("Obteniendo categoria con ID: {}", id);

        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));

        return convertirCategoriaEntityADto(categoria);
    }

    public CategoriaDto actualizarCategoria(Integer id, CategoriaDto categoriaDto) {
        logger.info("Actualizando categoria con ID: {}", id);

        CategoriaEntity categoriaEntity = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));

        categoriaEntity.setNombre(categoriaDto.getNombre());

        CategoriaEntity categoriaActualizada = categoriaRepository.save(categoriaEntity);

        return convertirCategoriaEntityADto(categoriaActualizada);
    }

    public void eliminarCategoria(Integer id) {
        logger.info("Eliminando categoria con ID: {}", id);

        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con ID: " + id);
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaDto convertirCategoriaEntityADto(CategoriaEntity categoriaEntity) {
        return new CategoriaDto(
                categoriaEntity.getId(),
                categoriaEntity.getNombre());
    }
}
