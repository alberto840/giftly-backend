package com.diplomado.diplomado.tienda_premios;

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
@RequestMapping(path = "api/v1/tienda-premio")
@Tag(name = "TiendaPremio", description = "Prize Shop management endpoints")
public class TiendaPremioController {
    private static final Logger logger = LoggerFactory.getLogger(TiendaPremioController.class);
    private final TiendaPremioService tiendaPremioService;
    private final JwtConfig jwtConfig;

    @Autowired
    public TiendaPremioController(TiendaPremioService tiendaPremioService, JwtConfig jwtConfig) {
        this.tiendaPremioService = tiendaPremioService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Create prize shop item", description = "Creates a new prize shop item. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<TiendaPremioDto>> crearTiendaPremio(
            @RequestBody TiendaPremioDto tiendaPremioDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear tienda premio");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear tienda premio: {}", username);
        TiendaPremioDto nuevaTiendaPremio = tiendaPremioService.crearTiendaPremio(tiendaPremioDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Tienda premio creada exitosamente", nuevaTiendaPremio));
    }

    @Operation(summary = "Get all prize shop items", description = "Retrieves a list of all prize shop items.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<TiendaPremioDto>>> obtenerTodasLasTiendaPremios(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener tienda premios");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener tienda premios: {}", username);
        List<TiendaPremioDto> tiendaPremios = tiendaPremioService.obtenerTodasLasTiendaPremios();
        return ResponseEntity.ok(new ResponseDto<>(true, "Tienda premios obtenidas exitosamente", tiendaPremios));
    }

    @Operation(summary = "Get prize shop item by ID", description = "Retrieves a specific prize shop item by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TiendaPremioDto>> obtenerTiendaPremioPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener tienda premio");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener tienda premio con ID: {}", id);
        TiendaPremioDto tiendaPremio = tiendaPremioService.obtenerTiendaPremioPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Tienda premio obtenida exitosamente", tiendaPremio));
    }

    @Operation(summary = "Update prize shop item", description = "Updates an existing prize shop item by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<TiendaPremioDto>> actualizarTiendaPremio(
            @PathVariable Integer id,
            @RequestBody TiendaPremioDto tiendaPremioDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar tienda premio");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar tienda premio con ID: {}", id);
        TiendaPremioDto tiendaPremioActualizada = tiendaPremioService.actualizarTiendaPremio(id, tiendaPremioDto);
        return ResponseEntity
                .ok(new ResponseDto<>(true, "Tienda premio actualizada exitosamente", tiendaPremioActualizada));
    }

    @Operation(summary = "Delete prize shop item", description = "Deletes a prize shop item by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarTiendaPremio(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar tienda premio");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar tienda premio con ID: {}", id);
        tiendaPremioService.eliminarTiendaPremio(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Tienda premio eliminada exitosamente", null));
    }
}
