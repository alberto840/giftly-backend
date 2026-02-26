package com.diplomado.diplomado.qr;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QrRepository extends JpaRepository<QrEntity, Integer> {
    QrEntity findFirstByOrderByIdDesc();
}