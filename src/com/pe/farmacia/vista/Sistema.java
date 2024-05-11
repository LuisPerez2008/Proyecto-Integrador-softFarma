package com.pe.farmacia.vista;

import com.pe.farmacia.dao.ClienteDao;
import com.pe.farmacia.dao.FarmaciaDao;
import com.pe.farmacia.dao.LaboratorioDao;
import com.pe.farmacia.dao.LoginDao;
import com.pe.farmacia.dao.MedicamentoDao;
import com.pe.farmacia.dao.VentaDao;
import com.pe.farmacia.daoimpl.ClienteDaoImpl;
import com.pe.farmacia.daoimpl.FarmaciaDaoImpl;
import com.pe.farmacia.daoimpl.LaboratorioDaoImpl;
import com.pe.farmacia.daoimpl.LoginDAOImpl;
import com.pe.farmacia.daoimpl.MedicamentoDaoImpl;
import com.pe.farmacia.daoimpl.VentaDaoImpl;
import com.pe.farmacia.modelo.Cliente;
import com.pe.farmacia.utils.Combo;
import com.pe.farmacia.modelo.Farmacia;
import com.pe.farmacia.modelo.DetalleVenta;
import com.pe.farmacia.utils.Evento;
import com.pe.farmacia.modelo.Medicamento;
import com.pe.farmacia.modelo.Laboratorio;
import com.pe.farmacia.modelo.Venta;
import com.pe.farmacia.modelo.Usuario;
import com.pe.farmacia.reportes.ReporteExcel;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Grupo 6
 */
public final class Sistema extends javax.swing.JFrame {

    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    Map<String, Object> map = new HashMap<>();
    Cliente cl = new Cliente();
    Laboratorio lb = new Laboratorio();
    Medicamento med = new Medicamento();
    Venta venta = new Venta();
    DetalleVenta detalleVenta = new DetalleVenta();
    Farmacia farmacia = new Farmacia();
    Evento eventos = new Evento();
    Usuario usr = null;
    //Utilizando el Patrón de diseño DAO
    ClienteDao clientDao = new ClienteDaoImpl();
    LaboratorioDao laboratorioDao = new LaboratorioDaoImpl();
    MedicamentoDao medDao = new MedicamentoDaoImpl();
    VentaDao ventaDao = new VentaDaoImpl();
    FarmaciaDao farmDao = new FarmaciaDaoImpl();
    LoginDao loginDao = new LoginDAOImpl();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;

    public Sistema() {
        initComponents();
    }

    public Sistema(Usuario usuarioLogeado) {
        initComponents();
        this.setTitle("Sistema de Inventario de FarmaSalud");
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/logo/icon-farmacia.png")).getImage());
        this.setLocationRelativeTo(null);
        Midate.setDate(fechaVenta);
        txtIdCliente.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdMedVenta.setVisible(false);
        txtIdVendedor.setVisible(false);
        txtIdVendedor.setEnabled(false);
        txtIdMed.setVisible(false);
        txtIdLaboratorio.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdClienteVenta.setVisible(false);
        txtIdUsuario.setVisible(false);
        btnEliminarventa.setEnabled(false);
        //btnGraficar.setVisible(false);

        llenarDatosFarmacia();
        if (usuarioLogeado.getRol().equals("Vendedor")) {
            //btnMedicamentos.setEnabled(false);
            btnEliminarMed.setVisible(false);
            btnEditarMed.setVisible(false);
            btnLaboratorios.setEnabled(false);
            btnConfig.setEnabled(false);
            btnUsuarios.setEnabled(false);
            LabelEmpleado.setText(usuarioLogeado.getNombre());
            txtIdVendedor.setText(String.valueOf(usuarioLogeado.getId()));
        } else {
            LabelEmpleado.setText(usuarioLogeado.getNombre());
            txtIdVendedor.setText(String.valueOf(usuarioLogeado.getId()));
        }
    }

    public void listarClientes() {
        List<Cliente> listaClts = clientDao.readAll();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listaClts.size(); i++) {
            ob[0] = listaClts.get(i).getId();
            ob[1] = listaClts.get(i).getDni();
            ob[2] = listaClts.get(i).getNombre();
            ob[3] = listaClts.get(i).getTelefono();
            ob[4] = listaClts.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);

    }

    public void listarLaboratorios() {
        List<Laboratorio> listaLb = laboratorioDao.readAll();
        modelo = (DefaultTableModel) TableLaboratorio.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listaLb.size(); i++) {
            ob[0] = listaLb.get(i).getId();
            ob[1] = listaLb.get(i).getRuc();
            ob[2] = listaLb.get(i).getNombre();
            ob[3] = listaLb.get(i).getTelefono();
            ob[4] = listaLb.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableLaboratorio.setModel(modelo);

    }

    public void listarUsuarios() {
        List<Usuario> listUsrs = loginDao.readAll();
        modelo = (DefaultTableModel) TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < listUsrs.size(); i++) {
            ob[0] = listUsrs.get(i).getId();
            ob[1] = listUsrs.get(i).getNombre();
            ob[2] = listUsrs.get(i).getCorreo();
            ob[3] = listUsrs.get(i).getRol();
            modelo.addRow(ob);
        }
        TableUsuarios.setModel(modelo);

    }

    public void listarMedicamentos() {
        /*List<Medicamento> ListarPro = medDao.readAll();
        modelo = (DefaultTableModel) TableMedicamento.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getNombreLaboratorio();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableMedicamento.setModel(modelo);*/

        List<Map<String, Object>> lista = new ArrayList<>();
        modelo = (DefaultTableModel) TableMedicamento.getModel();
        lista = medDao.readAll2();
        Object[] datos = new Object[6];
        for (Map<String, Object> map : lista) {
            datos[0] = map.get("id");
            datos[1] = map.get("codigo");
            datos[2] = map.get("nombre");
            datos[3] = map.get("laboratorio");
            datos[4] = map.get("stock");
            datos[5] = map.get("precio");
            modelo.addRow(datos);
        }
        TableMedicamento.setModel(modelo);

    }

    public void llenarDatosFarmacia() {
        farmacia = farmDao.searchData();
        txtIdConfig.setText("" + farmacia.getId());
        txtRucConfig.setText("" + farmacia.getRuc());
        txtNombreConfig.setText("" + farmacia.getNombre());
        txtTelefonoConfig.setText("" + farmacia.getTelefono());
        txtDireccionConfig.setText("" + farmacia.getDireccion());
        txtSloganConf.setText("" + farmacia.getSlogan());
    }

    public void listarVentas() {
        /*List<Venta> listVentas = ventaDao.readAll();
        modelo = (DefaultTableModel) TableRegistroVentas.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < listVentas.size(); i++) {
            ob[0] = listVentas.get(i).getId();
            ob[1] = listVentas.get(i).getNombre_cli();
            ob[2] = listVentas.get(i).getNombre_emp();
            ob[3] = listVentas.get(i).getFecha();
            ob[4] = listVentas.get(i).getTotal();
            modelo.addRow(ob);
        }
        TableRegistroVentas.setModel(modelo);*/

        List<Map<String, Object>> lista = new ArrayList<>();
        modelo = (DefaultTableModel) TableRegistroVentas.getModel();
        lista = ventaDao.readAll2();
        Object[] datos = new Object[5];
        for (Map<String, Object> map : lista) {
            datos[0] = map.get("id");
            datos[1] = map.get("cliente");
            datos[2] = map.get("vendedor");
            datos[3] = map.get("fecha");
            datos[4] = map.get("total");
            modelo.addRow(datos);
        }
        TableRegistroVentas.setModel(modelo);

    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LabelEmpleado = new javax.swing.JLabel();
        tipo = new javax.swing.JLabel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnLaboratorios = new javax.swing.JButton();
        btnMedicamentos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        btnEliminarventa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDNIVenta = new javax.swing.JTextField();
        txtNombreClienteventa = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdClienteVenta = new javax.swing.JTextField();
        txtIdVendedor = new javax.swing.JTextField();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        txtIdMedVenta = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDirecionCliente = new javax.swing.JTextField();
        txtIdCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableLaboratorio = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtRucLaboratorio = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNombreLaboratorio = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTelefonoLaboratorio = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDireccionLaboratorio = new javax.swing.JTextField();
        btnguardarLaboratorio = new javax.swing.JButton();
        btnEditarLaboratorio = new javax.swing.JButton();
        btnNuevoLaboratorio = new javax.swing.JButton();
        btnEliminarLaboratorio = new javax.swing.JButton();
        txtIdLaboratorio = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableMedicamento = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCodigoMed = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtNomMed = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtCantMed = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtPrecioMed = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cbxLabMed = new javax.swing.JComboBox<>();
        btnGuardarMed = new javax.swing.JButton();
        btnEditarMed = new javax.swing.JButton();
        btnEliminarMed = new javax.swing.JButton();
        btnNuevoMed = new javax.swing.JButton();
        txtIdMed = new javax.swing.JTextField();
        btnExcel = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableRegistroVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtSloganConf = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtIdConfig = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtCorreoUsuario = new javax.swing.JTextField();
        txtPassUser = new javax.swing.JPasswordField();
        btnRegistrarUsuario = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        btnEliminarUsr = new javax.swing.JButton();
        btnEditarUsuario = new javax.swing.JButton();
        txtIdUsuario = new javax.swing.JTextField();
        btnNewUsuario = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(44, 97, 114));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/logo-farmacia02.png"))); // NOI18N

        LabelEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        LabelEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelEmpleado.setText("Bienvenidos");

        tipo.setForeground(new java.awt.Color(255, 255, 255));

        btnNuevaVenta.setBackground(new java.awt.Color(14, 76, 117));
        btnNuevaVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.setBorderPainted(false);
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevaVenta.setFocusable(false);
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(14, 76, 117));
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setText("    Clientes");
        btnClientes.setBorderPainted(false);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClientes.setFocusable(false);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnLaboratorios.setBackground(new java.awt.Color(14, 76, 117));
        btnLaboratorios.setForeground(new java.awt.Color(255, 255, 255));
        btnLaboratorios.setText("  Laboratorios");
        btnLaboratorios.setBorderPainted(false);
        btnLaboratorios.setContentAreaFilled(false);
        btnLaboratorios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLaboratorios.setFocusable(false);
        btnLaboratorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaboratoriosActionPerformed(evt);
            }
        });

        btnMedicamentos.setBackground(new java.awt.Color(14, 76, 117));
        btnMedicamentos.setForeground(new java.awt.Color(255, 255, 255));
        btnMedicamentos.setText("Medicamentos");
        btnMedicamentos.setBorderPainted(false);
        btnMedicamentos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMedicamentos.setFocusable(false);
        btnMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMedicamentosMouseClicked(evt);
            }
        });
        btnMedicamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicamentosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(14, 76, 117));
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setText("       Ventas");
        btnVentas.setBorderPainted(false);
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentas.setFocusable(false);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(14, 76, 117));
        btnConfig.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig.setText("Configuración");
        btnConfig.setBorderPainted(false);
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConfig.setFocusable(false);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        btnUsuarios.setBackground(new java.awt.Color(14, 76, 117));
        btnUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setBorderPainted(false);
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUsuarios.setFocusable(false);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LabelEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(tipo)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLaboratorios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMedicamentos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfig, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelEmpleado)
                .addGap(30, 30, 30)
                .addComponent(tipo)
                .addGap(18, 18, 18)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLaboratorios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 560));

        jLabel2.setBackground(new java.awt.Color(44, 97, 114));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/header-final.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 870, 130));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(238, 238, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Código");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Nombre");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Cant");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Precio");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Stock Disponible");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodigoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 102, 30));

        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtDescripcionVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 191, 30));

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantidadVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 40, 30));

        txtPrecioVenta.setEditable(false);
        jPanel2.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 80, 30));

        txtStockDisponible.setEditable(false);
        jPanel2.add(txtStockDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 79, 30));

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIPCIÓN", "CANTIDAD", "PRECIO U.", "PRECIO TOTAL"
            }
        ));
        TableVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(40);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 843, 191));

        btnEliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/cancelar.png"))); // NOI18N
        btnEliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarventaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, -1, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("DNI:");
        jLabel8.setToolTipText("");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 352, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Nombre:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 352, -1, -1));

        txtDNIVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDNIVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDNIVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtDNIVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 375, 116, 30));

        txtNombreClienteventa.setEditable(false);
        jPanel2.add(txtNombreClienteventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 375, 169, 30));

        btnGenerarVenta.setText("Registrar");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanel2.add(btnGenerarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 90, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/dolar.png"))); // NOI18N
        jLabel10.setText("Total :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 377, -1, -1));

        LabelTotal.setText("-----");
        jPanel2.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(756, 381, -1, -1));
        jPanel2.add(txtIdClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 70, -1));
        jPanel2.add(txtIdVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, 30, -1));
        jPanel2.add(Midate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 210, 30));

        jLabel11.setText("Seleccionar:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, -1, -1));

        txtIdMedVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdMedVentaActionPerformed(evt);
            }
        });
        jPanel2.add(txtIdMedVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, 60, -1));

        jTabbedPane1.addTab("1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(238, 238, 238));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 555, 330));

        jPanel9.setBackground(new java.awt.Color(238, 238, 238));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("DNI:");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 10, -1, -1));

        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });
        jPanel9.add(txtDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 150, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Nombres:");
        jLabel13.setToolTipText("");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 50, -1, -1));
        jPanel9.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 148, 30));
        jPanel9.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 148, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Télefono:");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 90, 70, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Dirección:");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 130, -1, -1));
        jPanel9.add(txtDirecionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 148, 30));
        jPanel9.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 180, 50, -1));

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 44, -1));

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/editar.png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnEditarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 218, 44, -1));

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/borrar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnEliminarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 40, 33));

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/nuevo.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 44, -1));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, 330));

        jTabbedPane1.addTab("2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(238, 238, 238));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableLaboratorio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableLaboratorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableLaboratorioMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableLaboratorio);
        if (TableLaboratorio.getColumnModel().getColumnCount() > 0) {
            TableLaboratorio.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableLaboratorio.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableLaboratorio.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableLaboratorio.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableLaboratorio.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 57, 558, 310));

        jPanel10.setBackground(new java.awt.Color(238, 238, 238));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Ruc:");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Nombre:");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setText("Teléfono:");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("Dirección:");

        btnguardarLaboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/GuardarTodo.png"))); // NOI18N
        btnguardarLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarLaboratorioActionPerformed(evt);
            }
        });

        btnEditarLaboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/editar.png"))); // NOI18N
        btnEditarLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarLaboratorioActionPerformed(evt);
            }
        });

        btnNuevoLaboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/nuevo.png"))); // NOI18N
        btnNuevoLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoLaboratorioActionPerformed(evt);
            }
        });

        btnEliminarLaboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/borrar.png"))); // NOI18N
        btnEliminarLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarLaboratorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIdLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnguardarLaboratorio)
                            .addComponent(btnEliminarLaboratorio))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoLaboratorio)
                            .addComponent(btnEditarLaboratorio)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefonoLaboratorio)
                            .addComponent(txtDireccionLaboratorio)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRucLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtRucLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNombreLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTelefonoLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtDireccionLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnguardarLaboratorio)
                    .addComponent(btnEditarLaboratorio))
                .addGap(17, 17, 17)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoLaboratorio))
                .addGap(26, 26, 26))
        );

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, 320));

        jTabbedPane1.addTab("3", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableMedicamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "NOMBRE", "LABORATORIO", "STOCK", "PRECIO"
            }
        ));
        TableMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMedicamentoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableMedicamento);
        if (TableMedicamento.getColumnModel().getColumnCount() > 0) {
            TableMedicamento.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableMedicamento.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableMedicamento.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableMedicamento.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableMedicamento.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableMedicamento.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 540, 330));

        jPanel11.setBackground(new java.awt.Color(238, 238, 238));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Código:");
        jPanel11.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 47, -1, -1));
        jPanel11.add(txtCodigoMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 160, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Nombre:");
        jPanel11.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 89, -1, -1));
        jPanel11.add(txtNomMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 160, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Cantidad:");
        jPanel11.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 224, -1, -1));

        txtCantMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantMedKeyTyped(evt);
            }
        });
        jPanel11.add(txtCantMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 140, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Precio:");
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 179, -1, -1));

        txtPrecioMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioMedKeyTyped(evt);
            }
        });
        jPanel11.add(txtPrecioMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 140, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Laboratorio:");
        jPanel11.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 131, -1, -1));

        jPanel11.add(cbxLabMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 123, 143, 30));

        btnGuardarMed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/GuardarTodo.png"))); // NOI18N
        btnGuardarMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMedActionPerformed(evt);
            }
        });
        jPanel11.add(btnGuardarMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 42, -1));

        btnEditarMed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/editar.png"))); // NOI18N
        btnEditarMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarMedActionPerformed(evt);
            }
        });
        jPanel11.add(btnEditarMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 40, -1));

        btnEliminarMed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/borrar.png"))); // NOI18N
        btnEliminarMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMedActionPerformed(evt);
            }
        });
        jPanel11.add(btnEliminarMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 40, -1));

        btnNuevoMed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/nuevo.png"))); // NOI18N
        btnNuevoMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoMedActionPerformed(evt);
            }
        });
        jPanel11.add(btnNuevoMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 272, 40, -1));
        jPanel11.add(txtIdMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 11, -1, -1));

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/excel.png"))); // NOI18N
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel11.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, -1, -1));

        jPanel5.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 360));

        jTabbedPane1.addTab("4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(238, 238, 238));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableRegistroVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "FECHA", "TOTAL"
            }
        ));
        TableRegistroVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableRegistroVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableRegistroVentas);
        if (TableRegistroVentas.getColumnModel().getColumnCount() > 0) {
            TableRegistroVentas.getColumnModel().getColumn(0).setMinWidth(40);
            TableRegistroVentas.getColumnModel().getColumn(0).setPreferredWidth(40);
            TableRegistroVentas.getColumnModel().getColumn(0).setMaxWidth(40);
            TableRegistroVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableRegistroVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableRegistroVentas.getColumnModel().getColumn(3).setMinWidth(100);
            TableRegistroVentas.getColumnModel().getColumn(3).setPreferredWidth(100);
            TableRegistroVentas.getColumnModel().getColumn(3).setMaxWidth(100);
            TableRegistroVentas.getColumnModel().getColumn(4).setMinWidth(100);
            TableRegistroVentas.getColumnModel().getColumn(4).setPreferredWidth(100);
            TableRegistroVentas.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 80, 590, 310));

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/pdf.png"))); // NOI18N
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnPdfVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));
        jPanel6.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 46, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Registro de Ventas");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 280, -1));

        jTabbedPane1.addTab("5", jPanel6);

        jPanel7.setBackground(new java.awt.Color(238, 238, 238));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setText("RUC");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setText("NOMBRE");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, -1, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setText("TELÉFONO");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, -1, -1));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setText("DIRECCIÓN");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, -1, -1));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setText("SLOGAN");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, -1, -1));
        jPanel7.add(txtRucConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 147, 30));
        jPanel7.add(txtNombreConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 220, 30));
        jPanel7.add(txtTelefonoConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 218, 30));
        jPanel7.add(txtDireccionConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 147, 30));
        jPanel7.add(txtSloganConf, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 400, 30));

        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/sincronizar.png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });
        jPanel7.add(btnActualizarConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, -1, 35));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel32.setText("INFORMACIÓN DE LA FARMACIA");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        jPanel8.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(351, Short.MAX_VALUE)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 420, 310));

        jTabbedPane1.addTab("6", jPanel7);

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Email:");
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 48, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Contraseña:");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 107, -1, -1));

        txtCorreoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoUsuarioActionPerformed(evt);
            }
        });
        jPanel13.add(txtCorreoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 71, 226, 30));
        jPanel13.add(txtPassUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 126, 226, 30));

        btnRegistrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/agregar-usuario.png"))); // NOI18N
        btnRegistrarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarUsuarioActionPerformed(evt);
            }
        });
        jPanel13.add(btnRegistrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 54, 40));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Nombre y Apellidos:");
        jPanel13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 162, -1, -1));
        jPanel13.add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 185, 226, 30));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Rol:");
        jPanel13.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 221, -1, -1));

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor" }));
        jPanel13.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 244, 226, 30));

        btnEliminarUsr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/borrar.png"))); // NOI18N
        btnEliminarUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsrActionPerformed(evt);
            }
        });
        jPanel13.add(btnEliminarUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 60, 40));

        btnEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/editar.png"))); // NOI18N
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });
        jPanel13.add(btnEditarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 60, 40));
        jPanel13.add(txtIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 50, 30));

        btnNewUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pe/farmacia/img/nuevo.png"))); // NOI18N
        btnNewUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUsuarioActionPerformed(evt);
            }
        });
        jPanel13.add(btnNewUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 60, 40));

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 280, 380));

        TableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre Completo", "Correo", "Rol"
            }
        ));
        TableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TableUsuarios);
        if (TableUsuarios.getColumnModel().getColumnCount() > 0) {
            TableUsuarios.getColumnModel().getColumn(0).setMinWidth(50);
            TableUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
            TableUsuarios.getColumnModel().getColumn(0).setMaxWidth(50);
            TableUsuarios.getColumnModel().getColumn(3).setMinWidth(100);
            TableUsuarios.getColumnModel().getColumn(3).setPreferredWidth(100);
            TableUsuarios.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel12.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 540, 380));

        jTabbedPane1.addTab("7", jPanel12);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 95, 860, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        limpiarTable();
        listarClientes();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        LimpiarFormCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnLaboratoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaboratoriosActionPerformed
        // TODO add your handling code here:
        limpiarTable();
        listarLaboratorios();
        jTabbedPane1.setSelectedIndex(2);
        btnEditarLaboratorio.setEnabled(true);
        btnEliminarLaboratorio.setEnabled(true);
        limpiarLaboratorio();
    }//GEN-LAST:event_btnLaboratoriosActionPerformed

    private void btnMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicamentosActionPerformed
        // TODO add your handling code here:
        limpiarTable();
        listarMedicamentos();
        jTabbedPane1.setSelectedIndex(3);
        btnEditarMed.setEnabled(false);
        btnEliminarMed.setEnabled(false);
        btnGuardarMed.setEnabled(true);
        limpiarFormMedicamentos();
    }//GEN-LAST:event_btnMedicamentosActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        limpiarTable();
        listarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
        limpiarTable();
        listarUsuarios();
        btnEditarUsuario.setEnabled(false);
        btnEliminarUsr.setEnabled(false);
        btnRegistrarUsuario.setEnabled(true);
        limpiarFormUsuario();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnMedicamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMedicamentosMouseClicked
        // TODO add your handling code here:
        cbxLabMed.removeAllItems();
        llenarLaboratorio();

    }//GEN-LAST:event_btnMedicamentosMouseClicked

    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioActionPerformed
        if (!"".equals(txtNombreUsuario.getText()) && !"".equals(txtCorreoUsuario.getText()) && !"".equals(txtPassUser.getPassword()) && !"".equals(cbxRol.getSelectedItem())) {

            if (!validarDatosUsuario()) {
                return;
            }
            String rol = cbxRol.getSelectedItem().toString();
            usr = new Usuario.Builder()
                    .nombre(txtNombreUsuario.getText())
                    .correo(txtCorreoUsuario.getText())
                    .pass(String.valueOf(txtPassUser.getPassword()))
                    .rol(rol)
                    .build();

            int respuesta = loginDao.register(usr);
            if (respuesta != 1) {
                JOptionPane.showMessageDialog(null, "Error al registrar usuario, ingresa otro correo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Usuario Registrado");
            limpiarTable();
            listarUsuarios();
            limpiarFormUsuario();
        } else {
            JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
        }
    }//GEN-LAST:event_btnRegistrarUsuarioActionPerformed
    private void txtCorreoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoUsuarioActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
            if (!validarDatosFarmacia()) {
                return;
            }

            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar");
            if (pregunta == 0) {
                farmacia.setRuc(txtRucConfig.getText());
                farmacia.setNombre(txtNombreConfig.getText());
                farmacia.setTelefono(txtTelefonoConfig.getText());
                farmacia.setDireccion(txtDireccionConfig.getText());
                farmacia.setSlogan(txtSloganConf.getText());
                farmacia.setId(Integer.parseInt(txtIdConfig.getText()));
                farmDao.update(farmacia);
                JOptionPane.showMessageDialog(null, "Datos de la farmacia actualizados");
                llenarDatosFarmacia();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed

        if (txtIdVenta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            venta = ventaDao.searchSaleById(Integer.parseInt(txtIdVenta.getText()));
            usr = loginDao.searchUserById(venta.getEmpleado());
            ventaDao.convertSaleToPDF(venta.getId(), venta.getCliente(), venta.getTotal(), venta.getFecha(), usr.getNombre());
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void TableRegistroVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableRegistroVentasMouseClicked
        // TODO add your handling code here:
        int fila = TableRegistroVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableRegistroVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableRegistroVentasMouseClicked

    private void btnNuevoMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoMedActionPerformed
        // TODO add your handling code here:
        limpiarFormMedicamentos();
        btnGuardarMed.setEnabled(true);
        btnEditarMed.setEnabled(false);
        btnEliminarMed.setEnabled(false);
    }//GEN-LAST:event_btnNuevoMedActionPerformed

    private void btnEliminarMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMedActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdMed.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdMed.getText());
                medDao.delete(id);
                limpiarTable();
                limpiarFormMedicamentos();
                listarMedicamentos();
                btnEditarMed.setEnabled(false);
                btnEliminarMed.setEnabled(false);
                btnGuardarMed.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarMedActionPerformed

    private void btnEditarMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarMedActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdMed.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (!"".equals(txtCodigoMed.getText()) || !"".equals(txtNomMed.getText()) || !"".equals(txtCantMed.getText()) || !"".equals(txtPrecioMed.getText())) {

                if (!validarDatosMedicamento()) {
                    return;
                }
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar");
                if (pregunta == 0) {
                    med.setCodigo(txtCodigoMed.getText());
                    med.setNombre(txtNomMed.getText());
                    Combo itemL = (Combo) cbxLabMed.getSelectedItem();
                    med.setLaboratorio(itemL.getId());
                    med.setStock(Integer.parseInt(txtCantMed.getText()));
                    med.setPrecio(Double.parseDouble(txtPrecioMed.getText()));
                    med.setId(Integer.parseInt(txtIdMed.getText()));
                    medDao.update(med);
                    JOptionPane.showMessageDialog(null, "Medicamento Actualizado");
                    limpiarTable();
                    listarMedicamentos();
                    limpiarFormMedicamentos();
                    cbxLabMed.removeAllItems();
                    llenarLaboratorio();
                    btnEditarMed.setEnabled(false);
                    btnEliminarMed.setEnabled(false);
                    btnGuardarMed.setEnabled(true);
                }

            }
        }
    }//GEN-LAST:event_btnEditarMedActionPerformed

    private void btnGuardarMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMedActionPerformed
        // TODO add your handling code here:

        if (!"".equals(txtCodigoMed.getText()) && !"".equals(txtNomMed.getText()) && !"".equals(cbxLabMed.getSelectedItem()) && !"".equals(txtCantMed.getText()) && !"".equals(txtPrecioMed.getText())) {

            if (!validarDatosMedicamento()) {
                return;
            }
            med.setCodigo(txtCodigoMed.getText());
            med.setNombre(txtNomMed.getText());
            Combo itemL = (Combo) cbxLabMed.getSelectedItem();
            med.setLaboratorio(itemL.getId());
            med.setStock(Integer.parseInt(txtCantMed.getText()));
            med.setPrecio(Double.parseDouble(txtPrecioMed.getText()));
            int respuesta = medDao.register(med);

            if (respuesta != 1) {
                JOptionPane.showMessageDialog(null, "Error al registrar medicamento, ingresa otro código diferente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Medicamento Registrado");
            limpiarTable();
            listarMedicamentos();
            limpiarFormMedicamentos();
            cbxLabMed.removeAllItems();
            llenarLaboratorio();
            btnEditarMed.setEnabled(false);
            btnEliminarMed.setEnabled(false);
            btnGuardarMed.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan incompletos");
        }
    }//GEN-LAST:event_btnGuardarMedActionPerformed

    private void txtPrecioMedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioMedKeyTyped
        // TODO add your handling code here:
        eventos.numberDecimalKeyPress(evt, txtPrecioMed);
    }//GEN-LAST:event_txtPrecioMedKeyTyped

    private void TableMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMedicamentoMouseClicked
        // TODO add your handling code here:
        btnEditarMed.setEnabled(true);
        btnEliminarMed.setEnabled(true);
        btnGuardarMed.setEnabled(false);
        int fila = TableMedicamento.rowAtPoint(evt.getPoint());
        txtIdMed.setText(TableMedicamento.getValueAt(fila, 0).toString());
        map = medDao.searchMedicineById(Integer.parseInt(txtIdMed.getText()));
        txtCodigoMed.setText("" + String.valueOf(map.get("codigo")));
        txtNomMed.setText("" + String.valueOf(map.get("nombre")));
        txtCantMed.setText("" + String.valueOf(map.get("stock")));
        txtPrecioMed.setText("" + String.valueOf(map.get("precio")));

        //int idLab = Integer.parseInt(String.valueOf(map.get("id_laboratorio")));
        int id_lab = ((Integer) map.get("id_laboratorio"));
        String nombreLab = ((String) map.get("laboratorio"));
        //String nombreLab = String.valueOf(map.get("laboratorio"));

        cbxLabMed.setSelectedItem(new Combo(id_lab, nombreLab));
    }//GEN-LAST:event_TableMedicamentoMouseClicked

    private void btnEliminarLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarLaboratorioActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdLaboratorio.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdLaboratorio.getText());
                laboratorioDao.delete(id);
                limpiarTable();
                listarLaboratorios();
                limpiarLaboratorio();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarLaboratorioActionPerformed

    private void btnNuevoLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoLaboratorioActionPerformed
        // TODO add your handling code here:
        limpiarLaboratorio();
        btnEditarLaboratorio.setEnabled(false);
        btnEliminarLaboratorio.setEnabled(false);
        btnguardarLaboratorio.setEnabled(true);
    }//GEN-LAST:event_btnNuevoLaboratorioActionPerformed

    private void btnEditarLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarLaboratorioActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdLaboratorio.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucLaboratorio.getText()) || !"".equals(txtNombreLaboratorio.getText()) || !"".equals(txtTelefonoLaboratorio.getText()) || !"".equals(txtDireccionLaboratorio.getText())) {
                if (!validarDatosLaboratorio()) {
                    return;
                }
                lb.setRuc(txtRucLaboratorio.getText());
                lb.setNombre(txtNombreLaboratorio.getText());
                lb.setTelefono(txtTelefonoLaboratorio.getText());
                lb.setDireccion(txtDireccionLaboratorio.getText());
                lb.setId(Integer.parseInt(txtIdLaboratorio.getText()));

                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar");
                if (pregunta == 0) {
                    laboratorioDao.update(lb);
                    JOptionPane.showMessageDialog(null, "Laboratorio Actualizado");
                    limpiarTable();
                    listarLaboratorios();
                    limpiarLaboratorio();
                    btnEditarLaboratorio.setEnabled(false);
                    btnEliminarLaboratorio.setEnabled(false);
                    btnguardarLaboratorio.setEnabled(true);
                }

            }
        }
    }//GEN-LAST:event_btnEditarLaboratorioActionPerformed

    private void btnguardarLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarLaboratorioActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucLaboratorio.getText()) || !"".equals(txtNombreLaboratorio.getText()) || !"".equals(txtTelefonoLaboratorio.getText()) || !"".equals(txtDireccionLaboratorio.getText())) {
            if (!validarDatosLaboratorio()) {
                return;
            }
            lb.setRuc(txtRucLaboratorio.getText());
            lb.setNombre(txtNombreLaboratorio.getText());
            lb.setTelefono(txtTelefonoLaboratorio.getText());
            lb.setDireccion(txtDireccionLaboratorio.getText());
            int respuesta = laboratorioDao.register(lb);
            if (respuesta != 1) {
                JOptionPane.showMessageDialog(null, "Error al registrar laboratorio, ingresa otro RUC",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Laboratorio Registrado");
            limpiarTable();
            listarLaboratorios();
            limpiarLaboratorio();
            btnEditarLaboratorio.setEnabled(false);
            btnEliminarLaboratorio.setEnabled(false);
            btnguardarLaboratorio.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos esta vacios");
        }
    }//GEN-LAST:event_btnguardarLaboratorioActionPerformed

    private void TableLaboratorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableLaboratorioMouseClicked
        // TODO add your handling code here:
        btnEditarLaboratorio.setEnabled(true);
        btnEliminarLaboratorio.setEnabled(true);
        btnguardarLaboratorio.setEnabled(false);
        int fila = TableLaboratorio.rowAtPoint(evt.getPoint());
        txtIdLaboratorio.setText(TableLaboratorio.getValueAt(fila, 0).toString());
        txtRucLaboratorio.setText(TableLaboratorio.getValueAt(fila, 1).toString());
        txtNombreLaboratorio.setText(TableLaboratorio.getValueAt(fila, 2).toString());
        txtTelefonoLaboratorio.setText(TableLaboratorio.getValueAt(fila, 3).toString());
        txtDireccionLaboratorio.setText(TableLaboratorio.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableLaboratorioMouseClicked

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarFormCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");

            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());

                clientDao.delete(id);
                JOptionPane.showMessageDialog(null, "Cliente eliminado");
                limpiarTable();
                LimpiarFormCliente();
                listarClientes();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtDniCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText())) {

                if (!validarDatosCliente()) {
                    return;
                }
                cl.setDni(txtDniCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDirecionCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));

                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de editar");
                if (pregunta == 0) {
                    clientDao.update(cl);
                    JOptionPane.showMessageDialog(null, "Cliente Modificado");
                    limpiarTable();
                    LimpiarFormCliente();
                    listarClientes();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan incompletos");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtDniCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDirecionCliente.getText())) {

            if (!validarDatosCliente()) {
                return;
            }

            cl.setDni(txtDniCliente.getText());
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(txtTelefonoCliente.getText());
            cl.setDireccion(txtDirecionCliente.getText());

            int respuesta = clientDao.register(cl);

            if (respuesta != 1) {
                JOptionPane.showMessageDialog(null, "Error al registrar cliente, el DNI ingresado esta en uso",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            limpiarTable();
            LimpiarFormCliente();
            listarClientes();
            btnEditarCliente.setEnabled(false);
            btnEliminarCliente.setEnabled(false);
            btnGuardarCliente.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan incompletos");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        // TODO add your handling code here:
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDirecionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteventa.getText())) {
                RegistrarVenta();
                RegistrarDetalle();
                actualizarStock();
                LimpiarTableVenta();
                LimpiarClienteventa();
                LabelTotal.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay medicamentos en la venta");
        }

    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtDNIVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIVentaKeyTyped
        // TODO add your handling code here:
        eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtDNIVentaKeyTyped

    private void txtDNIVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIVentaKeyPressed
        // TODO add your handling code here:
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!"".equals(txtDNIVenta.getText())) {
                    int dni = Integer.parseInt(txtDNIVenta.getText());
                    cl = clientDao.searchClientByDNI(dni);
                    if (cl.getNombre() != null) {
                        txtNombreClienteventa.setText("" + cl.getNombre());
                        txtIdClienteVenta.setText("" + cl.getId());
                    } else {
                        txtDNIVenta.setText("");
                        JOptionPane.showMessageDialog(null, "El cliente no existe");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_txtDNIVentaKeyPressed

    private void btnEliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarventaActionPerformed
        // TODO add your handling code here:
        if (TableVenta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
            return;
        }
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarventaActionPerformed

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText())) {
                int id_medicamento = Integer.parseInt(txtIdMedVenta.getText());
                String descripcion = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TableVenta.getModel();
                    for (int i = 0; i < TableVenta.getRowCount(); i++) {
                        if (TableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El medicamento ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(id_medicamento);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    TableVenta.setModel(tmp);
                    TotalPagar();
                    LimparVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        // TODO add your handling code here:
        eventos.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        // TODO add your handling code here:
        eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!"".equals(txtCodigoVenta.getText())) {
                    String cod = txtCodigoVenta.getText();
                    med = medDao.searchMedicineByCode(cod);
                    if (med.getNombre() != null) {
                        txtIdMedVenta.setText("" + med.getId());
                        txtDescripcionVenta.setText("" + med.getNombre());
                        txtPrecioVenta.setText("" + med.getPrecio());
                        txtStockDisponible.setText("" + med.getStock());
                        txtCantidadVenta.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe medicamento con ese codigo");
                        LimparVenta();
                        txtCodigoVenta.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese el codigo del medicamento");
                    txtCodigoVenta.requestFocus();
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        // TODO add your handling code here:
        ReporteExcel.generateReport();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void txtIdMedVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdMedVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdMedVentaActionPerformed

    private void TableVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentaMouseClicked
        // TODO add your handling code here:
        btnEliminarventa.setEnabled(true);
    }//GEN-LAST:event_TableVentaMouseClicked

    private void btnEliminarUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsrActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdUsuario.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario");
        } else {

            if (!"".equals(txtIdUsuario.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este usuario");
                if (pregunta == 0) {
                    int id = Integer.parseInt(txtIdUsuario.getText());
                    loginDao.delete(id);
                    limpiarTable();
                    limpiarFormUsuario();
                    listarUsuarios();
                    btnEditarUsuario.setEnabled(false);
                    btnEliminarUsr.setEnabled(false);
                    btnRegistrarUsuario.setEnabled(true);
                }

            }
        }
    }//GEN-LAST:event_btnEliminarUsrActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        // TODO add your handling code here:

        if ("".equals(txtIdUsuario.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario");
        } else {
            if ("".equals(txtNombreUsuario.getText()) || "".equals(txtCorreoUsuario.getText()) || "".equals(String.valueOf(txtPassUser.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Datos incompletos");
            } else {

                if (!validarDatosUsuario()) {
                    return;
                }
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar este usuario");
                if (pregunta == 0) {
                    String rol = cbxRol.getSelectedItem().toString();
                    usr = new Usuario.Builder()
                            .id(Integer.parseInt(txtIdUsuario.getText()))
                            .nombre(txtNombreUsuario.getText())
                            .correo(txtCorreoUsuario.getText())
                            .pass(String.valueOf(txtPassUser.getPassword()))
                            .rol(rol)
                            .build();
                    loginDao.update(usr);
                    JOptionPane.showMessageDialog(null, "Usuario Actualizado");
                    limpiarTable();
                    limpiarFormUsuario();
                    listarUsuarios();
                    btnEditarUsuario.setEnabled(false);
                    btnEliminarUsr.setEnabled(false);
                    btnRegistrarUsuario.setEnabled(true);
                }

            }
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void TableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUsuariosMouseClicked
        // TODO add your handling code here:
        btnEditarUsuario.setEnabled(true);
        btnEliminarUsr.setEnabled(true);
        btnRegistrarUsuario.setEnabled(false);

        int fila = TableUsuarios.rowAtPoint(evt.getPoint());
        txtIdUsuario.setText(TableUsuarios.getValueAt(fila, 0).toString());
        usr = loginDao.searchUserById(Integer.parseInt(txtIdUsuario.getText()));
        txtNombreUsuario.setText(usr.getNombre());
        txtCorreoUsuario.setText(usr.getCorreo());
        txtPassUser.setText(usr.getPass());
        cbxRol.setSelectedItem(usr.getRol());

    }//GEN-LAST:event_TableUsuariosMouseClicked

    private void btnNewUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewUsuarioActionPerformed
        // TODO add your handling code here:
        limpiarFormUsuario();
        btnEditarUsuario.setEnabled(false);
        btnEliminarUsr.setEnabled(false);
        btnRegistrarUsuario.setEnabled(true);
    }//GEN-LAST:event_btnNewUsuarioActionPerformed

    private void txtCantMedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantMedKeyTyped
        // TODO add your handling code here:
        eventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantMedKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelEmpleado;
    private javax.swing.JLabel LabelTotal;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableLaboratorio;
    private javax.swing.JTable TableMedicamento;
    private javax.swing.JTable TableRegistroVentas;
    private javax.swing.JTable TableUsuarios;
    private javax.swing.JTable TableVenta;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarLaboratorio;
    private javax.swing.JButton btnEditarMed;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarLaboratorio;
    private javax.swing.JButton btnEliminarMed;
    private javax.swing.JButton btnEliminarUsr;
    private javax.swing.JButton btnEliminarventa;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarMed;
    private javax.swing.JButton btnLaboratorios;
    private javax.swing.JButton btnMedicamentos;
    private javax.swing.JButton btnNewUsuario;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoLaboratorio;
    private javax.swing.JButton btnNuevoMed;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnRegistrarUsuario;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnguardarLaboratorio;
    private javax.swing.JComboBox<Object> cbxLabMed;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel tipo;
    private javax.swing.JTextField txtCantMed;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoMed;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCorreoUsuario;
    private javax.swing.JTextField txtDNIVenta;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionLaboratorio;
    private javax.swing.JTextField txtDirecionCliente;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdClienteVenta;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdLaboratorio;
    private javax.swing.JTextField txtIdMed;
    private javax.swing.JTextField txtIdMedVenta;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtIdVendedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtNomMed;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreLaboratorio;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JPasswordField txtPassUser;
    private javax.swing.JTextField txtPrecioMed;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucLaboratorio;
    private javax.swing.JTextField txtSloganConf;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoLaboratorio;
    // End of variables declaration//GEN-END:variables
    private void LimpiarFormCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDirecionCliente.setText("");
    }

    private void limpiarLaboratorio() {
        txtIdLaboratorio.setText("");
        txtRucLaboratorio.setText("");
        txtNombreLaboratorio.setText("");
        txtTelefonoLaboratorio.setText("");
        txtDireccionLaboratorio.setText("");
    }

    private void limpiarFormMedicamentos() {
        txtCodigoMed.setText("");
        cbxLabMed.setSelectedItem(null);
        txtNomMed.setText("");
        txtCantMed.setText("");
        txtPrecioMed.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimparVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVenta.setText("");
    }

    private void RegistrarVenta() {
        int cliente = Integer.parseInt(txtIdClienteVenta.getText());
        int vendedor = Integer.parseInt(txtIdVendedor.getText());
        double monto = Totalpagar;
        venta.setCliente(cliente);
        venta.setEmpleado(vendedor);
        venta.setTotal(monto);
        venta.setFecha(fechaActual);
        int respuesta = ventaDao.register(venta);
        if (respuesta != 1) {
            JOptionPane.showMessageDialog(null, "Error al registrar venta",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Venta registrada");
    }

    private void RegistrarDetalle() {
        int id_venta = ventaDao.getIdNewSale();

        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id_med = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            detalleVenta.setId_med(id_med);
            detalleVenta.setCantidad(cant);
            detalleVenta.setPrecio(precio);
            //cambiar esta parte si el metodo cambio de nombre
            detalleVenta.setId_venta(id_venta);
            ventaDao.registerDetailOfSale(detalleVenta);

        }

        //int cliente = Integer.parseInt(txtIdClienteVenta.getText());
        //svDao.convertSaleToPDF(id_venta, cliente, Totalpagar, LabelEmpleado.getText());
    }

    private void actualizarStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            map = medDao.searchMedicineById(id);
            int stockAnt = ((Integer) map.get("stock"));
            int StockActual = stockAnt - cant;
            ventaDao.updateStock(StockActual, id);

        }
    }

    private void LimpiarTableVenta() {
        tmp = (DefaultTableModel) TableVenta.getModel();
        int fila = TableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarClienteventa() {
        txtDNIVenta.setText("");
        txtNombreClienteventa.setText("");
        txtIdClienteVenta.setText("");
    }

    private void limpiarFormUsuario() {
        txtNombreUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtPassUser.setText("");
    }

    private void llenarLaboratorio() {
        List<Laboratorio> lista = laboratorioDao.readAll();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxLabMed.addItem(new Combo(id, nombre));
        }
    }

    //Metodos para verfificar TextFields
    private boolean validarNumero(String datos, int longitud, String mensaje) {
        boolean esValido = datos.matches("\\d{" + longitud + "}");
        if (!esValido) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return esValido;
    }

    private boolean validarCant(String datos, int longitud, String mensaje) {
        boolean esValido = datos.matches("[1-9]\\d{0," + (longitud - 1) + "}");
        if (!esValido) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
        return esValido;
    }

    private boolean validarTexto(String texto, String mensaje) {
        boolean esValido = texto.matches("[a-zA-Z ]+") && !texto.matches(".*\\d.*");
        if (!esValido) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return esValido;
    }

    private boolean validarTextoConNum(String texto, String mensaje) {
        //acepta acentos y tildes al igual el caracter -
        boolean esValido = texto.matches("[a-zA-Z\\d \\p{L}.-]+") && !texto.matches("\\d+");
        if (!esValido) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return esValido;
    }

    private boolean validarCorreo(String texto, String mensaje) {
        boolean esValido = texto.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if (!esValido) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return esValido;
    }

    /*
    private boolean validarNumeroDecimal(String texto, String mensaje) {
        boolean esValido = texto.matches("\\d+(\\.\\d+)?");
        if (!esValido) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return esValido;
    }*/
    //Metodos validadores
    private boolean validarDatosCliente() {
        if (!validarNumero(txtDniCliente.getText().trim(), 8, "El DNI debe tener 8 números")) {
            txtDniCliente.setText("");
            return false;
        }
        if (!validarTexto(txtNombreCliente.getText().trim(), "El Nombre no contiene números")) {
            txtNombreCliente.setText("");
            return false;
        }

        if (!validarNumero(txtTelefonoCliente.getText().trim(), 9, "El Telefono debe tener 9 digitos")) {
            txtTelefonoCliente.setText("");
            return false;
        }
        if (!validarTextoConNum(txtDirecionCliente.getText().trim(), "La dirección no debe contener solo números")) {
            txtDirecionCliente.setText("");
            return false;
        }
        return true;
    }

    private boolean validarDatosLaboratorio() {
        if (!validarNumero(txtRucLaboratorio.getText().trim(), 11, "El RUC debe tener 11 números")) {
            txtRucLaboratorio.setText("");
            return false;
        }
        if (!validarTexto(txtNombreLaboratorio.getText().trim(), "El Nombre no debe contener números")) {
            txtNombreLaboratorio.setText("");
            return false;
        }

        if (!validarNumero(txtTelefonoLaboratorio.getText().trim(), 9, "El Telefono debe tener 9 digitos")) {
            txtTelefonoLaboratorio.setText("");
            return false;
        }
        if (!validarTextoConNum(txtDireccionLaboratorio.getText().trim(), "La dirección no debe contener solo números")) {
            txtDireccionLaboratorio.setText("");
            return false;
        }
        return true;
    }

    private boolean validarDatosMedicamento() {
        if (!validarNumero(txtCodigoMed.getText().trim(), 3, "El código debe tener 3 números")) {
            txtCodigoMed.setText("");
            return false;
        }
        if (!validarTextoConNum(txtNomMed.getText().trim(), "El Nombre no debe contener solo números")) {
            txtNomMed.setText("");
            return false;
        }

        if (!validarCant(txtCantMed.getText().trim(), 4, "Ingrese una cantidad válida (max 4 dígitos)")) {
            txtCantMed.setText("");
            return false;
        }
        return true;
    }

    private boolean validarDatosUsuario() {
        if (!validarCorreo(txtCorreoUsuario.getText().trim(), "Email no válido: ejemplo@dominio.com")) {
            txtCorreoUsuario.setText("");
            return false;
        }
        if (!validarTexto(txtNombreUsuario.getText().trim(), "El Nombre no debe contener números")) {
            txtNombreUsuario.setText("");
            return false;
        }

        return true;
    }

    private boolean validarDatosFarmacia() {
        if (!validarNumero(txtRucConfig.getText().trim(), 11, "El RUC debe tener 11 números")) {
            txtRucConfig.setText("");
            return false;
        }
        if (!validarTextoConNum(txtNombreConfig.getText().trim(), "El Nombre no debe contener solo números")) {
            txtNombreConfig.setText("");
            return false;
        }
        if (!validarTextoConNum(txtDireccionConfig.getText().trim(), "La dirección no debe contener solo números")) {
            txtDireccionConfig.setText("");
            return false;
        }
        if (!validarNumero(txtTelefonoConfig.getText().trim(), 9, "El Telefono debe tener 9 digitos")) {
            txtTelefonoConfig.setText("");
            return false;
        }

        if (!validarTextoConNum(txtSloganConf.getText().trim(), "El Slogan no debe contener solo números")) {
            txtSloganConf.setText("");
            return false;
        }
        return true;
    }

}
