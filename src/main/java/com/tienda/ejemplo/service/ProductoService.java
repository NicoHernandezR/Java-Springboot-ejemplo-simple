package com.tienda.ejemplo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.ejemplo.model.Producto;
import com.tienda.ejemplo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    };

    public List<Producto> getProductosByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public Producto getProductoByCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }

    public Producto insertProducto(Producto prod) {
        Producto codProd = productoRepository.findByCodigo(prod.getCodigo());

        if (codProd != null) {
            return null;
        }

        return productoRepository.save(prod);

    }

    public Producto updateProducto(Producto prod) {
        Producto codProd = productoRepository.findByCodigo(prod.getCodigo());

        if (codProd == null) {
            return null;
        }

        return productoRepository.save(prod);

    }

    public boolean deleteByCodigo(String codigo) {
        Producto codProd = productoRepository.findByCodigo(codigo);

        if (codProd == null) {
            return false;
        }

        productoRepository.deleteByCodigo(codigo);
        return true;
    }

}
