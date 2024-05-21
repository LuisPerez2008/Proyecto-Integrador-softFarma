package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.Cliente;
import java.util.List;


public interface ClienteDao {

    int register(Cliente cl);

    List<Cliente> readAll();

    void delete(int id);

    void update(Cliente cl);

    Cliente searchClientByDNI(int dni);
    
}
