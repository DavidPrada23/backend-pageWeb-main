package com.apiMedicMax.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarritoRequest {
    private Long productoId;
    private int cantidad;
    
}
