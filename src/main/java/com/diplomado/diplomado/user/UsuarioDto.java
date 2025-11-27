package com.diplomado.diplomado.user;

import java.io.Serializable;
import java.sql.Date;

public class UsuarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombreCompl;
    private String email;
    private Date fechaNacimien;
    private Integer puntos;
    private Integer rolId;

    public UsuarioDto() {
    }

    public UsuarioDto(Integer id, String nombreCompl, String email, Date fechaNacimien, Integer puntos, Integer rolId) {
        this.id = id;
        this.nombreCompl = nombreCompl;
        this.email = email;
        this.fechaNacimien = fechaNacimien;
        this.puntos = puntos;
        this.rolId = rolId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompl() {
        return nombreCompl;
    }

    public void setNombreCompl(String nombreCompl) {
        this.nombreCompl = nombreCompl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimien() {
        return fechaNacimien;
    }

    public void setFechaNacimien(Date fechaNacimien) {
        this.fechaNacimien = fechaNacimien;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }
}
