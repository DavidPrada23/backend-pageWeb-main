package com.apiMedicMax.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.apiMedicMax.services.PedidoService;
import com.apiMedicMax.models.Pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping //Crea un nuevo pedido.
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.createPedido(pedido));
    }

    @GetMapping("/{id}") //Obtiene un pedido por su ID.
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }
    
    @GetMapping //Lista todos los pedidos.
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}") //Actualiza el estado del pedido.
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido updated = pedidoService.updatePedido(id, pedido);
        if (updated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
        
    }

    @DeleteMapping //Cancela el pedido.
    public ResponseEntity<Void> deletePedido(@PathVariable Long id){
        boolean deleted = pedidoService.deletePedido(id);
        if (!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    
}
