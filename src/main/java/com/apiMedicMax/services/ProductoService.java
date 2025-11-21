package com.apiMedicMax.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.repositories.CategoriaRepository;
import com.apiMedicMax.repositories.ProductoRepository;
import com.apiMedicMax.models.Categoria;
import com.apiMedicMax.models.Producto;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto saveProducto(Producto producto) {
        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id){
        productoRepository.deleteById(id);
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> getByCategoriaSlug(String slug) {
        return productoRepository.findByCategoriaSlug(slug);
    }   
    
}
