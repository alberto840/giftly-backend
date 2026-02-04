package com.diplomado.diplomado.detalle_pedido;

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
@RequestMapping(path = "api/v1/detalle-pedido")
@Tag(name = "DetallePedido", description = "Order detail management endpoints")
public class DetallePedidoController {
    private static final Logger logger = LoggerFactory.getLogger(DetallePedidoController.class);
    private final DetallePedidoService detallePedidoService;
    private final JwtConfig jwtConfig;

    @Autowired
    public DetallePedidoController(DetallePedidoService detallePedidoService, JwtConfig jwtConfig) {
        this.detallePedidoService = detallePedidoService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Create order detail", description = "Creates a new order detail. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<DetallePedidoDto>> crearDetallePedido(
            @RequestBody DetallePedidoDto detallePedidoDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear detalle pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear detalle pedido: {}", username);
        DetallePedidoDto nuevoDetallePedido = detallePedidoService.crearDetallePedido(detallePedidoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Detalle pedido creado exitosamente", nuevoDetallePedido));
    }

    @Operation(summary = "Get all order details", description = "Retrieves a list of all order details.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<DetallePedidoDto>>> obtenerTodosLosDetallePedidos(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener detalle pedidos");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener detalle pedidos: {}", username);
        List<DetallePedidoDto> detallePedidos = detallePedidoService.obtenerTodosLosDetallePedidos();
        return ResponseEntity.ok(new ResponseDto<>(true, "Detalle pedidos obtenidos exitosamente", detallePedidos));
    }

    @Operation(summary = "Get order detail by ID", description = "Retrieves a specific order detail by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<DetallePedidoDto>> obtenerDetallePedidoPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener detalle pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener detalle pedido con ID: {}", id);
        DetallePedidoDto detallePedido = detallePedidoService.obtenerDetallePedidoPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Detalle pedido obtenido exitosamente", detallePedido));
    }

    @Operation(summary = "Update order detail", description = "Updates an existing order detail by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<DetallePedidoDto>> actualizarDetallePedido(
            @PathVariable Integer id,
            @RequestBody DetallePedidoDto detallePedidoDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar detalle pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar detalle pedido con ID: {}", id);
        DetallePedidoDto detallePedidoActualizado = detallePedidoService.actualizarDetallePedido(id, detallePedidoDto);
        return ResponseEntity
                .ok(new ResponseDto<>(true, "Detalle pedido actualizado exitosamente", detallePedidoActualizado));
    }

    @Operation(summary = "Delete order detail", description = "Deletes an order detail by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarDetallePedido(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar detalle pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar detalle pedido con ID: {}", id);
        detallePedidoService.eliminarDetallePedido(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Detalle pedido eliminado exitosamente", null));
    }
}
