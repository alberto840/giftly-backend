package com.diplomado.diplomado.ubicacion;

import java.io.Serializable;

public class UbicacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String longitud;
    private String latitud;

    public UbicacionDto() {
    }

    public UbicacionDto(Integer id, String longitud, String latitud) {
        this.id = id;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
}
