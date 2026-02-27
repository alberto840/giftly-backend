package com.diplomado.diplomado.telegram;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Telegram", description = "Endpoints para enviar notificaciones a Telegram")
public class TelegramController {

    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping(value = "/enviar-comprobante", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Enviar comprobante de pago a Telegram", 
               description = "Recibe una imagen y la reenvía al bot de Telegram configurado")
    public ResponseEntity<String> enviarComprobante(
            @RequestParam("file") MultipartFile file,
            @RequestParam("pedidoId") Integer pedidoId) {
        
        telegramService.enviarComprobante(file, pedidoId);
        return ResponseEntity.ok("Comprobante enviado a Telegram correctamente");
    }
}