package com.diplomado.diplomado.pedido_producto;

import java.io.Serializable;

public class PedidoProductoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer cantidad;
    private Integer pedidoId;
    private Integer productoId;

    public PedidoProductoDto() {
    }

    public PedidoProductoDto(Integer id, Integer cantidad, Integer pedidoId, Integer productoId) {
        this.id = id;
        this.cantidad = cantidad;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
