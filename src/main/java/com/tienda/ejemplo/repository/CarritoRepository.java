package com.tienda.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.ejemplo.model.Carrito;
import com.tienda.ejemplo.model.Cliente;

import java.util.List;
import java.util.Date;



@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{

    List<Carrito> findByCliente(Cliente cliente);
    List<Carrito> findByClienteAndFecha(Cliente cliente, Date fecha);
}
