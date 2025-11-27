package com.diplomado.diplomado.pedido_producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProductoRepository extends JpaRepository<PedidoProductoEntity, Object> {
    // El tipo de la clave (Object) debe ser reemplazado por la clase @Embeddable real.
}