package com.diplomado.diplomado.categoria;

import java.io.Serializable;

public class CategoriaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombre;

    public CategoriaDto() {
    }

    public CategoriaDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
