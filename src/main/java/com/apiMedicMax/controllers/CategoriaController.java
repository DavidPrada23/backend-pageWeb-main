package com.apiMedicMax.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiMedicMax.services.CategoriaService;
import com.apiMedicMax.models.Categoria;


@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping //Crea una nueva categoría.
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.createCategoria(categoria));
    }

    @GetMapping //Lista todas las categorías.
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}") //Obtiene una categoría específica.
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Categoria categoria = categoriaService.getCategoriaById(id);
        if(categoria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }
    
    @PutMapping("/{id}") //Actualiza una categoría.
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaData) {
        Categoria updatedCategoria = categoriaService.updateCategoria(id, categoriaData);
        if(updatedCategoria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCategoria);
    }

    @DeleteMapping //Elimina una categoría.
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        boolean deleted = categoriaService.deleteCategoria(id);
        if (!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
        
    
    
}
