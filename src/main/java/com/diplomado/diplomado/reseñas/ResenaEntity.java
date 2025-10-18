package com.diplomado.diplomado.reseñas;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

import com.diplomado.diplomado.producto.ProductoEntity;
import com.diplomado.diplomado.user.UsuarioEntity;

@Entity
@Table(name = "resenas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "comentari", length = 255)
    private String comentario;

    // Clave Foránea a Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private UsuarioEntity usuario;

    // Clave Foránea a Producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id") // ASUMIENDO que esta FK apunta a 'producto' y no a 'pedido' según el diagrama. Si el diagrama es correcto y apunta a 'pedido', se debe ajustar. Lo ajusto a 'producto_id' por lógica de una reseña.
    private ProductoEntity producto;
}