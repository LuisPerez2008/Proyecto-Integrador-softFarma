
package com.pe.farmacia.modelo;

public class Venta {
    private int id;
    private int cliente;
    private int empleado;
    private double total;
    private String fecha;
    
    public Venta(){
        
    }

    public Venta(int id, int cliente, int empleado,double total, String fecha) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
  
}
