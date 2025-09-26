import javax.swing.JOptionPane;
import seguridad.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oct88
 */
public class Inventario extends javax.swing.JFrame {
    Connection conex=null;
    Statement stm=null;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Inventario.class.getName());
    private String usuario = "hello";
    private boolean estado = false;
    private boolean busqueda = false;
    /**
     * Creates new form Inventario
     */
    public Inventario() {
        initComponents();
    }
    
    public Inventario(String nuevo, boolean estado) {
        this.usuario = nuevo;
        this.estado = estado;
        initComponents();
        lblUsuario.setText(usuario);
        conectar();
        llenarCombo();
        crearTabla();
        tblProductos.setAutoCreateRowSorter(true);
    }
    
    public void conectar(){
        String url="jdbc:mysql://localhost:3306/vistaalmar";
        String usuario="root";
        String pass="";
        try{
            conex=DriverManager.getConnection(url,usuario,pass);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"error en conexion "+ex,"error",1);
        }       
    }
    
    public void llenarCombo(){
        try{
                stm=conex.createStatement();
                ResultSet fila = stm.executeQuery("SELECT nombreTipo FROM tipoProductos");
                while(fila.next()){
                    cmbTipo.addItem(fila.getString("nombreTipo"));
                }             
            }catch(SQLException ex){
                  JOptionPane.showMessageDialog(null, "Error en database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);               
             }
    }
    
    public void crearTabla(){
        try{
            DefaultTableModel modeloBorrar = (DefaultTableModel) tblProductos.getModel();
            modeloBorrar.setRowCount(0);
            stm = conex.createStatement();
            int index = cmbTipo.getSelectedIndex();
            ResultSet lista;
            if(index!=0){
                String sentencia = cmbTipo.getSelectedItem().toString();
                lista = stm.executeQuery(
                "SELECT p.codProducto, p.nomProducto, t.nombreTipo, p.precioActual, p.stock " +
                "FROM productos p " +
                "INNER JOIN tipoProductos t ON p.codTipo = t.codTipo " +
                "WHERE t.nombreTipo = '" + sentencia + "'"
                 );   
            }
            else{
                lista = stm.executeQuery(
                "SELECT p.codProducto, p.nomProducto, t.nombreTipo, p.precioActual, p.stock " +
                "FROM productos p " +
                "INNER JOIN tipoProductos t ON p.codTipo = t.codTipo"
            );
            }
            DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
            modelo.setRowCount(0);
            while (lista.next()) {
                Object data[] = {
                    lista.getInt("codProducto"),
                    lista.getString("nomProducto"),
                    lista.getString("nombreTipo"),
                    lista.getDouble("precioActual"),
                    lista.getInt("Stock")
                };
                modelo.addRow(data);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"error en conexion "+ex,"error",1);
        }
    }
    
    public void crearFiltroTabla(){
        try{
            DefaultTableModel modeloBorrar = (DefaultTableModel) tblProductos.getModel();
            modeloBorrar.setRowCount(0);
            stm = conex.createStatement();
            int index = cmbTipo.getSelectedIndex();
            String nombre = txtBuscar.getText().trim();
            ResultSet lista;
            if(index!=0){
                String sentencia = cmbTipo.getSelectedItem().toString();
                lista = stm.executeQuery(
                "SELECT p.codProducto, p.nomProducto, t.nombreTipo, p.precioActual, p.stock " +
                "FROM productos p " +
                "INNER JOIN tipoProductos t ON p.codTipo = t.codTipo " +
                "WHERE t.nombreTipo = '" + sentencia + "' AND p.nomProducto LIKE '%" + nombre + "%'"
                 );   
            }
            else{
                lista = stm.executeQuery(
                "SELECT p.codProducto, p.nomProducto, t.nombreTipo, p.precioActual, p.stock " +
                "FROM productos p " +
                "INNER JOIN tipoProductos t ON p.codTipo = t.codTipo WHERE p.nomProducto LIKE '%" + nombre + "%'"
            );
            }
            DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
            modelo.setRowCount(0);
            while (lista.next()) {
                Object data[] = {
                    lista.getInt("codProducto"),
                    lista.getString("nomProducto"),
                    lista.getString("nombreTipo"),
                    lista.getDouble("precioActual"),
                    lista.getInt("Stock")
                };
                modelo.addRow(data);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"error en conexion "+ex,"error",1);
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

        btgFiltro = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        btnPrincipal = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        btnCompra = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnPrecio = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox<>();
        btnReiniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUsuario.setText("txt");

        btnPrincipal.setText("Menu Principal");
        btnPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrincipalActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnRegresar.setText("Inicio");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnCompra.setText("Compra Productos ");
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });

        btnVenta.setText("Venta Productos");
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Tipo", "Precio", "Stock"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        btnCrear.setText("Crear Producto");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnBorrar.setText("Borrar Producto");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPrecio.setText("Precio");

        btnPrecio.setText("Modificar Precio");
        btnPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrecioActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos" }));
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        btnReiniciar.setText("<html>Reiniciar<br> Tabla</html>");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnCompra)
                        .addGap(77, 77, 77)
                        .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(158, 158, 158))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(208, 208, 208)
                                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPrecio)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnPrecio, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
        new MenuAcceso().setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrincipalActionPerformed
        this.dispose();
        new MenuPrincipal(usuario,estado).setVisible(true);
    }//GEN-LAST:event_btnPrincipalActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        this.dispose();
        new Proveedores(usuario,estado).setVisible(true);
    }//GEN-LAST:event_btnCompraActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        this.dispose();
        new Venta(usuario,estado).setVisible(true);
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        this.dispose();
        new CrearProducto(usuario,estado).setVisible(true);
    }//GEN-LAST:event_btnCrearActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        if(busqueda==true){
            crearFiltroTabla();
            return;
        }
        crearTabla();
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void btnPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrecioActionPerformed
       int price = Integer.parseInt(txtPrecio.getText());
       int fila = tblProductos.getSelectedRow();
       if(fila==-1){
           JOptionPane.showMessageDialog(null,"Seleccione un Producto");
           return;
       }
       int codigo = (int) tblProductos.getValueAt(fila, 0);
       try{
            stm = conex.createStatement();
            stm.executeUpdate("UPDATE productos SET precioActual = " + price + " WHERE codProducto = " + codigo);
            JOptionPane.showMessageDialog(null,"Precio Actualizado ");
            if(cmbTipo.getSelectedIndex()==0){
                crearTabla();
            }
            else{
                cmbTipo.setSelectedIndex(0);
            }
            
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"error en conexion "+ex,"error",1);
        }
    }//GEN-LAST:event_btnPrecioActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
   
        cmbTipo.setSelectedIndex(0); 
        txtBuscar.setText("");
        this.busqueda = false;
        crearTabla();
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.busqueda = true;
        crearFiltroTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed

       int fila = tblProductos.getSelectedRow();
       if(fila==-1){
           JOptionPane.showMessageDialog(null,"Seleccione un Producto");
           return;
       }
       int codigo = (int) tblProductos.getValueAt(fila, 0);
       int codigo2 = (int) tblProductos.getValueAt(fila, 4);
       if(codigo2 != 0){
           JOptionPane.showMessageDialog(null,"Producto Existe en Otras Tablas", "Advertencia", JOptionPane.WARNING_MESSAGE);
           return;
       }
       try{
            stm = conex.createStatement();
            stm.executeUpdate("DELETE FROM productos WHERE codProducto = " + codigo);
            JOptionPane.showMessageDialog(null,"Producto Borrado");
            if(cmbTipo.getSelectedIndex()==0){
                crearTabla();
            }
            else{
                cmbTipo.setSelectedIndex(0);
            }
            
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"error en conexion "+ex,"error",1);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Inventario().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgFiltro;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCompra;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnPrecio;
    private javax.swing.JButton btnPrincipal;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVenta;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
