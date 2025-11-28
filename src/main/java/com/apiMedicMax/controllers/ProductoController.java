package com.apiMedicMax.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        if(producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.saveProducto(producto);
        return ResponseEntity.ok(nuevo);
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if(producto == null){
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
    public ResponseEntity<?> buscarProductos(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El parámetro de búsqueda no puede estar vacío.");
        }

        List<Producto> resultados = productoRepository.searchByNombre(query.toLowerCase());

        if(resultados.isEmpty()) {
            return ResponseEntity.ok("No se encontraron productos que coincidan con la búsqueda.");
        }
        return ResponseEntity.ok(resultados);
    }
    
}
