package com.diplomado.diplomado.niveles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "niveles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "exp", nullable = false, precision = 10, scale = 2)
    private BigDecimal exp;

    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;
}
