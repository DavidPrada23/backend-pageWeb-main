package com.apiMedicMax.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiMedicMax.services.CarritoService;
import com.apiMedicMax.dto.CarritoRequest;
import com.apiMedicMax.dto.ItemCarrito;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar") //Agregar un producto al carrito
    public ResponseEntity<?> agregarProducto(@RequestBody CarritoRequest request) {
        carritoService.agregarProductoPorId(request.getProductoId(), request.getCantidad());
        return ResponseEntity.ok("Se agrego producto");
        
    }
    
    @GetMapping //Obtiene el contenido del carrito
    public ResponseEntity<List<ItemCarrito>> verCarrito() {
        return ResponseEntity.ok(carritoService.obtenerCarrito());
    }

    @DeleteMapping("/{productoId}") // Elimina un producto del carrito
    public ResponseEntity<String> eliminarProducto(@PathVariable Long productoId){
        boolean eliminado = carritoService.eliminarProducto(productoId);
        if(eliminado){
            return ResponseEntity.ok("El producto ha sido eliminado");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/checkout") //Realiza el checkout
    public ResponseEntity<String> checkout() {
        try {
            carritoService.realizarCheckout();
            return ResponseEntity.ok("Compra realizada exitosamente");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
    }
    
    
}
