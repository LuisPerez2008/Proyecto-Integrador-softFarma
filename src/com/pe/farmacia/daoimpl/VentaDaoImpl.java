package com.pe.farmacia.daoimpl;

import com.pe.farmacia.config.Conexion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pe.farmacia.dao.VentaDao;
import com.pe.farmacia.modelo.DetalleVenta;
import com.pe.farmacia.modelo.Venta;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.filechooser.FileSystemView;

public class VentaDaoImpl implements VentaDao {

    private Connection con;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    private int r;

    @Override
    public int getIdNewSale() {
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }

    @Override
    public int register(Venta v) {
        String sql = "{CALL sp_registrar_venta(?,?,?,?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, v.getCliente());
            cs.setInt(2, v.getEmpleado());
            cs.setDouble(3, v.getTotal());
            cs.setString(4, v.getFecha());
            r = cs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    @Override
    public int registerDetailOfSale(DetalleVenta Dv) {
        String sql = "{CALL sp_registrar_detalle(?, ?, ?, ?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, Dv.getId_med());
            cs.setInt(2, Dv.getCantidad());
            cs.setDouble(3, Dv.getPrecio());
            cs.setInt(4, Dv.getId_venta());
            r = cs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    @Override
    public List<Map<String, Object>> readAll2() {
        String sql = "SELECT v.id,c.nombre as cliente,e.nombre as vendedor,v.fecha,v.total"
                + " FROM ventas v"
                + " INNER JOIN clientes c on c.id = v.cliente "
                + " INNER JOIN usuarios e on e.id = v.empleado order by id desc";
        List<Map<String, Object>> lista = new ArrayList<>();
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt(1));
                map.put("cliente", rs.getString(2));
                map.put("vendedor", rs.getString(3));
                map.put("fecha", rs.getString(4));
                map.put("total", rs.getDouble(5));
                lista.add(map);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

        return lista;
    }

    @Override
    public Venta searchSaleById(int id) {
        Venta cl = new Venta();
        String sql = "{CALL sp_buscar_venta_por_id(?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, id);
            rs = cs.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setCliente(rs.getInt("cliente"));
                cl.setTotal(rs.getDouble("total"));
                cl.setEmpleado(rs.getInt("empleado"));
                cl.setFecha(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }

    @Override
    public void updateStock(int cant, int id) {
        String sql = "{CALL sp_actualizar_stock_medicamento(?,?)}";
        try {
            con = Conexion.getConnection();
            cs = con.prepareCall(sql);
            cs.setInt(1, cant);
            cs.setInt(2, id);
            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void convertSaleToPDF(int idventa, int cliente, double total, String fechaVenta, String usuario) {
        try {

            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + "venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance(getClass().getResource("/logo/logo-farmacia.png"));
            //Fecha
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            fecha.add("Vendedor: " + usuario + "\nFolio: " + idventa + "\nFecha: "
                    + fechaVenta + "\n\n");
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);
            Encabezado.addCell("");
            //info empresa
            String infoFarmacia = "SELECT * FROM farmacia";
            String slogan = "";
            try {
                con = Conexion.getConnection();
                ps = con.prepareStatement(infoFarmacia);
                rs = ps.executeQuery();
                if (rs.next()) {
                    slogan = rs.getString("slogan");
                    Encabezado.addCell("Ruc:    " + rs.getString("ruc") + "\nNombre: " + rs.getString("nombre") + "\nTeléfono: " + rs.getString("telefono") + "\nDirección: " + rs.getString("direccion") + "\n\n");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            //cliente
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(cli);

            PdfPTable laboratorio = new PdfPTable(3);
            laboratorio.setWidthPercentage(100);
            laboratorio.getDefaultCell().setBorder(0);
            float[] columnWidthsCliente = new float[]{50f, 25f, 25f};
            laboratorio.setWidths(columnWidthsCliente);
            laboratorio.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);
            laboratorio.addCell(cliNom);
            laboratorio.addCell(cliTel);
            laboratorio.addCell(cliDir);
            String infoCliente = "SELECT * FROM clientes WHERE id = ?";
            try {
                ps = con.prepareStatement(infoCliente);
                ps.setInt(1, cliente);
                rs = ps.executeQuery();
                if (rs.next()) {
                    laboratorio.addCell(rs.getString("nombre"));
                    laboratorio.addCell(rs.getString("telefono"));
                    laboratorio.addCell(rs.getString("direccion") + "\n\n");
                } else {
                    laboratorio.addCell("Publico en General");
                    laboratorio.addCell("S/N");
                    laboratorio.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(laboratorio);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Descripción.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
            String infoDetalleVenta = "{CALL sp_obtener_detalle_venta(?)}";
            try {
                cs = con.prepareCall(infoDetalleVenta);
                cs.setInt(1, idventa);
                rs = cs.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total S/: " + total);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion \n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
            Paragraph gr = new Paragraph();
            gr.add(Chunk.NEWLINE);
            gr.add(slogan);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public Map<String, Integer> reportForCantSale() {
        Map<String, Integer> listMap = new HashMap<>();

        // Consulta SQL para agrupar ventas por vendedor
        String sql = "SELECT u.nombre AS vendedor, COUNT(v.id) AS cantidad_ventas "
                + "FROM ventas v "
                + "JOIN usuarios u ON v.empleado = u.id "
                + "WHERE u.rol = 'vendedor' "
                + "GROUP BY u.nombre "
                + "ORDER BY cantidad_ventas DESC;";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String vendedor = rs.getString("vendedor");
                int cantidadVentas = rs.getInt("cantidad_ventas");
                listMap.put(vendedor, cantidadVentas);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return listMap;

    }

    @Override
    public List<Map<String, Object>> readAll3(String nombre) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.fecha, v.total, u.nombre "
                + "FROM ventas v "
                + "JOIN usuarios u ON v.empleado = u.id "
                + "WHERE u.nombre = ?";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("fecha", rs.getString("fecha"));
                map.put("total", rs.getDouble("total"));
                map.put("vendedor", rs.getString("nombre"));
                lista.add(map);
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return lista;
    }

    @Override
    public Map<Integer, Integer> reportCantVentasXaño(int anio) {
        Map<Integer, Integer> listMap = new HashMap<>();
        String sql = "SELECT MONTH(STR_TO_DATE(fecha, '%d/%m/%Y')) AS mes, COUNT(*) AS cantidad_ventas "
                + "FROM ventas "
                + "WHERE YEAR(STR_TO_DATE(fecha, '%d/%m/%Y')) = ? "
                + "GROUP BY mes "
                + "ORDER BY mes";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, anio);

            rs = ps.executeQuery();
            while (rs.next()) {
                int mes = rs.getInt("mes");
                int cantidadVentas = rs.getInt("cantidad_ventas");
                listMap.put(mes, cantidadVentas);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return listMap;
    }

    @Override
    public Map<Integer, Integer> reportCantVentasXmes( int anio, int mes) {
        Map<Integer, Integer> reporteVentasXmes = new HashMap();
        String sql = "SELECT DAY(STR_TO_DATE(fecha, '%d/%m/%Y')) AS dia, COUNT(*) AS cantidad_ventas "
                + "FROM ventas "
                + "WHERE YEAR(STR_TO_DATE(fecha, '%d/%m/%Y')) = ? AND MONTH(STR_TO_DATE(fecha, '%d/%m/%Y')) = ? "
                + "GROUP BY dia";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, anio);
            ps.setInt(2, mes);
            rs = ps.executeQuery();

            while (rs.next()) {
                int dia = rs.getInt("dia");
                int cantidadVentas = rs.getInt("cantidad_ventas");
                reporteVentasXmes.put(dia, cantidadVentas);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reporteVentasXmes;
    }

}
