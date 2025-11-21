package com.apiMedicMax.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiMedicMax.models.Inventario;

@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Long>{
    
}
