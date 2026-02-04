package com.diplomado.diplomado.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.diplomado.diplomado.utils.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/auth")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns a token")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AuthResponseDto>> login(@RequestBody AuthDto authDto) {
        try {
            logger.info("Intento de login para usuario: {}", authDto.getEmail());
            AuthResponseDto authResponse = authService.login(authDto);
            return ResponseEntity.ok(new ResponseDto<>(true, "Login exitoso", authResponse));
        } catch (RuntimeException e) {
            logger.warn("Login fallido: {}", e.getMessage());
            return ResponseEntity.status(401).body(new ResponseDto<>(false, e.getMessage(), null));
        }
    }

    @Operation(summary = "User registration", description = "Registers a new user and returns a token")
    @ApiResponse(responseCode = "200", description = "Registration successful")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<AuthResponseDto>> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            logger.info("Registro de nuevo usuario: {}", registerRequest.getUsuario().getEmail());
            AuthResponseDto authResponse = authService.register(registerRequest);
            return ResponseEntity.ok(new ResponseDto<>(true, "Registro exitoso", authResponse));
        } catch (Exception e) {
            logger.error("Error en registro: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ResponseDto<>(false, "Error en registro: " + e.getMessage(), null));
        }
    }
}
