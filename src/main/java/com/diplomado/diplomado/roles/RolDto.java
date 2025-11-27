package com.diplomado.diplomado.roles;

import java.io.Serializable;

public class RolDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String descripcion;

    public RolDto() {
    }

    public RolDto(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
