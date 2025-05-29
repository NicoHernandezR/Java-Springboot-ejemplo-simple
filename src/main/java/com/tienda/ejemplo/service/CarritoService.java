package com.tienda.ejemplo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.ejemplo.model.Carrito;
import com.tienda.ejemplo.model.Cliente;
import com.tienda.ejemplo.model.Producto;
import com.tienda.ejemplo.payload.ProductoCantidad;
import com.tienda.ejemplo.repository.CarritoRepository;
import com.tienda.ejemplo.repository.ClienteRepository;
import com.tienda.ejemplo.repository.ProductoRepository;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Carrito> getCarritosByClienteRut(String rut){
        Cliente cli = clienteRepository.findByRut(rut);

        if (cli == null) {
            return null;
        }
        List<Carrito> carritos = carritoRepository.findByCliente(cli);
        return carritos;
        
    }

    public List<Carrito> getCarritosByClienteAndFecha(String rut, LocalDate  fecha){
        Cliente cli = clienteRepository.findByRut(rut);

        if (cli == null) {
            return null;
        }

        List<Carrito> carritos = carritoRepository.findByClienteAndFecha(cli, fecha);
        
        return carritos;
    }

    public Carrito insertCarrito(ProductoCantidad producto, LocalDate  fecha, String rut){
        Cliente cli = clienteRepository.findByRut(rut);

        if (cli == null) {
            return null;
        }
        Producto prod = productoRepository.findByCodigo(producto.getCodigoProducto());
        if (prod == null) {
            return null;
        }

        Carrito car = new Carrito();
        car.setCantidad(producto.getCantidad());
        car.setCliente(cli);
        car.setFecha(fecha);
        car.setProducto(prod);

        return carritoRepository.save(car);

    }

    public Carrito updateCarrito(Carrito car){

        Optional<Carrito> idCar = carritoRepository.findById(car.getId());

        if (!idCar.isPresent()) {
            return null;
        }

        Cliente cli = clienteRepository.findByRut(car.getCliente().getRut());
        if (cli == null) {
            return null;
        }

        Producto prod = productoRepository.findByCodigo(car.getProducto().getCodigo());
        if (prod == null) {
            return null;
        }

        Carrito newCar = idCar.get();
        
        newCar.setCantidad(car.getCantidad());
        newCar.setCliente(cli);
        newCar.setFecha(car.getFecha());
        newCar.setProducto(prod);

        return carritoRepository.save(newCar);

    }

    public Boolean deleteCarritoById(Long id){
        Optional<Carrito> car = carritoRepository.findById(id);

        if (!car.isPresent()) {
            return false;
        }

        carritoRepository.deleteById(id);
        return true;


    }



}
