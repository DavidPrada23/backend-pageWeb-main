package com.apiMedicMax.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiMedicMax.dto.ProductoDTO;
import com.apiMedicMax.exceptions.ProductoNoEncontradoException;
import com.apiMedicMax.models.Producto;
import com.apiMedicMax.repositories.ProductoRepository;
import com.apiMedicMax.services.ProductoService;

import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173") // Permite acceso desde Angular
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ProductoDTO getProductoPorId(@PathVariable Long id) {
        return productoService.toDTO(
                productoRepository.findById(id)
                        .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado")));
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.saveProducto(producto);
        return ResponseEntity.ok(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.deleteProducto(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/categoria/{slug}")
    public List<Producto> getProductosByCategoriaSlug(@PathVariable String slug) {
        return productoService.getByCategoriaSlug(slug);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(@RequestParam String query) {
        List<Producto> productos = productoRepository.searchByNombre(query.toLowerCase());

        List<ProductoDTO> dtos = productos.stream()
                .map(productoService::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/paginado")
    public Page<Producto> obtenerProductosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productoRepository.findAll(pageable);
    }

}
