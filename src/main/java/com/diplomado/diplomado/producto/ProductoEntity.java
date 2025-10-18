package com.diplomado.diplomado.producto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.diplomado.diplomado.categoria.CategoriaEntity;
import com.diplomado.diplomado.pedido_producto.PedidoProductoEntity;
import com.diplomado.diplomado.reseñas.ResenaEntity;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precio", precision = 15, scale = 2)
    private BigDecimal precio;

    // Relación ManyToOne con Categoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private CategoriaEntity categoria;

    // Relación OneToMany con Reseñas
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResenaEntity> resenas;

    // Relación OneToMany con Pedido_producto (la tabla de unión)
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProductoEntity> pedidoProductos;
}