package com.diplomado.diplomado.misiones_usuarios;

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
@RequestMapping(path = "api/v1/mision-usuario")
@Tag(name = "MisionUsuario", description = "Mission-User relationship management endpoints")
public class MisionUsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(MisionUsuarioController.class);
    private final MisionUsuarioService misionUsuarioService;
    private final JwtConfig jwtConfig;

    @Autowired
    public MisionUsuarioController(MisionUsuarioService misionUsuarioService, JwtConfig jwtConfig) {
        this.misionUsuarioService = misionUsuarioService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Assign mission to user", description = "Creates a new mission-user relationship. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<MisionUsuarioDto>> crearMisionUsuario(
            @RequestBody MisionUsuarioDto misionUsuarioDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear mision usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear mision usuario: {}", username);
        MisionUsuarioDto nuevaMisionUsuario = misionUsuarioService.crearMisionUsuario(misionUsuarioDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision usuario creada exitosamente", nuevaMisionUsuario));
    }

    @Operation(summary = "Get all mission-user relationships", description = "Retrieves a list of all mission-user relationships.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<MisionUsuarioDto>>> obtenerTodasLasMisionesUsuarios(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener misiones usuarios");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener misiones usuarios: {}", username);
        List<MisionUsuarioDto> misionesUsuarios = misionUsuarioService.obtenerTodasLasMisionesUsuarios();
        return ResponseEntity.ok(new ResponseDto<>(true, "Misiones usuarios obtenidas exitosamente", misionesUsuarios));
    }

    @Operation(summary = "Get mission-user relationship by ID", description = "Retrieves a specific mission-user relationship by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MisionUsuarioDto>> obtenerMisionUsuarioPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener mision usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener mision usuario con ID: {}", id);
        MisionUsuarioDto misionUsuario = misionUsuarioService.obtenerMisionUsuarioPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision usuario obtenida exitosamente", misionUsuario));
    }

    @Operation(summary = "Update mission-user relationship", description = "Updates an existing mission-user relationship by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<MisionUsuarioDto>> actualizarMisionUsuario(
            @PathVariable Integer id,
            @RequestBody MisionUsuarioDto misionUsuarioDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar mision usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar mision usuario con ID: {}", id);
        MisionUsuarioDto misionUsuarioActualizada = misionUsuarioService.actualizarMisionUsuario(id, misionUsuarioDto);
        return ResponseEntity
                .ok(new ResponseDto<>(true, "Mision usuario actualizada exitosamente", misionUsuarioActualizada));
    }

    @Operation(summary = "Delete mission-user relationship", description = "Deletes a mission-user relationship by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarMisionUsuario(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar mision usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar mision usuario con ID: {}", id);
        misionUsuarioService.eliminarMisionUsuario(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision usuario eliminada exitosamente", null));
    }
}
