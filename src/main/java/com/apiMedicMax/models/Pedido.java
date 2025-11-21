package com.apiMedicMax.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;
    
    @Column(nullable = false)
    private String estado;

    @ManyToMany
    @JoinTable(
        name = "pedidos_productos",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )

    private List<Producto> productos;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items = new ArrayList<>();

    public void setItems(List<ItemPedido> items){
        this.items = items;
    }

    public Double calcularTotal() {
        return items.stream().mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad()).sum();
    }

}
             