package com.pe.farmacia.daoimpl;

import com.pe.farmacia.config.Conexion;
import com.pe.farmacia.dao.FarmaciaDao;
import com.pe.farmacia.modelo.Farmacia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Grupo 6
 */
public class FarmaciaDaoImpl implements FarmaciaDao{
    
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    @Override
    public Farmacia searchData() {
        Farmacia farmacia = new Farmacia();
        String sql = "SELECT * FROM farmacia";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                farmacia.setId(rs.getInt("id"));
                farmacia.setRuc(rs.getString("ruc"));
                farmacia.setNombre(rs.getString("nombre"));
                farmacia.setTelefono(rs.getString("telefono"));
                farmacia.setDireccion(rs.getString("direccion"));
                farmacia.setSlogan(rs.getString("slogan"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return farmacia;
    }

    @Override
    public void update(Farmacia farmacia) {
        String sql = "{CALL sp_actualizar_farmacia(?, ?, ?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, farmacia.getRuc());
            ps.setString(2, farmacia.getNombre());
            ps.setString(3, farmacia.getTelefono());
            ps.setString(4, farmacia.getDireccion());
            ps.setString(5, farmacia.getSlogan());
            ps.setInt(6, farmacia.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
    }

}
