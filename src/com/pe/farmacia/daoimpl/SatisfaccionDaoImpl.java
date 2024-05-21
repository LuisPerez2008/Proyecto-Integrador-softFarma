
package com.pe.farmacia.daoimpl;
import com.pe.farmacia.config.Conexion;
import com.pe.farmacia.dao.SatisfaccionDao;
import com.pe.farmacia.modelo.Satisfaccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 51901
 */
public class SatisfaccionDaoImpl implements  SatisfaccionDao{
    private Connection con;
    //private Conexion con;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    int r;
    
    @Override
    public int regitrar(Satisfaccion sat) {
        
    String sql = "INSERT INTO satisfaccion (id_cliente, puntuacion) VALUES (?, ?)";

    try {
        con = Conexion.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, sat.getIdCliente());
        ps.setString(2, sat.getPuntuacion());
        r = ps.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.toString());
    } 

    return r;
    }

    @Override
    public Map<String, Integer> cantidadDePuntuacion() {
        Map<String, Integer> listaMap = new HashMap<>();
        String sql = "SELECT puntuacion, COUNT(*) AS cantidad "+
                "FROM satisfaccion "+
                "GROUP BY puntuacion "+
                "ORDER BY cantidad DESC ";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                String puntuacion = rs.getString("puntuacion");
                int cantidad = rs.getInt("cantidad");
                listaMap.put(puntuacion, cantidad);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }  
        return listaMap;
    }
    
}
