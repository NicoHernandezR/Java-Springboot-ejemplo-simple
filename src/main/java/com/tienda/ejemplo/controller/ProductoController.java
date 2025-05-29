package com.tienda.ejemplo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.ejemplo.model.Producto;
import com.tienda.ejemplo.service.ProductoService;


@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    private boolean validarProducto(Producto prod){
        if (prod.getNombre() == null) {
            return false;
        }
        if (prod.getCategoria() == null) {
            return false;
        }

        if (prod.getPrecio() == null) {
            return false;
        }

        if (prod.getNombre().length() < 2 || prod.getNombre().length() > 31){
            return false;
        }
        if (prod.getCategoria().length() < 2 || prod.getCategoria().length() > 31){
            return false;
        }

        if (prod.getPrecio() < 100 || prod.getPrecio() > 1000000){
            return false;
        }

        return true;
    }

    private String generarCodigo(Producto prod){
        // Codigo es las 3 primeras letras de la categoria en mayuscula
        // Un guion, la cantidad de letras del nombre multiplicada por 2
        // Los 2 ultimos digitos del precio
        String categoria = prod.getCategoria();
        String parteCategoria;
        if (categoria.length() >= 3) {
            parteCategoria = categoria.substring(0, 3).toUpperCase();
        } else {
            parteCategoria = categoria.toUpperCase();
        }
        int longitudNombre = prod.getNombre().replace(" ", "").length();
        String parteNombre = String.valueOf(longitudNombre * 2);
        
        // 3. Dos últimos dígitos del precio
        String partePrecio = String.format("%02d", prod.getPrecio() % 100);
        
        // Combinar todas las partes
        return parteCategoria + "-" + parteNombre + partePrecio;
    }

    @GetMapping()
    public ResponseEntity<List<Producto>> getAllClients() {
        try {
            List<Producto> productos = productoService.getAllProductos();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> getMethodName(@PathVariable("nombre") String nombre) {
        try {
            List<Producto> productos = productoService.getProductosByNombre(nombre);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Producto> getClienteByRut(@PathVariable("codigo") String codigo) {

        try {
            Producto prod = productoService.getProductoByCodigo(codigo);
            if (prod == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(prod);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        
    }

    @PostMapping()
    public ResponseEntity<Producto> insertProducto(@RequestBody Producto prod) {
        try {
            boolean ok = validarProducto(prod);
            if (ok == false){
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }

        try {
            prod.setCodigo(generarCodigo(prod));
            Producto newProd = productoService.insertProducto(prod);
            if (newProd == null){
                return ResponseEntity.status(409).build();
            }
            return ResponseEntity.ok(newProd);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @PutMapping()
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto prod) {
        try {
            boolean ok = validarProducto(prod);
            if (ok == false){
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Producto newProd = productoService.updateProducto(prod);
            if (newProd == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(newProd);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deleteByRut(@PathVariable("codigo") String codigo) {
        try {
            boolean eliminado = productoService.deleteByCodigo(codigo);
            if (eliminado){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().body(null);
        }
        
    }
}
