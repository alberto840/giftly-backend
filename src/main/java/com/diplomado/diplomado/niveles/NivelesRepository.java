package com.diplomado.diplomado.niveles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelesRepository extends JpaRepository<NivelesEntity, Integer> {
}
