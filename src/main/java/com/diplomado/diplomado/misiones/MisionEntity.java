package com.diplomado.diplomado.misiones;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.diplomado.diplomado.misiones_usuarios.MisionUsuarioEntity;

@Entity
@Table(name = "misiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MisionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "objetivo", length = 255)
    private String objetivo;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creaci")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_final")
    private Date fechaFinal;

    @Column(name = "premio_punt")
    private Integer premioPuntos;

    // Relación OneToMany con Misiones_usuarios (la tabla de unión)
    @OneToMany(mappedBy = "mision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MisionUsuarioEntity> misionesUsuarios;
}