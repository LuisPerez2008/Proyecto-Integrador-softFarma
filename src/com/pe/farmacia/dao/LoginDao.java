package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Grupo 6
 */
public interface LoginDao {

    Usuario log(String correo, String pass);

    int register(Usuario user);
    
    void update (Usuario user);
    
    void delete (int id);

    List<Usuario> readAll();
    
    Usuario searchUserById(int id);

}
