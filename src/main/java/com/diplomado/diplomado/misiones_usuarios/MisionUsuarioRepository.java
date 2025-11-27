package com.diplomado.diplomado.misiones_usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MisionUsuarioRepository extends JpaRepository<MisionUsuarioEntity, Object> {
    // El tipo de la clave (Object) debe ser reemplazado por la clase @Embeddable real.
}
