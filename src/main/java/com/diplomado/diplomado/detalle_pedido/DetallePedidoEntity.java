package com.diplomado.diplomado.detalle_pedido;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

import com.diplomado.diplomado.pedido.PedidoEntity;
import com.diplomado.diplomado.ubicacion.UbicacionEntity;

@Entity
@Table(name = "detalle_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mensaje", length = 255)
    private String mensaje;

    @Column(name = "instrucciones", length = 255)
    private String instrucciones;

    @Column(name = "receptor_encarga", length = 255)
    private String receptorEncarga;

    @Column(name = "celular_1", length = 20)
    private String celular1;

    @Column(name = "celular_2", length = 20)
    private String celular2;

    @Column(name = "nombre_objetivo", length = 20)
    private String nombreObjetivo;

    @Column(name = "nombre_emisor", length = 255)
    private String nombreEmisor;

    // Clave Foránea a Pedido (Relación OneToOne)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id") // Asume que la PK de Detalle_pedido es también la FK a Pedido
    private PedidoEntity pedido;

    // Clave Foránea a Ubicacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    private UbicacionEntity ubicacion;
}
