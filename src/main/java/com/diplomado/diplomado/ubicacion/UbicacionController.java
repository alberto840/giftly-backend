package com.diplomado.diplomado.ubicacion;

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
@RequestMapping(path = "api/v1/ubicacion")
public class UbicacionController {
    private static final Logger logger = LoggerFactory.getLogger(UbicacionController.class);
    private final UbicacionService ubicacionService;
    private final JwtConfig jwtConfig;

    @Autowired
    public UbicacionController(UbicacionService ubicacionService, JwtConfig jwtConfig) {
        this.ubicacionService = ubicacionService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<UbicacionDto>> crearUbicacion(
            @RequestBody UbicacionDto ubicacionDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear ubicacion");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear ubicacion: {}", username);
        UbicacionDto nuevaUbicacion = ubicacionService.crearUbicacion(ubicacionDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Ubicacion creada exitosamente", nuevaUbicacion));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<UbicacionDto>>> obtenerTodasLasUbicaciones(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener ubicaciones");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener ubicaciones: {}", username);
        List<UbicacionDto> ubicaciones = ubicacionService.obtenerTodasLasUbicaciones();
        return ResponseEntity.ok(new ResponseDto<>(true, "Ubicaciones obtenidas exitosamente", ubicaciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UbicacionDto>> obtenerUbicacionPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener ubicacion");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener ubicacion con ID: {}", id);
        UbicacionDto ubicacion = ubicacionService.obtenerUbicacionPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Ubicacion obtenida exitosamente", ubicacion));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<UbicacionDto>> actualizarUbicacion(
            @PathVariable Integer id,
            @RequestBody UbicacionDto ubicacionDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar ubicacion");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar ubicacion con ID: {}", id);
        UbicacionDto ubicacionActualizada = ubicacionService.actualizarUbicacion(id, ubicacionDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Ubicacion actualizada exitosamente", ubicacionActualizada));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarUbicacion(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar ubicacion");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar ubicacion con ID: {}", id);
        ubicacionService.eliminarUbicacion(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Ubicacion eliminada exitosamente", null));
    }
}
