package com.pe.farmacia.daoimpl;

import com.pe.farmacia.config.Conexion;
import com.pe.farmacia.dao.LaboratorioDao;
import com.pe.farmacia.modelo.Laboratorio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDaoImpl implements LaboratorioDao {

    private Connection con;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    int r;

    @Override
    public int register(Laboratorio lb) {
        String sql = "{CALL sp_registrar_laboratorio(?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, lb.getRuc());
            cs.setString(2, lb.getNombre());
            cs.setString(3, lb.getTelefono());
            cs.setString(4, lb.getDireccion());
            r = cs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
       return r;
    }

    @Override
    public List<Laboratorio> readAll() {
        List<Laboratorio> listalb = new ArrayList();
        String sql = "SELECT * FROM laboratorios";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Laboratorio lb = new Laboratorio();
                lb.setId(rs.getInt("id"));
                lb.setRuc(rs.getString("ruc"));
                lb.setNombre(rs.getString("nombre"));
                lb.setTelefono(rs.getString("telefono"));
                lb.setDireccion(rs.getString("direccion"));
                listalb.add(lb);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listalb ;
    }

    @Override
    public void delete(int id) {
        String sql = "CALL sp_eliminar_laboratorio(?)";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, id);
            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
        
    }

    @Override
    public void update(Laboratorio lb) {
        String sql = "{CALL sp_actualizar_laboratorio(?, ?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, lb.getRuc());
            cs.setString(2, lb.getNombre());
            cs.setString(3, lb.getTelefono());
            cs.setString(4, lb.getDireccion());
            cs.setInt(5, lb.getId());
            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
        
    }

    @Override
    public Laboratorio searchLabByName(String nombre) {
        Laboratorio lb = new Laboratorio();
        String sql = "{CALL sp_buscar_lab_por_nombre(?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, nombre);
            rs = cs.executeQuery();
            if (rs.next()) {
                lb.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lb;
    }
}
