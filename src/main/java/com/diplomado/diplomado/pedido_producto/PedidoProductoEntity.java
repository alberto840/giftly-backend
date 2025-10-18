package com.diplomado.diplomado.pedido_producto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.producto.ProductoEntity;

@Entity
@Table(name = "pedido_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    // Clave Foránea a Pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private PedidoEntity pedido;

    // Clave Foránea a Producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private ProductoEntity producto;
}
