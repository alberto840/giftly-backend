package com.diplomado.diplomado.referidos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

import com.diplomado.diplomado.roles.RolEntity;
import com.diplomado.diplomado.user.UsuarioEntity;

@Entity
@Table(name = "referidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", length = 150)
    private String codigo;

    @Column(name = "cantidad_invita")
    private Integer cantidadInvita;

    // Clave Foránea a Usuario (el usuario que refiere)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private UsuarioEntity usuario;

    // Clave Foránea a Rol (el rol que aplica el referido)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private RolEntity rol;
}
