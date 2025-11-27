package com.diplomado.diplomado.auth;

import com.diplomado.diplomado.user.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private UsuarioDto usuario;
}
