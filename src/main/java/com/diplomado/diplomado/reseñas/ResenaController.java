package com.diplomado.diplomado.reseñas;

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
@RequestMapping(path = "api/v1/resena")
public class ResenaController {
    private static final Logger logger = LoggerFactory.getLogger(ResenaController.class);
    private final ResenaService resenaService;
    private final JwtConfig jwtConfig;

    @Autowired
    public ResenaController(ResenaService resenaService, JwtConfig jwtConfig) {
        this.resenaService = resenaService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<ResenaDto>> crearResena(
            @RequestBody ResenaDto resenaDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear resena");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear resena: {}", username);
        ResenaDto nuevaResena = resenaService.crearResena(resenaDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Resena creada exitosamente", nuevaResena));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ResenaDto>>> obtenerTodasLasResenas(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener resenas");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener resenas: {}", username);
        List<ResenaDto> resenas = resenaService.obtenerTodasLasResenas();
        return ResponseEntity.ok(new ResponseDto<>(true, "Resenas obtenidas exitosamente", resenas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ResenaDto>> obtenerResenaPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener resena");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener resena con ID: {}", id);
        ResenaDto resena = resenaService.obtenerResenaPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Resena obtenida exitosamente", resena));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<ResenaDto>> actualizarResena(
            @PathVariable Integer id,
            @RequestBody ResenaDto resenaDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar resena");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar resena con ID: {}", id);
        ResenaDto resenaActualizada = resenaService.actualizarResena(id, resenaDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Resena actualizada exitosamente", resenaActualizada));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarResena(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar resena");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar resena con ID: {}", id);
        resenaService.eliminarResena(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Resena eliminada exitosamente", null));
    }
}
