package com.diplomado.diplomado.ubicacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.diplomado.diplomado.detalle_pedido.DetallePedidoEntity;
import com.diplomado.diplomado.user.UsuarioEntity;

@Entity
@Table(name = "ubicacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "longitud", length = 150)
    private String longitud;

    @Column(name = "latitud", length = 150)
    private String latitud;

    @Column(name = "detalle", length = 300)
    private String detalle;

    // Relación OneToMany con Detalle_pedido
    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedidoEntity> detallePedidos;

    // Relación ManyToOne con Usuarios
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private UsuarioEntity usuario;
}
