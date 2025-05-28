package com.tienda.ejemplo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.ejemplo.model.Cliente;
import com.tienda.ejemplo.repository.ClienteRepository;

@Service
public class ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClients() {
        return clienteRepository.findAll();
    };

    public Cliente getClienteByRut(String rut) {
        return clienteRepository.findByRut(rut);
    }

    public Cliente insertCliente(Cliente cli) {
        Cliente rutCli = clienteRepository.findByRut(cli.getRut());

        if (rutCli != null) {
            return null;
        }

        return clienteRepository.save(cli);

    }

    public Cliente updateCliente(Cliente cli) {
        Cliente rutCli = clienteRepository.findByRut(cli.getRut());

        if (rutCli == null) {
            return null;
        }

        return clienteRepository.save(cli);

    }

    public boolean deleteByRut(String rut) {
        Cliente rutCli = clienteRepository.findByRut(rut);

        if (rutCli == null) {
            return false;
        }

        clienteRepository.deleteByRut(rut);
        return true;
    }

}
