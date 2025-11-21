package com.apiMedicMax.services;

import com.apiMedicMax.repositories.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.models.Producto;

@Service
public class InventarioService {
    @Autowired
    private ProductoRepository productoRepository;

    public boolean verificarStock(Long productoId, int cantidad){
        Producto producto = productoRepository.findById(productoId).orElse(null);
        return producto != null && producto.getStock() >= cantidad;
    }

    public void actualizarStock(Long productoId, int cantidad){
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if(producto != null){
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
        }
    }
    
}
