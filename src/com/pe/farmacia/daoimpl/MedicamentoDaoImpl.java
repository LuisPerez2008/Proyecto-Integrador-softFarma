package com.pe.farmacia.daoimpl;

import com.pe.farmacia.config.Conexion;
import com.pe.farmacia.dao.MedicamentoDao;
import com.pe.farmacia.modelo.Medicamento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MedicamentoDaoImpl implements MedicamentoDao {

    private Connection con;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    int r;

    @Override
    public int register(Medicamento med) {
        String sql = "{CALL sp_registrar_medicamento(?,?,?,?,?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, med.getCodigo());
            cs.setString(2, med.getNombre());
            cs.setInt(3, med.getLaboratorio());
            cs.setInt(4, med.getStock());
            cs.setDouble(5, med.getPrecio());
            r = cs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        return r;
    }

    /*@Override
    public List<Medicamento> readAll() {
        List<Medicamento> listaMed = new ArrayList();
        String sql = "SELECT lb.id AS id_laboratorio, lb.nombre AS nombre_laboratorio, m.* FROM laboratorios lb"
                + " INNER JOIN medicamentos m ON lb.id = m.laboratorio"
                + " ORDER BY m.nombre";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Medicamento med = new Medicamento();
                med.setId(rs.getInt("id"));
                med.setCodigo(rs.getString("codigo"));
                med.setNombre(rs.getString("nombre"));
                med.setLaboratorio(rs.getInt("id_laboratorio"));
                med.setNombreLaboratorio(rs.getString("nombre_laboratorio"));
                med.setStock(rs.getInt("stock"));
                med.setPrecio(rs.getDouble("precio"));
                listaMed.add(med);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaMed;
    }
     */
    @Override
    public List<Map<String, Object>> readAll2() {
        String sql = "SELECT m.id,m.codigo,m.nombre,l.nombre as laboratorio, m.stock,m.precio"
                + " FROM medicamentos m"
                + " INNER JOIN laboratorios l ON m.laboratorio = l.id"
                + " ORDER BY m.nombre";
        List<Map<String, Object>> lista = new ArrayList<>();
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                
                Map<String, Object> map = new HashMap<>();
                
                map.put("id", rs.getInt(1));
                map.put("codigo", rs.getString(2));
                map.put("nombre", rs.getString(3));
                map.put("laboratorio", rs.getString(4));
                map.put("stock", rs.getInt(5));
                map.put("precio", rs.getDouble(6));
                
                lista.add(map);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

        return lista;
    }

    @Override
    public void delete(int id) {
        String sql = "{CALL sp_eliminar_medicamento(?)}";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(Medicamento med) {
        String sql = "{CALL sp_actualizar_medicamento(?, ?, ?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, med.getCodigo());
            cs.setString(2, med.getNombre());
            cs.setInt(3, med.getLaboratorio());
            cs.setInt(4, med.getStock());
            cs.setDouble(5, med.getPrecio());
            cs.setInt(6, med.getId());
            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    
    @Override
    public Medicamento searchMedicineByCode(String cod) {
        Medicamento med = new Medicamento();
        String sql = "{CALL sp_buscar_medicamento_por_codigo(?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setString(1, cod);
            rs = cs.executeQuery();
            if (rs.next()) {
                med.setId(rs.getInt("id"));
                med.setNombre(rs.getString("nombre"));
                med.setPrecio(rs.getDouble("precio"));
                med.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return med;
    }

    @Override
    public Map<String, Object> searchMedicineById(int id) {
        String sql = "{CALL sp_obtener_medicamento_por_id(?)}";

        Map<String, Object> map = new HashMap<>();

        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, id);
            rs = cs.executeQuery();
            if (rs.next()) {

                map.put("id", rs.getInt(1));
                map.put("codigo", rs.getString(2));
                map.put("nombre", rs.getString(3));
                map.put("id_laboratorio", rs.getInt(4));
                map.put("laboratorio", rs.getString(5));
                map.put("stock", rs.getInt(6));
                map.put("precio", rs.getDouble(7));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return map;
    }

}
