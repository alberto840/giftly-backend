package com.diplomado.diplomado.pedido;

import com.diplomado.diplomado.detalle_pedido.DetallePedidoDto;
import com.diplomado.diplomado.pedido_producto.PedidoProductoDto;

import java.io.Serializable;
import java.util.List;

public class PedidoRegistroRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private PedidoDto pedido;
    private DetallePedidoDto detallePedido;
    private List<PedidoProductoDto> productos;

    public PedidoRegistroRequestDto() {
    }

    public PedidoRegistroRequestDto(PedidoDto pedido, DetallePedidoDto detallePedido, List<PedidoProductoDto> productos) {
        this.pedido = pedido;
        this.detallePedido = detallePedido;
        this.productos = productos;
    }

    public PedidoDto getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDto pedido) {
        this.pedido = pedido;
    }

    public DetallePedidoDto getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedidoDto detallePedido) {
        this.detallePedido = detallePedido;
    }

    public List<PedidoProductoDto> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProductoDto> productos) {
        this.productos = productos;
    }
}
