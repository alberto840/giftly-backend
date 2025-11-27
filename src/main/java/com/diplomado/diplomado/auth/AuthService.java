package com.diplomado.diplomado.auth;

import com.diplomado.diplomado.config.JwtConfig;
import com.diplomado.diplomado.user.UsuarioDto;
import com.diplomado.diplomado.user.UsuarioEntity;
import com.diplomado.diplomado.user.UsuarioRepository;
import com.diplomado.diplomado.user.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final JwtConfig jwtConfig;

    public AuthService(AuthRepository authRepository, UsuarioRepository usuarioRepository,
            UsuarioService usuarioService, JwtConfig jwtConfig) {
        this.authRepository = authRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.jwtConfig = jwtConfig;
    }

    public AuthResponseDto login(AuthDto authDto) {
        Optional<AuthEntity> authEntityOptional = authRepository.findByEmail(authDto.getEmail());

        if (authEntityOptional.isPresent()) {
            AuthEntity authEntity = authEntityOptional.get();
            if (authEntity.getPassword().equals(authDto.getPassword())) {
                String token = jwtConfig.generateToken(authEntity.getEmail());
                UsuarioDto usuarioDto = usuarioService.obtenerUsuarioPorId(authEntity.getUsuario().getId());
                return new AuthResponseDto(token, usuarioDto);
            }
        }
        throw new RuntimeException("Credenciales inválidas");
    }

    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
        // Primero crear el usuario
        UsuarioDto usuarioDto = usuarioService.crearUsuario(registerRequest.getUsuario());

        // Buscar la entidad usuario recién creada para vincularla
        UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioDto.getId())
                .orElseThrow(() -> new RuntimeException("Error al crear usuario"));

        // Crear la entidad de autenticación
        AuthEntity authEntity = new AuthEntity();
        authEntity.setEmail(usuarioDto.getEmail());
        authEntity.setPassword(registerRequest.getPassword()); // En producción usar BCrypt
        authEntity.setUsuario(usuarioEntity);

        authRepository.save(authEntity);

        String token = jwtConfig.generateToken(authEntity.getEmail());
        return new AuthResponseDto(token, usuarioDto);
    }
}
