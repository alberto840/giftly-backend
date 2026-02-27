package com.diplomado.diplomado.tienda_premios;

import java.io.Serializable;
import java.math.BigDecimal;

public class TiendaPremioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer precioPunto;
    private BigDecimal precioExp;
    private String tipo;
    private Integer productoId;

    public TiendaPremioDto() {
    }

    public TiendaPremioDto(Integer id, Integer precioPunto, BigDecimal precioExp, String tipo, Integer productoId) {
        this.id = id;
        this.precioPunto = precioPunto;
        this.precioExp = precioExp;
        this.tipo = tipo;
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

    public BigDecimal getPrecioExp() {
        return precioExp;
    }

    public void setPrecioExp(BigDecimal precioExp) {
        this.precioExp = precioExp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
