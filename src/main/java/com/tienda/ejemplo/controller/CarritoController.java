package com.tienda.ejemplo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.ejemplo.model.Carrito;
import com.tienda.ejemplo.payload.CarritoInsertPayload;
import com.tienda.ejemplo.payload.ProductoCantidad;
import com.tienda.ejemplo.service.CarritoService;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    private boolean validarRut(String rut){

        int lenRut = rut.length();
        if (lenRut < 9 || lenRut > 10){
            return false;
        }

        return true;
    }

    @GetMapping("/clienterut/{rut}")
    public ResponseEntity<List<Carrito>> getCarritosByRut(@PathVariable("rut") String rut) {

        try {
            boolean ok = validarRut(rut);
            if (ok == false){
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        try {
            List<Carrito> carritos = carritoService.getCarritosByClienteRut(rut);

            if (carritos == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(carritos);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/rutfecha/{rut}/{fecha}")
    public ResponseEntity<List<Carrito>> getCarritosByClienteAndFecha(
        @PathVariable("rut") String rut, 
        @PathVariable("fecha") LocalDate fecha) {

        try {
            boolean ok = validarRut(rut);
            if (ok == false){
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        try {
            List<Carrito> carritos = carritoService.getCarritosByClienteAndFecha(rut, fecha);

            if (carritos == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(carritos);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<List<Carrito>> insertCarritos(@RequestBody CarritoInsertPayload carro) {
        
        try {
            boolean ok = validarRut(carro.getRut());
            if (ok == false){
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        List<Carrito> carritos = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        for (ProductoCantidad producto : carro.getProductos()) {
            // Si da nulo talvez igual seguir? o matar la funcion aun por decidir.
            // Aca agregar la validacion de ProductoCantidad.
            Carrito carrito = carritoService.insertCarrito(producto, fechaActual, carro.getRut());
            carritos.add(carrito);
        }

        return ResponseEntity.status(201).body(carritos);

    }

    @PutMapping()
    public ResponseEntity<Carrito> updateCarrito(@RequestBody Carrito car) {
        try {
            Carrito newCar = carritoService.updateCarrito(car);
            if (newCar == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(newCar);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByRut(@PathVariable("id") Long id) {
        try {
            boolean eliminado = carritoService.deleteCarritoById(id);
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
