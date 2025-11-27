package com.diplomado.diplomado.misiones_usuarios;

import java.io.Serializable;

public class MisionUsuarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Boolean estado;
    private Integer misionId;
    private Integer usuarioId;

    public MisionUsuarioDto() {
    }

    public MisionUsuarioDto(Integer id, Boolean estado, Integer misionId, Integer usuarioId) {
        this.id = id;
        this.estado = estado;
        this.misionId = misionId;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getMisionId() {
        return misionId;
    }

    public void setMisionId(Integer misionId) {
        this.misionId = misionId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
