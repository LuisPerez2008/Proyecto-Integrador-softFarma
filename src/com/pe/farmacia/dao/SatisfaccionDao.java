
package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.Satisfaccion;
import java.util.Map;


public interface SatisfaccionDao {
    
    int regitrar(Satisfaccion sat);
    
    Map<String, Integer> cantidadDePuntuacion();
    
}
