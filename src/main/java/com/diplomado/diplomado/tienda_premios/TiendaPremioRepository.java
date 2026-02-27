package com.diplomado.diplomado.tienda_premios;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TiendaPremioRepository extends JpaRepository<TiendaPremioEntity, Integer> {
    List<TiendaPremioEntity> findByTipo(String tipo);
}