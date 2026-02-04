package com.diplomado.diplomado.pedido_producto;

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
@RequestMapping(path = "api/v1/pedido-producto")
@Tag(name = "PedidoProducto", description = "Order-Product relationship management endpoints")
public class PedidoProductoController {
    private static final Logger logger = LoggerFactory.getLogger(PedidoProductoController.class);
    private final PedidoProductoService pedidoProductoService;
    private final JwtConfig jwtConfig;

    @Autowired
    public PedidoProductoController(PedidoProductoService pedidoProductoService, JwtConfig jwtConfig) {
        this.pedidoProductoService = pedidoProductoService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Create order-product relationship", description = "Creates a new order-product relationship. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<PedidoProductoDto>> crearPedidoProducto(
            @RequestBody PedidoProductoDto pedidoProductoDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear pedido producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear pedido producto: {}", username);
        PedidoProductoDto nuevoPedidoProducto = pedidoProductoService.crearPedidoProducto(pedidoProductoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido producto creado exitosamente", nuevoPedidoProducto));
    }

    @Operation(summary = "Get all order-product relationships", description = "Retrieves a list of all order-product relationships.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<PedidoProductoDto>>> obtenerTodosLosPedidoProductos(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener pedido productos");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener pedido productos: {}", username);
        List<PedidoProductoDto> pedidoProductos = pedidoProductoService.obtenerTodosLosPedidoProductos();
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido productos obtenidos exitosamente", pedidoProductos));
    }

    @Operation(summary = "Get order-product relationship by ID", description = "Retrieves a specific order-product relationship by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PedidoProductoDto>> obtenerPedidoProductoPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener pedido producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener pedido producto con ID: {}", id);
        PedidoProductoDto pedidoProducto = pedidoProductoService.obtenerPedidoProductoPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido producto obtenido exitosamente", pedidoProducto));
    }

    @Operation(summary = "Update order-product relationship", description = "Updates an existing order-product relationship by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<PedidoProductoDto>> actualizarPedidoProducto(
            @PathVariable Integer id,
            @RequestBody PedidoProductoDto pedidoProductoDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar pedido producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar pedido producto con ID: {}", id);
        PedidoProductoDto pedidoProductoActualizado = pedidoProductoService.actualizarPedidoProducto(id,
                pedidoProductoDto);
        return ResponseEntity
                .ok(new ResponseDto<>(true, "Pedido producto actualizado exitosamente", pedidoProductoActualizado));
    }

    @Operation(summary = "Delete order-product relationship", description = "Deletes an order-product relationship by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarPedidoProducto(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar pedido producto");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar pedido producto con ID: {}", id);
        pedidoProductoService.eliminarPedidoProducto(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido producto eliminado exitosamente", null));
    }
}
