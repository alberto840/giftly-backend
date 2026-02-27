package com.diplomado.diplomado.niveles;

import java.io.Serializable;
import java.math.BigDecimal;

public class NivelesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private BigDecimal exp;
    private String nombre;

    public NivelesDto() {
    }

    public NivelesDto(Integer id, BigDecimal exp, String nombre) {
        this.id = id;
        this.exp = exp;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getExp() {
        return exp;
    }

    public void setExp(BigDecimal exp) {
        this.exp = exp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
