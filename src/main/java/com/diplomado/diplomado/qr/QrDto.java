package com.diplomado.diplomado.qr;

import java.io.Serializable;
import java.sql.Date;

public class QrDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date fechaCreacion;
    private Date fechaExpiracion;
    private Integer pedidoId;
    private String imageUrl;

    public QrDto() {
    }

    public QrDto(Integer id, Date fechaCreacion, Date fechaExpiracion, Integer pedidoId, String imageUrl) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.fechaExpiracion = fechaExpiracion;
        this.pedidoId = pedidoId;
        this.imageUrl = imageUrl;
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

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
