package com.diplomado.diplomado.roles;

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
@RequestMapping(path = "api/v1/rol")
public class RolController {
    private static final Logger logger = LoggerFactory.getLogger(RolController.class);
    private final RolService rolService;
    private final JwtConfig jwtConfig;

    @Autowired
    public RolController(RolService rolService, JwtConfig jwtConfig) {
        this.rolService = rolService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<RolDto>> crearRol(
            @RequestBody RolDto rolDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear rol");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear rol: {}", username);
        RolDto nuevoRol = rolService.crearRol(rolDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Rol creado exitosamente", nuevoRol));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RolDto>>> obtenerTodosLosRoles(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener roles");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener roles: {}", username);
        List<RolDto> roles = rolService.obtenerTodosLosRoles();
        return ResponseEntity.ok(new ResponseDto<>(true, "Roles obtenidos exitosamente", roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RolDto>> obtenerRolPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener rol");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener rol con ID: {}", id);
        RolDto rol = rolService.obtenerRolPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Rol obtenido exitosamente", rol));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<RolDto>> actualizarRol(
            @PathVariable Integer id,
            @RequestBody RolDto rolDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar rol");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar rol con ID: {}", id);
        RolDto rolActualizado = rolService.actualizarRol(id, rolDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Rol actualizado exitosamente", rolActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarRol(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar rol");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar rol con ID: {}", id);
        rolService.eliminarRol(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Rol eliminado exitosamente", null));
    }
}
