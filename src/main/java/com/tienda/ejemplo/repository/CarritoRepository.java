package com.tienda.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.ejemplo.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{

}
