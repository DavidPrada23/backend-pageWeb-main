package com.apiMedicMax.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.apiMedicMax.repositories.CategoriaRepository;
import com.apiMedicMax.repositories.ProductoRepository;
import com.apiMedicMax.dto.ProductoDTO;
import com.apiMedicMax.exceptions.ProductoNoEncontradoException;
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

    public Page<ProductoDTO> getProductosPaginados(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productoRepository.findAll(pageable).map(this::toDTO);
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

    public List<ProductoDTO> obtenerRelacionados(Long id) {

        Producto base = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado"));

        List<Producto> relacionados = productoRepository
                .findTop6ByCategoriaIdAndIdNot(base.getCategoria().getId(), id);

        return relacionados.stream()
                .map(this::toDTO)
                .toList();
    }

    public Page<ProductoDTO> filtrarProductos(
            String categoria,
            String marca,
            Double min,
            Double max,
            int page,
            int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<Producto> resultado = productoRepository.filtrar(
                categoria, marca, min, max, pageable);

        return resultado.map(this::toDTO);
    }

}
