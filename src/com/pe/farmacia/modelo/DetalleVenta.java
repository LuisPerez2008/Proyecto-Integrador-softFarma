package com.pe.farmacia.modelo;

public class DetalleVenta {

    private int id;
    private int id_med;
    private int cantidad;
    private double precio;
    private int id_venta;

    public DetalleVenta() {

    }

    public DetalleVenta(int id, int id_med, int cantidad, double precio, int id_venta) {
        this.id = id;
        this.id_med = id_med;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_venta = id_venta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_med() {
        return id_med;
    }

    public void setId_med(int id_med) {
        this.id_med = id_med;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
}
