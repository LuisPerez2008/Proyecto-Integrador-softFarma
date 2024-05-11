package com.pe.farmacia.daoimpl;

import com.pe.farmacia.config.Conexion;
import com.pe.farmacia.dao.LoginDao;
import com.pe.farmacia.modelo.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements LoginDao {

    private Connection con;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    private int r;

    @Override
    public Usuario log(String correo, String pass) {
        Usuario user = null;
        String sql = "{CALL sp_buscar_usuario_por_correo_y_contrasena(?,?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, correo);
            cs.setString(2, pass);
            rs = cs.executeQuery();
            if (rs.next()) {
                user = new Usuario.Builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .correo(rs.getString("correo"))
                        .pass(rs.getString("pass"))
                        .rol(rs.getString("rol"))
                        .build();

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return user;
    }

    @Override
    public int register(Usuario user) {
        String sql = "{CALL sp_registrar_usuario(?,?,?,?)}";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCorreo());
            ps.setString(3, user.getPass());
            ps.setString(4, user.getRol());
            r = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    @Override
    public void update(Usuario user) {
        String sql = "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, user.getNombre());
            cs.setString(2, user.getCorreo());
            cs.setString(3, user.getPass());
            cs.setString(4, user.getRol());
            cs.setInt(5, user.getId());
            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public void delete(int id) {
        String sql = "CALL sp_eliminar_usuario(?)";
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
    public List<Usuario> readAll() {
        List<Usuario> lista = new ArrayList();
        String sql = "SELECT * FROM usuarios";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario user = new Usuario.Builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .correo(rs.getString("correo"))
                        .pass(rs.getString("pass"))
                        .rol(rs.getString("rol"))
                        .build();
                lista.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    @Override
    public Usuario searchUserById(int id) {
        Usuario user = null;
        String sql = "CALL sp_buscar_usuario_por_id(?)";

        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, id);
            rs = cs.executeQuery();
            if (rs.next()) {
                user = new Usuario.Builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .correo(rs.getString("correo"))
                        .pass(rs.getString("pass"))
                        .rol(rs.getString("rol"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return user;
    }

}
