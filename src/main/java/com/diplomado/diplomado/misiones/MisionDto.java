package com.diplomado.diplomado.misiones;

import java.io.Serializable;
import java.sql.Date; // Usamos java.sql.Date para las fechas del diagrama

public class MisionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String objetivo;
    private String titulo;
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaFinal;
    private Integer premioPunt;

    public MisionDto() {
    }

    public MisionDto(Integer id, String objetivo, String titulo, String descripcion, Date fechaCreacion,
            Date fechaFinal, Integer premioPunt) {
        this.id = id;
        this.objetivo = objetivo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaFinal = fechaFinal;
        this.premioPunt = premioPunt;
    }

    // Getters y Setters...
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getPremioPunt() {
        return premioPunt;
    }

    public void setPremioPunt(Integer premioPunt) {
        this.premioPunt = premioPunt;
    }
}