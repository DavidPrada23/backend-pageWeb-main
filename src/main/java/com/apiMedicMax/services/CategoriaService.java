package com.apiMedicMax.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.repositories.CategoriaRepository;
import com.apiMedicMax.models.Categoria;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria createCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(Long id){
        return categoriaRepository.findById(id).orElse(null);
    }

    // Actualizar la categoría
    public Categoria updateCategoria(long id, Categoria categoriaData){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if(optionalCategoria.isPresent()){
            Categoria categoriaExistente = optionalCategoria.get();
            categoriaExistente.setNombre(categoriaData.getNombre()); // Ajusta los campos según su modelo
            // Puedes actualizar más campos aquí
            return categoriaRepository.save(categoriaExistente);
        } else {
            return null;
        }
    }

    // Eliminar una Categoría
    public boolean deleteCategoria(long id){
        if(categoriaRepository.existsById(id)){
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}