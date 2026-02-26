package com.diplomado.diplomado.qr;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import com.diplomado.diplomado.pedido.PedidoEntity;

@Entity
@Table(name = "QR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_expiracion")
    private Date fechaExpiracion;

    @Column(name = "image_url")
    private String imageUrl;

    // Clave Foránea a Pedido (Relación OneToOne)
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
}
