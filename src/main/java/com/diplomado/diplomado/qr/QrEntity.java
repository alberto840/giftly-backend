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

    // Clave Foránea a Pedido (Relación OneToOne)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id") // Asume que la PK de QR es también la FK a Pedido, o que Pedido tiene la FK a QR
    private PedidoEntity pedido;
}
