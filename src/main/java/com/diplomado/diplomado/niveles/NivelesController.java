package com.diplomado.diplomado.niveles;

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
@RequestMapping(path = "api/v1/niveles")
@Tag(name = "Niveles", description = "Endpoints para la gestión de niveles")
public class NivelesController {
    private static final Logger logger = LoggerFactory.getLogger(NivelesController.class);
    private final NivelesService nivelesService;
    private final JwtConfig jwtConfig;

    @Autowired
    public NivelesController(NivelesService nivelesService, JwtConfig jwtConfig) {
        this.nivelesService = nivelesService;
        this.jwtConfig = jwtConfig;
    }

    @Operation(summary = "Crear un nivel", description = "Crea un nuevo nivel. Requiere autenticación.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<NivelesDto>> crearNivel(
            @RequestBody NivelesDto nivelesDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear nivel");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear nivel: {}", username);
        NivelesDto nuevoNivel = nivelesService.crearNivel(nivelesDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Nivel creado exitosamente", nuevoNivel));
    }

    @Operation(summary = "Obtener todos los niveles", description = "Recupera una lista de todos los niveles.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<NivelesDto>>> obtenerTodosLosNiveles(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener niveles");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener niveles: {}", username);
        List<NivelesDto> niveles = nivelesService.obtenerTodosLosNiveles();
        return ResponseEntity.ok(new ResponseDto<>(true, "Niveles obtenidos exitosamente", niveles));
    }

    @Operation(summary = "Obtener nivel por ID", description = "Recupera un nivel específico por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<NivelesDto>> obtenerNivelPorId(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener nivel");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener nivel con ID: {}", id);
        NivelesDto nivel = nivelesService.obtenerNivelPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Nivel obtenido exitosamente", nivel));
    }

    @Operation(summary = "Actualizar nivel", description = "Actualiza un nivel existente por su ID.")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<NivelesDto>> actualizarNivel(
            @PathVariable Integer id,
            @RequestBody NivelesDto nivelesDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar nivel");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar nivel con ID: {}", id);
        NivelesDto nivelActualizado = nivelesService.actualizarNivel(id, nivelesDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Nivel actualizado exitosamente", nivelActualizado));
    }

    @Operation(summary = "Eliminar nivel", description = "Elimina un nivel por su ID.")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarNivel(
            @PathVariable Integer id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar nivel");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar nivel con ID: {}", id);
        nivelesService.eliminarNivel(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Nivel eliminado exitosamente", null));
    }
}
