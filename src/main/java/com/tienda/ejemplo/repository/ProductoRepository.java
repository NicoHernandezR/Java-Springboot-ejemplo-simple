package com.tienda.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.ejemplo.model.Producto;
import java.util.List;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    public List<Producto> findByNombre(String nombre);
    public Producto findByCodigo(String codigo);
    void deleteByCodigo(String codigo);
}
