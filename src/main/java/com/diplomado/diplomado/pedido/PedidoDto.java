package com.diplomado.diplomado.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class PedidoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date fechaCreacion;
    private Date fechaEnvio;
    private BigDecimal total;
    private Integer qrId;
    private Integer usuarioId;
    private Integer tiendaPremioId;
    private String status;

    public PedidoDto() {
    }

    public PedidoDto(Integer id, Date fechaCreacion, Date fechaEnvio, BigDecimal total, Integer qrId,
            Integer usuarioId, Integer tiendaPremioId, String status) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.fechaEnvio = fechaEnvio;
        this.total = total;
        this.qrId = qrId;
        this.usuarioId = usuarioId;
        this.tiendaPremioId = tiendaPremioId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getQrId() {
        return qrId;
    }

    public void setQrId(Integer qrId) {
        this.qrId = qrId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getTiendaPremioId() {
        return tiendaPremioId;
    }

    public void setTiendaPremioId(Integer tiendaPremioId) {
        this.tiendaPremioId = tiendaPremioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
