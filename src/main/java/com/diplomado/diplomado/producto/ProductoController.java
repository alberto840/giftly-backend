package com.diplomado.diplomado.producto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import com.diplomado.diplomado.config.JwtConfig;
import com.diplomado.diplomado.utils.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/producto")
@Tag(name = "Producto", description = "Product management endpoints")
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    private final ProductoService productoService;
    private final JwtConfig jwtConfig;

    @Autowired
    public ProductoController(ProductoService productoService, JwtConfig jwtConfig) {
        this.productoService = productoService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Create a product", description = "Creates a new product. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<ProductoDto>> crearProducto(
            @RequestBody ProductoDto productoDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear producto: {}", username);
        ProductoDto nuevoProducto = productoService.crearProducto(productoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Producto creado exitosamente", nuevoProducto));
    }

    @Operation(summary = "Get all products", description = "Retrieves a list of all products.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductoDto>>> obtenerTodosLosProductos(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener productos");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener productos: {}", username);
        List<ProductoDto> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(new ResponseDto<>(true, "Productos obtenidos exitosamente", productos));
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductoDto>> obtenerProductoPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener producto con ID: {}", id);
        ProductoDto producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Producto obtenido exitosamente", producto));
    }

    @Operation(summary = "Update product", description = "Updates an existing product by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<ProductoDto>> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody ProductoDto productoDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar producto con ID: {}", id);
        ProductoDto productoActualizado = productoService.actualizarProducto(id, productoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Producto actualizado exitosamente", productoActualizado));
    }

    @Operation(summary = "Delete product", description = "Deletes a product by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarProducto(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar producto con ID: {}", id);
        productoService.eliminarProducto(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Producto eliminado exitosamente", null));
    }
}
