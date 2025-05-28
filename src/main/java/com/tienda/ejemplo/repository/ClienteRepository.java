package com.tienda.ejemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.ejemplo.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    public Cliente findByRut(String rut);
    public void deleteByRut(String rut);
}
