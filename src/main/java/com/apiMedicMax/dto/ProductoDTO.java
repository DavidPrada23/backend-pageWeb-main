package com.apiMedicMax.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String marca;
    private int stock;
    private double precio;
    private String descripcion;
    private String imagen;

    private String categoriaNombre;
    private String categoriaSlug;
}
