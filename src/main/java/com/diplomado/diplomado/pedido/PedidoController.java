package com.diplomado.diplomado.pedido;

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
@RequestMapping(path = "api/v1/pedido")
public class PedidoController {
    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService pedidoService;
    private final JwtConfig jwtConfig;

    @Autowired
    public PedidoController(PedidoService pedidoService, JwtConfig jwtConfig) {
        this.pedidoService = pedidoService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<PedidoDto>> crearPedido(
            @RequestBody PedidoDto pedidoDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear pedido: {}", username);
        PedidoDto nuevoPedido = pedidoService.crearPedido(pedidoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido creado exitosamente", nuevoPedido));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<PedidoDto>>> obtenerTodosLosPedidos(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener pedidos");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener pedidos: {}", username);
        List<PedidoDto> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedidos obtenidos exitosamente", pedidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PedidoDto>> obtenerPedidoPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener pedido con ID: {}", id);
        PedidoDto pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido obtenido exitosamente", pedido));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<PedidoDto>> actualizarPedido(
            @PathVariable Integer id,
            @RequestBody PedidoDto pedidoDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar pedido con ID: {}", id);
        PedidoDto pedidoActualizado = pedidoService.actualizarPedido(id, pedidoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido actualizado exitosamente", pedidoActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarPedido(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar pedido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar pedido con ID: {}", id);
        pedidoService.eliminarPedido(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Pedido eliminado exitosamente", null));
    }
}
