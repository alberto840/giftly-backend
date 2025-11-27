package com.diplomado.diplomado.detalle_pedido;

import java.io.Serializable;

public class DetallePedidoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String mensaje;
    private String instrucciones;
    private String receptorEncarga;
    private String celular1;
    private String celular2;
    private String nombreObjetivo;
    private Integer pedidoId;
    private Integer ubicacionId;

    public DetallePedidoDto() {
    }

    public DetallePedidoDto(Integer id, String mensaje, String instrucciones, String receptorEncarga,
            String celular1, String celular2, String nombreObjetivo, Integer pedidoId, Integer ubicacionId) {
        this.id = id;
        this.mensaje = mensaje;
        this.instrucciones = instrucciones;
        this.receptorEncarga = receptorEncarga;
        this.celular1 = celular1;
        this.celular2 = celular2;
        this.nombreObjetivo = nombreObjetivo;
        this.pedidoId = pedidoId;
        this.ubicacionId = ubicacionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getReceptorEncarga() {
        return receptorEncarga;
    }

    public void setReceptorEncarga(String receptorEncarga) {
        this.receptorEncarga = receptorEncarga;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(Integer ubicacionId) {
        this.ubicacionId = ubicacionId;
    }
}
