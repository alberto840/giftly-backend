package com.diplomado.diplomado.tienda_premios;

import com.diplomado.diplomado.producto.ProductoEntity;
import com.diplomado.diplomado.producto.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TiendaPremioService {
    private static final Logger logger = LoggerFactory.getLogger(TiendaPremioService.class);
    private final TiendaPremioRepository tiendaPremioRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public TiendaPremioService(TiendaPremioRepository tiendaPremioRepository, ProductoRepository productoRepository) {
        this.tiendaPremioRepository = tiendaPremioRepository;
        this.productoRepository = productoRepository;
    }

    public TiendaPremioDto crearTiendaPremio(TiendaPremioDto tiendaPremioDto) {
        logger.info("Creando tienda premio");

        ProductoEntity producto = productoRepository.findById(tiendaPremioDto.getProductoId())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con ID: " + tiendaPremioDto.getProductoId()));

        TiendaPremioEntity tiendaPremioEntity = new TiendaPremioEntity();
        tiendaPremioEntity.setPrecioPunto(tiendaPremioDto.getPrecioPunto());
        tiendaPremioEntity.setProducto(producto);

        TiendaPremioEntity nuevaTiendaPremio = tiendaPremioRepository.save(tiendaPremioEntity);
        logger.info("Tienda premio creada con ID: {}", nuevaTiendaPremio.getId());

        return convertirTiendaPremioEntityADto(nuevaTiendaPremio);
    }

    public List<TiendaPremioDto> obtenerTodasLasTiendaPremios() {
        logger.info("Obteniendo todas las tienda premios");

        List<TiendaPremioEntity> tiendaPremios = tiendaPremioRepository.findAll();
        return tiendaPremios.stream()
                .map(this::convertirTiendaPremioEntityADto)
                .collect(Collectors.toList());
    }

    public TiendaPremioDto obtenerTiendaPremioPorId(Integer id) {
        logger.info("Obteniendo tienda premio con ID: {}", id);

        TiendaPremioEntity tiendaPremio = tiendaPremioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda premio no encontrada con ID: " + id));

        return convertirTiendaPremioEntityADto(tiendaPremio);
    }

    public TiendaPremioDto actualizarTiendaPremio(Integer id, TiendaPremioDto tiendaPremioDto) {
        logger.info("Actualizando tienda premio con ID: {}", id);

        TiendaPremioEntity tiendaPremioEntity = tiendaPremioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda premio no encontrada con ID: " + id));

        ProductoEntity producto = productoRepository.findById(tiendaPremioDto.getProductoId())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con ID: " + tiendaPremioDto.getProductoId()));

        tiendaPremioEntity.setPrecioPunto(tiendaPremioDto.getPrecioPunto());
        tiendaPremioEntity.setProducto(producto);

        TiendaPremioEntity tiendaPremioActualizada = tiendaPremioRepository.save(tiendaPremioEntity);

        return convertirTiendaPremioEntityADto(tiendaPremioActualizada);
    }

    public void eliminarTiendaPremio(Integer id) {
        logger.info("Eliminando tienda premio con ID: {}", id);

        if (!tiendaPremioRepository.existsById(id)) {
            throw new RuntimeException("Tienda premio no encontrada con ID: " + id);
        }

        tiendaPremioRepository.deleteById(id);
    }

    private TiendaPremioDto convertirTiendaPremioEntityADto(TiendaPremioEntity tiendaPremioEntity) {
        return new TiendaPremioDto(
                tiendaPremioEntity.getId(),
                tiendaPremioEntity.getPrecioPunto(),
                tiendaPremioEntity.getProducto().getId());
    }
}
