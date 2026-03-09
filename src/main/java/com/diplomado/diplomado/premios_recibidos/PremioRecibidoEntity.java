package com.diplomado.diplomado.premios_recibidos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import com.diplomado.diplomado.tienda_premios.TiendaPremioEntity;
import com.diplomado.diplomado.user.UsuarioEntity;

@Entity
@Table(name = "premios_recibidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremioRecibidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "recibido")
    private Boolean recibido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_recibido")
    private Date fechaRecibido;

    @Column(name = "tipo", length = 100)
    private String tipo;

    // Clave Foránea a TiendaPremio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda_premio", referencedColumnName = "id")
    private TiendaPremioEntity tiendaPremio;

    // Clave Foránea a Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UsuarioEntity usuario;
}
