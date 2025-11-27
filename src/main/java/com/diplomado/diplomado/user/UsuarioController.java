package com.diplomado.diplomado.user;

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
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;
    private final JwtConfig jwtConfig;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, JwtConfig jwtConfig) {
        this.usuarioService = usuarioService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<UsuarioDto>> crearUsuario(
            @RequestBody UsuarioDto usuarioDto,
            @RequestHeader("Authorization") String token) {

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

    @GetMapping
    public ResponseEntity<ResponseDto<List<UsuarioDto>>> obtenerTodosLosUsuarios(
            @RequestHeader("Authorization") String token) {

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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> obtenerUsuarioPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

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

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioDto usuarioDto,
            @RequestHeader("Authorization") String token) {

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

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarUsuario(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

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
}
