package com.apiMedicMax.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "facturas")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    private Double total;

    // Relación con el pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Relación con el producto
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    private int cantidad;

    // Relación con usuario de la compra
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
}
