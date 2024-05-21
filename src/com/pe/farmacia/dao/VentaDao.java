package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.DetalleVenta;
import com.pe.farmacia.modelo.Venta;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grupo 6
 */
public interface VentaDao {

    int register(Venta v);

    int registerDetailOfSale(DetalleVenta Dv);

    void updateStock(int cant, int id);

    //List<Venta> readAll();
    
    List<Map<String, Object>> readAll2();

    Venta searchSaleById(int id);

    int getIdNewSale();

    void convertSaleToPDF(int idventa, int cliente, double total,String fechaVenta, String usuario);
    
    Map<String, Integer> reportForCantSale();
    
    List<Map<String, Object>> readAll3(String nombre);
    
    Map<Integer, Integer> reportCantVentasXaño(int anio);

}
