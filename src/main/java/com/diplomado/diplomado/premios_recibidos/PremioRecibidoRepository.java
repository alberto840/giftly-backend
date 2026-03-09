package com.diplomado.diplomado.premios_recibidos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PremioRecibidoRepository extends JpaRepository<PremioRecibidoEntity, Integer> {
    List<PremioRecibidoEntity> findByUsuarioId(Integer userId);
}
