package com.diplomado.diplomado.auth;

import com.diplomado.diplomado.user.UsuarioDto;
import lombok.Data;

@Data
public class RegisterRequestDto {
    private UsuarioDto usuario;
    private String password;
}
