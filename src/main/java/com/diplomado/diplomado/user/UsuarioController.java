package com.diplomado.diplomado.user;

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
@RequestMapping(path = "api/v1/usuario")
@Tag(name = "Usuario", description = "User management endpoints")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;
    private final JwtConfig jwtConfig;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, JwtConfig jwtConfig) {
        this.usuarioService = usuarioService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Create user", description = "Creates a new user. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<UsuarioDto>> crearUsuario(
            @RequestBody UsuarioDto usuarioDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear usuario: {}", username);
        UsuarioDto nuevoUsuario = usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Usuario creado exitosamente", nuevoUsuario));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<UsuarioDto>>> obtenerTodosLosUsuarios(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener usuarios");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener usuarios: {}", username);
        List<UsuarioDto> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(new ResponseDto<>(true, "Usuarios obtenidos exitosamente", usuarios));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> obtenerUsuarioPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener usuario con ID: {}", id);
        UsuarioDto usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Usuario obtenido exitosamente", usuario));
    }

    @Operation(summary = "Update user", description = "Updates an existing user by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioDto usuarioDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar usuario con ID: {}", id);
        UsuarioDto usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Usuario actualizado exitosamente", usuarioActualizado));
    }

    @Operation(summary = "Delete user", description = "Deletes a user by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarUsuario(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar usuario con ID: {}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Usuario eliminado exitosamente", null));
    }

    @Operation(summary = "Modificar exp y puntos de usuario", description = "Suma o resta puntos y exp a un usuario existente.")
    @PutMapping("/modificar-puntos-exp/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> modificarExpYPuntos(
            @PathVariable Integer id,
            @RequestBody ModificarExpPuntosDto request,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para modificar puntos/exp de usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para modificar puntos/exp del usuario con ID: {}", id);
        UsuarioDto actualizado = usuarioService.modificarExpYPuntos(id, request.getDeltaExp(), request.getDeltaPuntos());
        return ResponseEntity.ok(new ResponseDto<>(true, "Puntos y exp actualizados exitosamente", actualizado));
    }

    @Operation(summary = "Obtener puntos del usuario", description = "Retorna solo la cantidad de puntos de un usuario específico.")
    @GetMapping("/puntos/{id}")
    public ResponseEntity<ResponseDto<Integer>> obtenerPuntosDeUsuario(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido para obtener puntos de usuario");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener puntos del usuario con ID: {}", id);
        Integer puntos = usuarioService.obtenerPuntosDeUsuario(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Puntos obtenidos exitosamente", puntos));
    }
}
