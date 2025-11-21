package com.apiMedicMax.repositories;

import com.apiMedicMax.models.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombre(String nombre);
    List<Producto> findCategoriaById(Long categoriaId);
    List<Producto> findByCategoriaSlug(String slug);
}
