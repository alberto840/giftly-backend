package com.diplomado.diplomado.misiones;

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
@RequestMapping(path = "api/v1/mision")
public class MisionController {
    private static final Logger logger = LoggerFactory.getLogger(MisionController.class);
    private final MisionService misionService;
    private final JwtConfig jwtConfig;

    @Autowired
    public MisionController(MisionService misionService, JwtConfig jwtConfig) {
        this.misionService = misionService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<MisionDto>> crearMision(
            @RequestBody MisionDto misionDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear mision");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear mision: {}", username);
        MisionDto nuevaMision = misionService.crearMision(misionDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision creada exitosamente", nuevaMision));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<MisionDto>>> obtenerTodasLasMisiones(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener misiones");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener misiones: {}", username);
        List<MisionDto> misiones = misionService.obtenerTodasLasMisiones();
        return ResponseEntity.ok(new ResponseDto<>(true, "Misiones obtenidas exitosamente", misiones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MisionDto>> obtenerMisionPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener mision");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener mision con ID: {}", id);
        MisionDto mision = misionService.obtenerMisionPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision obtenida exitosamente", mision));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<MisionDto>> actualizarMision(
            @PathVariable Integer id,
            @RequestBody MisionDto misionDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar mision");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar mision con ID: {}", id);
        MisionDto misionActualizada = misionService.actualizarMision(id, misionDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision actualizada exitosamente", misionActualizada));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarMision(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar mision");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar mision con ID: {}", id);
        misionService.eliminarMision(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Mision eliminada exitosamente", null));
    }
}
