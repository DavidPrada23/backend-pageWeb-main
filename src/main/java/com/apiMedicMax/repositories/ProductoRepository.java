package com.apiMedicMax.repositories;

import com.apiMedicMax.models.Categoria;
import com.apiMedicMax.models.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombre(String nombre);
    List<Producto> findCategoriaById(Long categoriaId);
    List<Producto> findByCategoriaSlug(String slug);
    List<Producto> findByCategoria(Categoria categoria);
    
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Producto> searchByNombre(@Param("query") String query);

    List<Producto> findTop6ByCategoriaIdAndIdNot(Long categoriaId, Long id);
}
