package com.diplomado.diplomado.producto;

import com.diplomado.diplomado.categoria.CategoriaEntity;
import com.diplomado.diplomado.categoria.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public ProductoDto crearProducto(ProductoDto productoDto) {
        logger.info("Creando producto: {}", productoDto.getNombre());

        CategoriaEntity categoria = categoriaRepository.findById(productoDto.getCategoriaId())
                .orElseThrow(
                        () -> new RuntimeException("Categoria no encontrada con ID: " + productoDto.getCategoriaId()));

        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setNombre(productoDto.getNombre());
        productoEntity.setStock(productoDto.getStock());
        productoEntity.setPrecio(productoDto.getPrecio());
        productoEntity.setCategoria(categoria);

        ProductoEntity nuevoProducto = productoRepository.save(productoEntity);
        logger.info("Producto creado con ID: {}", nuevoProducto.getId());

        return convertirProductoEntityADto(nuevoProducto);
    }

    public List<ProductoDto> obtenerTodosLosProductos() {
        logger.info("Obteniendo todos los productos");

        List<ProductoEntity> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertirProductoEntityADto)
                .collect(Collectors.toList());
    }

    public ProductoDto obtenerProductoPorId(Integer id) {
        logger.info("Obteniendo producto con ID: {}", id);

        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        return convertirProductoEntityADto(producto);
    }

    public ProductoDto actualizarProducto(Integer id, ProductoDto productoDto) {
        logger.info("Actualizando producto con ID: {}", id);

        ProductoEntity productoEntity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        CategoriaEntity categoria = categoriaRepository.findById(productoDto.getCategoriaId())
                .orElseThrow(
                        () -> new RuntimeException("Categoria no encontrada con ID: " + productoDto.getCategoriaId()));

        productoEntity.setNombre(productoDto.getNombre());
        productoEntity.setStock(productoDto.getStock());
        productoEntity.setPrecio(productoDto.getPrecio());
        productoEntity.setCategoria(categoria);

        ProductoEntity productoActualizado = productoRepository.save(productoEntity);

        return convertirProductoEntityADto(productoActualizado);
    }

    public void eliminarProducto(Integer id) {
        logger.info("Eliminando producto con ID: {}", id);

        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }

        productoRepository.deleteById(id);
    }

    private ProductoDto convertirProductoEntityADto(ProductoEntity productoEntity) {
        return new ProductoDto(
                productoEntity.getId(),
                productoEntity.getNombre(),
                productoEntity.getStock(),
                productoEntity.getPrecio(),
                productoEntity.getCategoria().getId());
    }
}
