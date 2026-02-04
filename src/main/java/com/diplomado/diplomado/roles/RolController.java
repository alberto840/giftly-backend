package com.diplomado.diplomado.roles;

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
@RequestMapping(path = "api/v1/rol")
@Tag(name = "Rol", description = "Role management endpoints")
public class RolController {
    private static final Logger logger = LoggerFactory.getLogger(RolController.class);
    private final RolService rolService;
    private final JwtConfig jwtConfig;

    @Autowired
    public RolController(RolService rolService, JwtConfig jwtConfig) {
        this.rolService = rolService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Create role", description = "Creates a new role. Requires authentication.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<RolDto>> crearRol(
            @RequestBody RolDto rolDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

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

    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<RolDto>>> obtenerTodosLosRoles(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

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

    @Operation(summary = "Get role by ID", description = "Retrieves a specific role by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RolDto>> obtenerRolPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

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

    @Operation(summary = "Update role", description = "Updates an existing role by its ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<RolDto>> actualizarRol(
            @PathVariable Integer id,
            @RequestBody RolDto rolDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

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

    @Operation(summary = "Delete role", description = "Deletes a role by its ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarRol(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

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
