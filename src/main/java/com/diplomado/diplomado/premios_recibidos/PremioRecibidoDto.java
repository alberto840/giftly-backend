package com.diplomado.diplomado.premios_recibidos;

import java.io.Serializable;
import java.util.Date;

public class PremioRecibidoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Boolean recibido;
    private Date fechaRecibido;
    private String tipo;
    private Integer tiendaPremioId;
    private Integer userId;

    public PremioRecibidoDto() {
    }

    public PremioRecibidoDto(Integer id, Boolean recibido, Date fechaRecibido, String tipo,
            Integer tiendaPremioId, Integer userId) {
        this.id = id;
        this.recibido = recibido;
        this.fechaRecibido = fechaRecibido;
        this.tipo = tipo;
        this.tiendaPremioId = tiendaPremioId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRecibido() {
        return recibido;
    }

    public void setRecibido(Boolean recibido) {
        this.recibido = recibido;
    }

    public Date getFechaRecibido() {
        return fechaRecibido;
    }

    public void setFechaRecibido(Date fechaRecibido) {
        this.fechaRecibido = fechaRecibido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getTiendaPremioId() {
        return tiendaPremioId;
    }

    public void setTiendaPremioId(Integer tiendaPremioId) {
        this.tiendaPremioId = tiendaPremioId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
