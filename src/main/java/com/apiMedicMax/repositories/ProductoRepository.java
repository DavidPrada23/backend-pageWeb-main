package com.apiMedicMax.repositories;

import com.apiMedicMax.models.Categoria;
import com.apiMedicMax.models.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombre(String nombre);

    List<Producto> findCategoriaById(Long categoriaId);

    List<Producto> findByCategoriaSlug(String slug);

    List<Producto> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Producto> searchByNombre(@Param("query") String query);

    List<Producto> findTop6ByCategoriaIdAndIdNot(Long categoriaId, Long id);

    @Query("""
                SELECT p FROM Producto p
                WHERE (:categoria IS NULL OR p.categoria.slug = :categoria)
                AND (:marca IS NULL OR p.marca = :marca)
                AND (:min IS NULL OR p.precio >= :min)
                AND (:max IS NULL OR p.precio <= :max)
            """)
    Page<Producto> filtrar(
            @Param("categoria") String categoria,
            @Param("marca") String marca,
            @Param("min") Double min,
            @Param("max") Double max,
            Pageable pageable);

}
