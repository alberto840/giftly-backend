package com.diplomado.diplomado.user;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.diplomado.diplomado.misiones_usuarios.MisionUsuarioEntity;
import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.referidos.ReferidoEntity;
import com.diplomado.diplomado.reseñas.ResenaEntity;
import com.diplomado.diplomado.roles.RolEntity;
import com.diplomado.diplomado.ubicacion.UbicacionEntity;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_compl", length = 150)
    private String nombreCompl;

    @Column(name = "email", length = 150)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimien")
    private Date fechaNacimien;

    @Column(name = "puntos")
    private Integer puntos;

    // Relación ManyToOne con Roles
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private RolEntity rol;

    // Relación OneToMany con Misiones_usuarios (la tabla de unión)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MisionUsuarioEntity> misionesUsuarios;

    // Relación OneToMany con Referidos (como dueño/referidor)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReferidoEntity> referidos;

    // Relación OneToMany con Reseñas
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResenaEntity> resenas;

    // Relación OneToMany con Pedidos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoEntity> pedidos;

    // Relación OneToMany con Ubicaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UbicacionEntity> ubicaciones;
}