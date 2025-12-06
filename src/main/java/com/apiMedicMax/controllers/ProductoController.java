package com.apiMedicMax.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import com.apiMedicMax.dto.ProductoDTO;
import com.apiMedicMax.exceptions.ProductoNoEncontradoException;
import com.apiMedicMax.models.Producto;
import com.apiMedicMax.repositories.ProductoRepository;
import com.apiMedicMax.services.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173") // Permite acceso desde Angular
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(
                productoService.getAllProductos().stream()
                        .map(productoService::toDTO)
                        .toList());
    }

    @GetMapping("/{id}")
    public ProductoDTO getProductoPorId(@PathVariable Long id) {
        return productoService.toDTO(
                productoRepository.findById(id)
                        .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado")));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.saveProducto(producto);
        return ResponseEntity.ok(productoService.toDTO(nuevo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{slug}")
    public List<ProductoDTO> getProductosByCategoriaSlug(@PathVariable String slug) {
        return productoService.getByCategoriaSlug(slug)
                .stream()
                .map(productoService::toDTO)
                .toList();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(@RequestParam String query) {
        List<Producto> productos = productoRepository.searchByNombre(query.toLowerCase());
        return ResponseEntity.ok(
                productos.stream()
                        .map(productoService::toDTO)
                        .toList());
    }

    @GetMapping("/paginado")
    public Page<ProductoDTO> obtenerProductosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return productoService.getProductosPaginados(page, size);
    }

    @GetMapping("/{id}/relacionados")
    public List<ProductoDTO> getRelacionados(@PathVariable Long id) {
        return productoService.obtenerRelacionados(id);
    }

}
