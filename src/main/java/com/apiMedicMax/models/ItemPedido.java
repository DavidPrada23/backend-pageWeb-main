package com.apiMedicMax.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
