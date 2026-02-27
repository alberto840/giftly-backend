package com.diplomado.diplomado.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TelegramService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramService.class);
    private static final String TELEGRAM_SEND_PHOTO_URL = "https://api.telegram.org/bot%s/sendPhoto";

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chatId}")
    private String chatId;

    private final RestTemplate restTemplate;

    public TelegramService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarComprobante(MultipartFile file, Integer pedidoId) {
        String caption = String.format("📦 Comprobante de pago recibido\n🆔 Pedido ID: %d", pedidoId);
        enviarFoto(file, caption);
    }

    /**
     * Envía una foto con un texto (caption) personalizado.
     */
    public void enviarFoto(MultipartFile file, String caption) {
        String url = String.format(TELEGRAM_SEND_PHOTO_URL, botToken);

        try {
            byte[] imageBytes = file.getBytes();
            String filename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "imagen.jpg";

            ByteArrayResource imageResource = new ByteArrayResource(imageBytes) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("chat_id", chatId);
            body.add("caption", caption);
            body.add("photo", imageResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, requestEntity, String.class);
            logger.info("Notificación enviada a Telegram con éxito.");

        } catch (IOException e) {
            logger.error("Error al procesar imagen: {}", e.getMessage());
            throw new RuntimeException("Error al procesar imagen", e);
        } catch (Exception e) {
            logger.error("Error al enviar a Telegram: {}", e.getMessage());
            throw new RuntimeException("Error en comunicación con Telegram", e);
        }
    }
}
