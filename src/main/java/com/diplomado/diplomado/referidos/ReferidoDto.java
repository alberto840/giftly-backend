package com.diplomado.diplomado.referidos;

import java.io.Serializable;

public class ReferidoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private Integer cantidadInvita;
    private Integer usuarioId;
    private Integer rolId;

    public ReferidoDto() {
    }

    public ReferidoDto(Integer id, String codigo, Integer cantidadInvita, Integer usuarioId, Integer rolId) {
        this.id = id;
        this.codigo = codigo;
        this.cantidadInvita = cantidadInvita;
        this.usuarioId = usuarioId;
        this.rolId = rolId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidadInvita() {
        return cantidadInvita;
    }

    public void setCantidadInvita(Integer cantidadInvita) {
        this.cantidadInvita = cantidadInvita;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }
}
