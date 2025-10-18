package com.diplomado.diplomado.pedido;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.diplomado.diplomado.detalle_pedido.DetallePedidoEntity;
import com.diplomado.diplomado.pedido_producto.PedidoProductoEntity;
import com.diplomado.diplomado.qr.QrEntity;
import com.diplomado.diplomado.tienda_permisos.TiendaPremioEntity;
import com.diplomado.diplomado.user.UsuarioEntity;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Column(name = "total", precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "QR_id")
    private Integer qrId; // Se mantiene como campo simple ya que la relación es 1 a 1 y 'QR_id' es la FK en 'pedido'

    // Clave Foránea a Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private UsuarioEntity usuario;

    // Relación ManyToOne con Tienda_premios
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_premios_id", referencedColumnName = "id")
    private TiendaPremioEntity tiendaPremio;

    // Relación OneToOne con QR (si QR_id es la FK, esta es la relación bidireccional)
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private QrEntity qr;

    // Relación OneToMany con Pedido_producto (la tabla de unión)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProductoEntity> pedidoProductos;

    // Relación OneToOne con Detalle_pedido
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetallePedidoEntity detallePedido;
}