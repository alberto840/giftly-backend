package com.diplomado.diplomado.tienda_permisos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.producto.ProductoEntity;

@Entity
@Table(name = "tienda_premios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaPremioEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "precio_punto")
    private Integer precioPunto;

    // Clave Foránea a Producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private ProductoEntity producto;

    // Relación OneToMany con Pedido
    @OneToMany(mappedBy = "tiendaPremio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoEntity> pedidos;
}
