package com.diplomado.diplomado.reseñas;

import java.io.Serializable;

public class ResenaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer calificacion;
    private String comentario;
    private Integer usuarioId;
    private Integer productoId;

    public ResenaDto() {
    }

    public ResenaDto(Integer id, Integer calificacion, String comentario, Integer usuarioId, Integer productoId) {
        this.id = id;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
