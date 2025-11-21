package com.apiMedicMax.models;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Producto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100, name = "name")
    private String nombre;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false, name = "price")   
    private double precio;

    private String descripcion;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

}