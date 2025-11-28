package com.apiMedicMax.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.repositories.CategoriaRepository;
import com.apiMedicMax.repositories.ProductoRepository;
import com.apiMedicMax.dto.ProductoDTO;
import com.apiMedicMax.models.Categoria;
import com.apiMedicMax.models.Producto;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto saveProducto(Producto producto) {
        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> getByCategoriaSlug(String slug) {
        return productoRepository.findByCategoriaSlug(slug);
    }

    public ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setMarca(p.getMarca());
        dto.setStock(p.getStock());
        dto.setPrecio(p.getPrecio());
        dto.setDescripcion(p.getDescripcion());
        dto.setImagen(p.getImagen());

        // Datos planos de la categoría (limpio para frontend)
        dto.setCategoriaNombre(p.getCategoria().getNombre());
        dto.setCategoriaSlug(p.getCategoria().getSlug());

        return dto;
    }

}
