package com.tienda.ejemplo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.ejemplo.model.Cliente;
import com.tienda.ejemplo.service.ClienteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    private boolean validarRut(String rut){

        int lenRut = rut.length();
        if (lenRut < 9 || lenRut > 10){
            return false;
        }

        return true;
    }

    private boolean validarCliente(Cliente cli){
    
        boolean okRut = validarRut(cli.getRut());

        if (okRut == false){
            return false;
        }

        if (cli.getPnombre() == null) {
            return false;
        }
        if (cli.getAppaterno() == null) {
            return false;
        }

        if (!(cli.getPnombre() instanceof String)){
            return false;
        }

        if (cli.getPnombre().length() < 2 || cli.getPnombre().length() > 31){
                return false;
        }

        if (!(cli.getAppaterno() instanceof String)){
            return false;
        }

        if (cli.getAppaterno().length() < 2 || cli.getAppaterno().length() > 31){
                return false;
        }

        if (cli.getSnombre() != null) {
            if (!(cli.getSnombre() instanceof String)){
                return false;
            }
            if (cli.getSnombre().length() < 2 || cli.getSnombre().length() > 31){
                return false;
            }
        }
        if (cli.getApmaterno() != null) {
            if (!(cli.getApmaterno() instanceof String)){
                return false;
            }
            if (cli.getApmaterno().length() < 2 || cli.getApmaterno().length() > 31){
                return false;
            }
        }

        return true;

    }

    @GetMapping()
    public ResponseEntity<List<Cliente>> getAllClients() {
        try {
            List<Cliente> clientes = clienteService.getAllClients();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("/{rut}")
    public ResponseEntity<Cliente> getClienteByRut(@PathVariable("rut") String rut) {
        
        try {
            boolean ok = validarRut(rut);
            if (ok == false){
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            Cliente cli = clienteService.getClienteByRut(rut);
            if (cli == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(cli);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        
    }

    @PostMapping()
    public ResponseEntity<Cliente> insertCliente(@RequestBody Cliente cli) {
        try {
            boolean ok = validarCliente(cli);
            if (ok == false){
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Cliente newCli = clienteService.insertCliente(cli);
            if (newCli == null){
                return ResponseEntity.status(409).build();
            }
            return ResponseEntity.ok(newCli);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cli) {
        try {
            boolean ok = validarCliente(cli);
            if (ok == false){
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Cliente newCli = clienteService.updateCliente(cli);
            if (newCli == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(newCli);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> deleteByRut(@PathVariable("rut") String rut) {
        
        try {
            boolean ok = validarRut(rut);
            if (ok == false){
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            boolean eliminado = clienteService.deleteByRut(rut);
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
