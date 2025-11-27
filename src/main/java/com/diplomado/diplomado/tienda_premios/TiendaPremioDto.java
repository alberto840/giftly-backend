package com.diplomado.diplomado.tienda_premios;

import java.io.Serializable;

public class TiendaPremioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer precioPunto;
    private Integer productoId;

    public TiendaPremioDto() {
    }

    public TiendaPremioDto(Integer id, Integer precioPunto, Integer productoId) {
        this.id = id;
        this.precioPunto = precioPunto;
        this.productoId = productoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrecioPunto() {
        return precioPunto;
    }

    public void setPrecioPunto(Integer precioPunto) {
        this.precioPunto = precioPunto;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
