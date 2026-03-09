package com.diplomado.diplomado.premios_recibidos;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(path = "api/v1/premios-recibidos")
@Tag(name = "PremiosRecibidos", description = "Endpoints para gestión de premios recibidos por usuarios")
public class PremioRecibidoController {
    private static final Logger logger = LoggerFactory.getLogger(PremioRecibidoController.class);

    private final PremioRecibidoService premioRecibidoService;
    private final JwtConfig jwtConfig;

    @Autowired
    public PremioRecibidoController(PremioRecibidoService premioRecibidoService, JwtConfig jwtConfig) {
        this.premioRecibidoService = premioRecibidoService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Crear premio recibido", description = "Registra un nuevo premio recibido por un usuario. La fecha se establece automáticamente.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<PremioRecibidoDto>> crear(
            @RequestBody PremioRecibidoDto dto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para crear premio recibido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear premio recibido: {}", username);
        PremioRecibidoDto nuevo = premioRecibidoService.crearPremioRecibido(dto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Premio recibido creado exitosamente", nuevo));
    }

    @Operation(summary = "Obtener todos los premios recibidos", description = "Retorna la lista completa de premios recibidos.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<PremioRecibidoDto>>> obtenerTodos(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para obtener premios recibidos");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener premios recibidos: {}", username);
        List<PremioRecibidoDto> lista = premioRecibidoService.obtenerTodos();
        return ResponseEntity.ok(new ResponseDto<>(true, "Premios recibidos obtenidos exitosamente", lista));
    }

    @Operation(summary = "Obtener premio recibido por ID", description = "Retorna un premio recibido específico por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PremioRecibidoDto>> obtenerPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para obtener premio recibido por ID");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener premio recibido con ID: {}", id);
        PremioRecibidoDto dto = premioRecibidoService.obtenerPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Premio recibido obtenido exitosamente", dto));
    }

    @Operation(summary = "Obtener premios recibidos por usuario", description = "Retorna todos los premios recibidos de un usuario específico.")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<ResponseDto<List<PremioRecibidoDto>>> obtenerPorUserId(
            @PathVariable Integer userId,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para obtener premios recibidos por usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener premios recibidos del usuario ID: {}", userId);
        List<PremioRecibidoDto> lista = premioRecibidoService.obtenerPorUserId(userId);
        return ResponseEntity.ok(new ResponseDto<>(true, "Premios recibidos del usuario obtenidos exitosamente", lista));
    }

    @Operation(summary = "Actualizar premio recibido", description = "Actualiza un premio recibido existente por su ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<PremioRecibidoDto>> actualizar(
            @PathVariable Integer id,
            @RequestBody PremioRecibidoDto dto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para actualizar premio recibido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar premio recibido con ID: {}", id);
        PremioRecibidoDto actualizado = premioRecibidoService.actualizarPremioRecibido(id, dto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Premio recibido actualizado exitosamente", actualizado));
    }

    @Operation(summary = "Eliminar premio recibido", description = "Elimina un premio recibido por su ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminar(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para eliminar premio recibido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar premio recibido con ID: {}", id);
        premioRecibidoService.eliminarPremioRecibido(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Premio recibido eliminado exitosamente", null));
    }
}
