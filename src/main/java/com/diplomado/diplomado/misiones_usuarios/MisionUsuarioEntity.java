package com.diplomado.diplomado.misiones_usuarios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

import com.diplomado.diplomado.misiones.MisionEntity;
import com.diplomado.diplomado.user.UsuarioEntity;

@Entity
@Table(name = "misiones_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MisionUsuarioEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estado")
    private Boolean estado;

    // Clave Foránea a Mision
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "misiones_id", referencedColumnName = "id")
    private MisionEntity mision;

    // Clave Foránea a Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private UsuarioEntity usuario;
}