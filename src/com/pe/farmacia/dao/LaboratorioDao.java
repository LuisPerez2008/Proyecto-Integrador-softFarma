package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.Laboratorio;
import java.util.List;

public interface LaboratorioDao {

    int register(Laboratorio lb);

    List<Laboratorio> readAll();

    void delete(int id);

    void update(Laboratorio lb);

    Laboratorio searchLabByName(String nombre);

}
