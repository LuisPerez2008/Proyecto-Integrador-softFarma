package com.pe.farmacia.daoimpl;
import com.pe.farmacia.config.Conexion;
import com.pe.farmacia.dao.ClienteDao;
import com.pe.farmacia.modelo.Cliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDaoImpl implements ClienteDao {

    private Connection con;
    //private Conexion con;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    int r;

    @Override
    public int register(Cliente cl) {
        
        String sql = "{CALL sp_registrar_cliente(?,?,?,?)}";
        
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, cl.getDni());
            cs.setString(2, cl.getNombre());
            cs.setString(3, cl.getTelefono());
            cs.setString(4, cl.getDireccion());
            r = cs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
        return r;
    }

    @Override
    public List<Cliente> readAll() {
        List<Cliente> listaClientes = new ArrayList();
        String sql = "SELECT * FROM clientes ORDER BY nombre";
        try {
            
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("id"));
                cl.setDni(rs.getString("dni"));
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                listaClientes.add(cl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaClientes;
    }

    @Override
    public void delete(int id) {
        String sql = "{CALL sp_eliminar_cliente(?)}";
        try {
            cs = con.prepareCall(sql);
            cs.setInt(1, id);
            cs.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());

        }
    }

    @Override
    public void update(Cliente cl) {
        String sql = "{CALL sp_actualizar_cliente(?, ?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, cl.getDni());
            cs.setString(2, cl.getNombre());
            cs.setString(3, cl.getTelefono());
            cs.setString(4, cl.getDireccion());
            cs.setInt(5, cl.getId());
            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
    }

    @Override
    public Cliente searchClientByDNI(int dni) {
        Cliente cl = new Cliente();
        String sql = "{CALL sp_buscar_cliente_por_dni(?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, dni);
            rs = cs.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }

}
