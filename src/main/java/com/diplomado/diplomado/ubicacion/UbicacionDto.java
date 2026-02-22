package com.diplomado.diplomado.ubicacion;

import java.io.Serializable;

public class UbicacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String longitud;
    private String latitud;
    private String detalle;
    private Integer usuarioId;

    public UbicacionDto() {
    }

    public UbicacionDto(Integer id, String longitud, String latitud, String detalle, Integer usuarioId) {
        this.id = id;
        this.longitud = longitud;
        this.latitud = latitud;
        this.detalle = detalle;
        this.usuarioId = usuarioId;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
