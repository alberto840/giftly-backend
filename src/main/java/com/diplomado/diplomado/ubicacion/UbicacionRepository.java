package com.diplomado.diplomado.ubicacion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Integer> {
    List<UbicacionEntity> findByUsuarioId(Integer usuarioId);
}