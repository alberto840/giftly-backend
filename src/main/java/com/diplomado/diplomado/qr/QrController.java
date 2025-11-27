package com.diplomado.diplomado.qr;

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
@RequestMapping(path = "api/v1/qr")
public class QrController {
    private static final Logger logger = LoggerFactory.getLogger(QrController.class);
    private final QrService qrService;
    private final JwtConfig jwtConfig;

    @Autowired
    public QrController(QrService qrService, JwtConfig jwtConfig) {
        this.qrService = qrService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseDto<QrDto>> crearQr(
            @RequestBody QrDto qrDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para crear qr");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para crear qr: {}", username);
        QrDto nuevoQr = qrService.crearQr(qrDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Qr creado exitosamente", nuevoQr));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<QrDto>>> obtenerTodosLosQrs(
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener qrs");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener qrs: {}", username);
        List<QrDto> qrs = qrService.obtenerTodosLosQrs();
        return ResponseEntity.ok(new ResponseDto<>(true, "Qrs obtenidos exitosamente", qrs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<QrDto>> obtenerQrPorId(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para obtener qr");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para obtener qr con ID: {}", id);
        QrDto qr = qrService.obtenerQrPorId(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Qr obtenido exitosamente", qr));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDto<QrDto>> actualizarQr(
            @PathVariable Integer id,
            @RequestBody QrDto qrDto,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para actualizar qr");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para actualizar qr con ID: {}", id);
        QrDto qrActualizado = qrService.actualizarQr(id, qrDto);
        return ResponseEntity.ok(new ResponseDto<>(true, "Qr actualizado exitosamente", qrActualizado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDto<Void>> eliminarQr(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtConfig.extractUsername(extractedToken);

        if (username == null || !jwtConfig.validateToken(extractedToken, username)) {
            logger.warn("Token inválido o usuario no autorizado para eliminar qr");
            return ResponseEntity.status(401)
                    .body(new ResponseDto<>(false, "Token inválido o usuario no autorizado", null));
        }

        logger.info("Usuario autorizado para eliminar qr con ID: {}", id);
        qrService.eliminarQr(id);
        return ResponseEntity.ok(new ResponseDto<>(true, "Qr eliminado exitosamente", null));
    }
}
