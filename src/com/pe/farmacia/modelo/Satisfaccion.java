
package com.pe.farmacia.modelo;

public class Satisfaccion {
    private int id;
    private int idCliente;
    private String puntuacion;

    public Satisfaccion() {
    }
    
    
    
    public Satisfaccion(int id, int idCliente, String puntuacion) {
        this.id = id;
        this.idCliente = idCliente;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    
   
    
    
            
}
