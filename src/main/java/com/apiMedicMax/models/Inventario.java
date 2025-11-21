package com.apiMedicMax.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventarios")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;

    private int cantidad;

    private String ubicacion;
}
