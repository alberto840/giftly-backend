package com.diplomado.diplomado.referidos;

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
@RequestMapping(path = "api/v1/referido")
public class ReferidoController {
    private static final Logger logger = LoggerFactory.getLogger(ReferidoController.class);
    private final ReferidoService referidoService;
    private final JwtConfig jwtConfig;

    @Autowired
    public ReferidoController(ReferidoService referidoService, JwtConfig jwtConfig) {
        this.referidoService = referidoService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<ReferidoDto>> crearReferido(
            @RequestBody ReferidoDto referidoDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear referido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear referido: {}", username);
        ReferidoDto nuevoReferido = referidoService.crearReferido(referidoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Referido creado exitosamente", nuevoReferido));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ReferidoDto>>> obtenerTodosLosReferidos(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener referidos");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener referidos: {}", username);
        List<ReferidoDto> referidos = referidoService.obtenerTodosLosReferidos();
        return ResponseEntity.ok(new ResponseDto<>(true, "Referidos obtenidos exitosamente", referidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ReferidoDto>> obtenerReferidoPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener referido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener referido con ID: {}", id);
        ReferidoDto referido = referidoService.obtenerReferidoPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Referido obtenido exitosamente", referido));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<ReferidoDto>> actualizarReferido(
            @PathVariable Integer id,
            @RequestBody ReferidoDto referidoDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar referido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar referido con ID: {}", id);
        ReferidoDto referidoActualizado = referidoService.actualizarReferido(id, referidoDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Referido actualizado exitosamente", referidoActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarReferido(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar referido");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar referido con ID: {}", id);
        referidoService.eliminarReferido(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Referido eliminado exitosamente", null));
    }
}
