/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package television;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class PantallaIngreso extends javax.swing.JFrame {
    String rutIngresado;
    File archivoEjecutivos;
    static Scanner s;
    static ArrayList<Admin> ejecutivos;
    static File archivo;
    static HashMap<Integer,Region> regiones;
    static Admin usuarioAdmin = null;
    static Cliente usuarioCliente = null;
    static int regionSeleccionada;
    static int comunaSeleccionada;
    static int clienteSeleccionado;
    static int funcionBusqueda;
    
    
    Admin buscarAdmin(String rut , ArrayList<Admin> array){
        int i;
        
        for(i = 0 ; i < array.size() ; i++){
            if(array.get(i).getRut().equals(rut))
                return array.get(i);
        }
        
        return null;
    }
    
    Cliente buscarCliente(String rut , HashMap<Integer,Region> regiones){
        int i,j;
        
        for(i = 1 ; i < 16 ; i++){
            for(j = 0 ; j < regiones.get(i).getCantidadComunas() ; j++){
                if(regiones.get(i).getComuna(j).getCliente(rut) != null)
                    return regiones.get(i).getComuna(j).getCliente(rut);
            }
        }
        
        return null;
    }
    
    void generarReporte() throws IOException{
        String ruta = ("./src/txt/clientes.txt");
        File file = new File(ruta);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file,false); // el writer reescribirá todo el archivo
        BufferedWriter bw = new BufferedWriter(fw);
        int i,j,k;
        //bw.write("Comuna: "+nombre+":\n");
        for(i  = 1 ; i < 16 ; i++){
            for(j = 0 ; j < regiones.get(i).getCantidadComunas() ; j++){
                for(k = 0 ; k < regiones.get(i).getComuna(j).casasInscritas ; k++){
                    try{
                        bw.write(regiones.get(i).getComuna(j).getCliente(k).getNombre() + (",")+
                        regiones.get(i).getComuna(j).getCliente(k).getRut() + (",")+
                        regiones.get(i).getComuna(j).getCliente(k).getDireccion() + (",")+
                        regiones.get(i).getComuna(j).getCliente(k).getPlan() + (",")+
                        regiones.get(i).getComuna(j).getCliente(k).getRegion()+ (",")+
                        regiones.get(i).getComuna(j).getCliente(k).getComuna()+ ("\n")); 
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        bw.close();
    }
    
    /**
     * Creates new form Pantalla
     * @throws java.io.FileNotFoundException
     */
    public PantallaIngreso() throws FileNotFoundException, IOException {
        initComponents();
        initComponents2();
    }
    
    private void initComponents2() throws FileNotFoundException, IOException{
        archivoEjecutivos = new File("./src/txt/Ejecutivos.txt");
        s = new Scanner(archivoEjecutivos);
        ejecutivos = new ArrayList<>();
        String linea;
        String lineas[] = null;
        int codigoRegion , codigoComuna,i;

        while(s.hasNext()){
            linea = s.nextLine();
            lineas = linea.split(",");
            ejecutivos.add(new Admin(lineas[1], lineas[0]));
        }
        
        try{
            File archivo = new File("./src/reporte/reporte.txt");
            archivo.delete();
        }catch(Exception e){
           System.out.println(e);
        }        
        
       File comunas = new File("./src/txt/comunas.txt");
       
       s = new Scanner(comunas);
       
       regiones = new HashMap<>();
       
        regiones.put(1 , new Region(1 , "Region de Tarapacá"));
        regiones.put(2 , new Region(2 , "Region de Antofagasta"));
        regiones.put(3 , new Region(3 , "Region de Atacama"));
        regiones.put(4 , new Region(4 , "Region de Coquimbo")) ;
        regiones.put(5 , new Region(5 , "Region de Valparaiso"));
        regiones.put(6 , new Region(6 , "Region de O'Higgins"));
        regiones.put(7 , new Region(7 , "Region de Maule"));
        regiones.put(8 , new Region(8 , "Region de BioBio"));
        regiones.put(9 , new Region(9 , "Region de Araucania"));
        regiones.put(10, new Region(10, "Region de Los Lagos"));
        regiones.put(11, new Region(11, "Region de Aysén"));
        regiones.put(12, new Region(12, "Region de Magallanes"));
        regiones.put(13, new Region(13, "Region Metropolitana de Santiago"));
        regiones.put(14, new Region(14, "Region de Los Rios"));
        regiones.put(15, new Region(15, "Region de Arica y Parinacota"));
        
        
        while(s.hasNextLine()){
            linea = s.nextLine();
            lineas = linea.split(",");
            
            codigoRegion = Integer.parseInt(lineas[0]) / 1000;
            codigoComuna = Integer.parseInt(lineas[0]) % 1000;
            
            regiones.get(codigoRegion).agregarComuna(lineas[1], codigoComuna , 100);
        }
        for (i = 1; i <= 15 ; i++)
            regiones.get(i).leerClientes();
        
        //archivo texto clientes
        File clientes = new File("./src/txt/clientes.txt");
       
       

       if(clientes.exists()){
           s = new Scanner(clientes);
           while(s.hasNextLine()){
                linea = s.nextLine();
                lineas = linea.split(",");
                String nombre = lineas[0];
                String rut = lineas[1];
                String direccion = lineas[2];
                int plan = Integer.parseInt(lineas[3]);
                int region = Integer.parseInt(lineas[4]);
                int comuna = Integer.parseInt(lineas[5]);

                regiones.get(region).getComuna(comuna).agregarCliente(nombre, rut, direccion, plan, region, comuna);
           }
       }
    }
    
    void popUp(String mensaje){
        popUpFrame.setVisible(true);
        labelPopUp.setText(mensaje);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frameIngresoDatos = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        registroRut = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        registroNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        registroDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboListaPlanes = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        comboRegiones = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        botonRegistrarClienteNuevo = new javax.swing.JButton();
        comboComunas = new javax.swing.JComboBox<>();
        labelAdvertenciaRegistro = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ventanaListaClientes = new javax.swing.JFrame();
        jLabel13 = new javax.swing.JLabel();
        scrollListaClientes = new javax.swing.JScrollPane();
        panelListaClientes = new javax.swing.JTextArea();
        frameEliminarCliente = new javax.swing.JFrame();
        botonEliminarCliente = new javax.swing.JButton();
        fieldRutEliminar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        labelRutNoRegistrado = new javax.swing.JLabel();
        ventanaEditarDatos = new javax.swing.JFrame();
        jLabel14 = new javax.swing.JLabel();
        fieldNuevoNombre = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        fieldNuevaDireccion = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        comboNuevaRegion = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        comboNuevaComuna = new javax.swing.JComboBox<>();
        botonAplicarCambios = new javax.swing.JButton();
        frameCabiarPlan = new javax.swing.JFrame();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comboCambiarPlan = new javax.swing.JComboBox<>();
        botonAceptarCambioPlan = new javax.swing.JButton();
        popUpFrame = new javax.swing.JFrame();
        labelPopUp = new javax.swing.JLabel();
        botonCerrarPopUp = new javax.swing.JButton();
        paneles = new javax.swing.JTabbedPane();
        panelIngresoRut = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        botonAgregarCliente1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        ingresoRut = new javax.swing.JTextField();
        BotonIngresoRut = new javax.swing.JButton();
        instruccionIngresoRut = new javax.swing.JLabel();
        advertenciaRutNoRegistrado = new javax.swing.JLabel();
        panelAdmin = new javax.swing.JPanel();
        botonAgregarCliente = new javax.swing.JButton();
        botonVentanaEliminarCliente = new javax.swing.JButton();
        botonListaCliente = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        labelNombreAdmin = new javax.swing.JLabel();
        botonCerrarSesion = new javax.swing.JButton();
        panelCliente = new javax.swing.JPanel();
        botonEditarDatos = new javax.swing.JButton();
        botonCancelarSuscripcion = new javax.swing.JButton();
        botonCambiarPlan = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        labelNombreCLiente = new javax.swing.JLabel();
        labelRegionCLiente = new javax.swing.JLabel();
        labelComunaCLiente = new javax.swing.JLabel();
        labelRutCLiente = new javax.swing.JLabel();
        labelDireccionCliente = new javax.swing.JLabel();
        botonCerrarSesionCliente = new javax.swing.JButton();

        frameIngresoDatos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frameIngresoDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        frameIngresoDatos.setMinimumSize(new java.awt.Dimension(600, 500));
        frameIngresoDatos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                frameIngresoDatosWindowClosing(evt);
            }
        });

        jLabel1.setText("Rut sin puntos y con guion");

        jLabel3.setText("Nombre completo");

        jLabel4.setText("Direccion");

        jLabel5.setText("Plan");

        comboListaPlanes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione un plan-", "Plan via internet, sin cableado 100 canales $14990", "Plan via internet, sin cableado 120 canales (20HD) $17990", "Plan cableado con antena satelital 150 canales (50HD) $19990", "Plan cableado con antena satelital 200 canales (100HD) $24990" }));

        jLabel6.setText("Region");

        comboRegiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione Region-", "Región de Tarapacá.", "Región de Antofagasta.", "Región de Atacama.", "Región de Coquimbo.", "Región de Valparaíso.", "Region de O'Higgins.", "Región del Maule.", "Región del Biobío.", "Región de La Araucanía.", "Región de Los Lagos.", "Región de Aysén.", "Región de Magallanes.", "Región Metropolitana de Santiago.", "Región de Los Ríos.", "Región de Arica y Parinacota." }));
        comboRegiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRegionesActionPerformed(evt);
            }
        });

        jLabel7.setText("Comuna");

        botonRegistrarClienteNuevo.setForeground(new java.awt.Color(0, 0, 255));
        botonRegistrarClienteNuevo.setText("Aceptar");
        botonRegistrarClienteNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarClienteNuevoActionPerformed(evt);
            }
        });

        comboComunas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..."}));
        comboComunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboComunasActionPerformed(evt);
            }
        });

        labelAdvertenciaRegistro.setForeground(new java.awt.Color(255, 0, 0));
        labelAdvertenciaRegistro.setText(" ");

        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("*Obligatorio");

        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("*Obligatorio");

        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("*Obligatorio");

        jLabel11.setForeground(new java.awt.Color(255, 0, 51));
        jLabel11.setText("*Obligatorio");

        jLabel12.setForeground(new java.awt.Color(255, 0, 51));
        jLabel12.setText("*Obligatorio");

        javax.swing.GroupLayout frameIngresoDatosLayout = new javax.swing.GroupLayout(frameIngresoDatos.getContentPane());
        frameIngresoDatos.getContentPane().setLayout(frameIngresoDatosLayout);
        frameIngresoDatosLayout.setHorizontalGroup(
            frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboListaPlanes, 0, 580, Short.MAX_VALUE)
                    .addComponent(comboRegiones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registroRut)
                    .addComponent(registroNombre)
                    .addComponent(registroDireccion)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frameIngresoDatosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonRegistrarClienteNuevo))
                    .addComponent(comboComunas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                        .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11))
                            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12))
                            .addComponent(labelAdvertenciaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frameIngresoDatosLayout.setVerticalGroup(
            frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameIngresoDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registroRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registroNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registroDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboListaPlanes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboRegiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frameIngresoDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboComunas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(labelAdvertenciaRegistro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(botonRegistrarClienteNuevo)
                .addContainerGap())
        );

        ventanaListaClientes.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ventanaListaClientes.setMinimumSize(new java.awt.Dimension(600, 600));

        jLabel13.setText("Lista de clientes:");

        panelListaClientes.setColumns(20);
        panelListaClientes.setRows(5);
        scrollListaClientes.setViewportView(panelListaClientes);

        javax.swing.GroupLayout ventanaListaClientesLayout = new javax.swing.GroupLayout(ventanaListaClientes.getContentPane());
        ventanaListaClientes.getContentPane().setLayout(ventanaListaClientesLayout);
        ventanaListaClientesLayout.setHorizontalGroup(
            ventanaListaClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaListaClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ventanaListaClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollListaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(ventanaListaClientesLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ventanaListaClientesLayout.setVerticalGroup(
            ventanaListaClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaListaClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollListaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );

        frameEliminarCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frameEliminarCliente.setMinimumSize(new java.awt.Dimension(350, 184));

        botonEliminarCliente.setText("Eliminar");
        botonEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarClienteActionPerformed(evt);
            }
        });

        fieldRutEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldRutEliminarActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingrese Rut a eliminar");

        labelRutNoRegistrado.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout frameEliminarClienteLayout = new javax.swing.GroupLayout(frameEliminarCliente.getContentPane());
        frameEliminarCliente.getContentPane().setLayout(frameEliminarClienteLayout);
        frameEliminarClienteLayout.setHorizontalGroup(
            frameEliminarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameEliminarClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frameEliminarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frameEliminarClienteLayout.createSequentialGroup()
                        .addGap(0, 261, Short.MAX_VALUE)
                        .addComponent(botonEliminarCliente))
                    .addGroup(frameEliminarClienteLayout.createSequentialGroup()
                        .addGroup(frameEliminarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(frameEliminarClienteLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelRutNoRegistrado, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fieldRutEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frameEliminarClienteLayout.setVerticalGroup(
            frameEliminarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameEliminarClienteLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(frameEliminarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelRutNoRegistrado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fieldRutEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(botonEliminarCliente))
        );

        jLabel14.setText("Nombre:");

        fieldNuevoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNuevoNombreActionPerformed(evt);
            }
        });

        jLabel15.setText("Direccion:");

        fieldNuevaDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNuevaDireccionActionPerformed(evt);
            }
        });

        jLabel18.setText("Region");

        comboNuevaRegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione Region-", "Región de Tarapacá.", "Región de Antofagasta.", "Región de Atacama.", "Región de Coquimbo.", "Región de Valparaíso.", "Region de O'Higgins.", "Región del Maule.", "Región del Biobío.", "Región de La Araucanía.", "Región de Los Lagos.", "Región de Aysén.", "Región de Magallanes.", "Región Metropolitana de Santiago.", "Región de Los Ríos.", "Región de Arica y Parinacota." }));
        comboNuevaRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNuevaRegionActionPerformed(evt);
            }
        });

        jLabel19.setText("Comuna");

        comboNuevaComuna.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..."}));
        comboNuevaComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNuevaComunaActionPerformed(evt);
            }
        });

        botonAplicarCambios.setText("Aplicar");
        botonAplicarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAplicarCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ventanaEditarDatosLayout = new javax.swing.GroupLayout(ventanaEditarDatos.getContentPane());
        ventanaEditarDatos.getContentPane().setLayout(ventanaEditarDatosLayout);
        ventanaEditarDatosLayout.setHorizontalGroup(
            ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaEditarDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboNuevaRegion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboNuevaComuna, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventanaEditarDatosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonAplicarCambios))
                    .addGroup(ventanaEditarDatosLayout.createSequentialGroup()
                        .addGroup(ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ventanaEditarDatosLayout.createSequentialGroup()
                                .addGroup(ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldNuevoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldNuevaDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ventanaEditarDatosLayout.setVerticalGroup(
            ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaEditarDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(fieldNuevoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ventanaEditarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(fieldNuevaDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboNuevaRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboNuevaComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(botonAplicarCambios)
                .addContainerGap())
        );

        frameCabiarPlan.setMinimumSize(new java.awt.Dimension(390, 150));

        jLabel16.setForeground(new java.awt.Color(255, 0, 51));
        jLabel16.setText("*Obligatorio");

        jLabel17.setText("Plan");

        comboCambiarPlan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione un plan-", "Plan via internet, sin cableado 100 canales $14990", "Plan via internet, sin cableado 120 canales (20HD) $17990", "Plan cableado con antena satelital 150 canales (50HD) $19990", "Plan cableado con antena satelital 200 canales (100HD) $24990" }));
        comboCambiarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCambiarPlanActionPerformed(evt);
            }
        });

        botonAceptarCambioPlan.setText("Aplicar");
        botonAceptarCambioPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarCambioPlanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frameCabiarPlanLayout = new javax.swing.GroupLayout(frameCabiarPlan.getContentPane());
        frameCabiarPlan.getContentPane().setLayout(frameCabiarPlanLayout);
        frameCabiarPlanLayout.setHorizontalGroup(
            frameCabiarPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frameCabiarPlanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frameCabiarPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCambiarPlan, 0, 370, Short.MAX_VALUE)
                    .addGroup(frameCabiarPlanLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frameCabiarPlanLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonAceptarCambioPlan))
        );
        frameCabiarPlanLayout.setVerticalGroup(
            frameCabiarPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameCabiarPlanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frameCabiarPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCambiarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(botonAceptarCambioPlan)
                .addContainerGap())
        );

        popUpFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        popUpFrame.setMinimumSize(new java.awt.Dimension(220, 150));

        labelPopUp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPopUp.setText("Insertar Texto");

        botonCerrarPopUp.setText("Aceptar");
        botonCerrarPopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarPopUpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout popUpFrameLayout = new javax.swing.GroupLayout(popUpFrame.getContentPane());
        popUpFrame.getContentPane().setLayout(popUpFrameLayout);
        popUpFrameLayout.setHorizontalGroup(
            popUpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popUpFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPopUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popUpFrameLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(botonCerrarPopUp)
                .addGap(69, 69, 69))
        );
        popUpFrameLayout.setVerticalGroup(
            popUpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popUpFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelPopUp, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonCerrarPopUp)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mientel");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                listenerCerrarVentana(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                listenerCerrarVentana(evt);
            }
        });

        paneles.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Bienvenido a Mientel"));

        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 0, 255)));

        botonAgregarCliente1.setText("Registrese Aqui!");
        botonAgregarCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAgregarCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(88, 88, 88))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAgregarCliente1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 0, 255)));

        ingresoRut.setNextFocusableComponent(BotonIngresoRut);
        ingresoRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIngresoRutActionPerformed(evt);
            }
        });

        BotonIngresoRut.setText("Ingresar");
        BotonIngresoRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIngresoRutActionPerformed(evt);
            }
        });
        BotonIngresoRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BotonIngresoRutKeyReleased(evt);
            }
        });

        instruccionIngresoRut.setText("Ingrese rut sin puntos y con guion");

        advertenciaRutNoRegistrado.setForeground(new java.awt.Color(255, 0, 0));
        advertenciaRutNoRegistrado.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(instruccionIngresoRut)
                        .addGap(0, 56, Short.MAX_VALUE))
                    .addComponent(ingresoRut, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(advertenciaRutNoRegistrado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonIngresoRut)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(instruccionIngresoRut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ingresoRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonIngresoRut)
                    .addComponent(advertenciaRutNoRegistrado))
                .addContainerGap(225, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelIngresoRutLayout = new javax.swing.GroupLayout(panelIngresoRut);
        panelIngresoRut.setLayout(panelIngresoRutLayout);
        panelIngresoRutLayout.setHorizontalGroup(
            panelIngresoRutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngresoRutLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelIngresoRutLayout.setVerticalGroup(
            panelIngresoRutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        paneles.addTab("Ingreso", panelIngresoRut);

        botonAgregarCliente.setText("Nuevo Cliente");
        botonAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarClienteActionPerformed(evt);
            }
        });

        botonVentanaEliminarCliente.setText("Eliminar Cliente");
        botonVentanaEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVentanaEliminarClienteActionPerformed(evt);
            }
        });

        botonListaCliente.setText("Lista Clientes");
        botonListaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListaClienteActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 0, 255)));

        labelNombreAdmin.setText(" ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNombreAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNombreAdmin)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        botonCerrarSesion.setText("Cerrar Sesion");
        botonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAdminLayout = new javax.swing.GroupLayout(panelAdmin);
        panelAdmin.setLayout(panelAdminLayout);
        panelAdminLayout.setHorizontalGroup(
            panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdminLayout.createSequentialGroup()
                        .addGroup(panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botonVentanaEliminarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonAgregarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonListaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAdminLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelAdminLayout.setVerticalGroup(
            panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdminLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAdminLayout.createSequentialGroup()
                        .addComponent(botonAgregarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonVentanaEliminarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonListaCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(botonCerrarSesion)
                .addContainerGap())
        );

        paneles.addTab("Ejecutivos", panelAdmin);

        botonEditarDatos.setText("Editar Datos");
        botonEditarDatos.setPreferredSize(new java.awt.Dimension(99, 23));
        botonEditarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarDatosActionPerformed(evt);
            }
        });

        botonCancelarSuscripcion.setText("Cancelar Suscripcion");
        botonCancelarSuscripcion.setPreferredSize(new java.awt.Dimension(99, 23));
        botonCancelarSuscripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarSuscripcionActionPerformed(evt);
            }
        });

        botonCambiarPlan.setText("Cambiar Plan");
        botonCambiarPlan.setPreferredSize(new java.awt.Dimension(99, 23));
        botonCambiarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCambiarPlanActionPerformed(evt);
            }
        });

        labelNombreCLiente.setText(" ");

        labelRegionCLiente.setText(" ");

        labelComunaCLiente.setText(" ");

        labelRutCLiente.setText(" ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNombreCLiente, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(labelRutCLiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelRegionCLiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelComunaCLiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDireccionCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNombreCLiente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelRutCLiente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelRegionCLiente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelComunaCLiente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelDireccionCliente)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        botonCerrarSesionCliente.setText("Cerrar Sesion");
        botonCerrarSesionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelClienteLayout = new javax.swing.GroupLayout(panelCliente);
        panelCliente.setLayout(panelClienteLayout);
        panelClienteLayout.setHorizontalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(botonCambiarPlan, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(botonCancelarSuscripcion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(botonEditarDatos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClienteLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonCerrarSesionCliente)))
                .addContainerGap())
        );
        panelClienteLayout.setVerticalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(botonEditarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonCambiarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonCancelarSuscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(botonCerrarSesionCliente)
                .addContainerGap())
        );

        paneles.addTab("Clientes", panelCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paneles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paneles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneles.setEnabledAt(1, false);
        paneles.setEnabledAt(2, false);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BotonIngresoRutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BotonIngresoRutKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonIngresoRutKeyReleased

    private void BotonIngresoRutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIngresoRutActionPerformed
        // TODO add your handling code here:
        rutIngresado = ingresoRut.getText();
        int pestaña;

        Admin admin = buscarAdmin(rutIngresado, ejecutivos);
        Cliente cliente = buscarCliente(rutIngresado, regiones);
        if (admin != null){
            pestaña = 1;
            usuarioAdmin = admin;
        }
        else if(cliente != null){
            pestaña = 2;
            usuarioCliente = cliente;
        }
        else{
            advertenciaRutNoRegistrado.setText("Rut no registrado!");
            pestaña = 0;
        }

        paneles.setEnabledAt(0,!(admin != null) && !(cliente!= null));
        paneles.setEnabledAt(1,  admin != null);
        paneles.setEnabledAt(2,  cliente != null);
        paneles.setSelectedIndex(pestaña);
        
        if(admin != null){
            labelNombreAdmin.setText(usuarioAdmin.getNombre());
        }
        else if(cliente != null){
            labelNombreCLiente.setText(usuarioCliente.getNombre());
            labelRutCLiente.setText(usuarioCliente.getRut());
            labelRegionCLiente.setText(regiones.get(usuarioCliente.getRegion()).getNombre());
            labelComunaCLiente.setText(regiones.get(usuarioCliente.getRegion()).getComuna(usuarioCliente.getComuna()).getNombre());
            labelDireccionCliente.setText(usuarioCliente.getDireccion());
        }
        
        ingresoRut.setText("");
        
    }//GEN-LAST:event_BotonIngresoRutActionPerformed

    private void botonAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarClienteActionPerformed
        // TODO add your handling code here:
        frameIngresoDatos.setVisible(true);
    }//GEN-LAST:event_botonAgregarClienteActionPerformed

    private void comboRegionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRegionesActionPerformed
        // TODO add your handling code here:
        
        regionSeleccionada = comboRegiones.getSelectedIndex();
        int i;
        comboComunas.removeAllItems();
        
        if(regionSeleccionada > 0){
            for(i = 0 ; i < regiones.get(regionSeleccionada).getCantidadComunas() ; i++){
                comboComunas.addItem(regiones.get(regionSeleccionada).getComuna(i).getNombre());
            }
        } else {
            comboComunas.addItem("...");
        }
        
    }//GEN-LAST:event_comboRegionesActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        ingresoRut.requestFocusInWindow();
    }//GEN-LAST:event_formWindowActivated

    private void comboComunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboComunasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboComunasActionPerformed

    private void botonRegistrarClienteNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarClienteNuevoActionPerformed
        
        if(registroRut.getText().equals("")){
            labelAdvertenciaRegistro.setText("Debe ingresar un rut valido.");
        } else if (buscarCliente(registroRut.getText(), regiones) != null){
            labelAdvertenciaRegistro.setText("Rut ya registrado.");
        } else if (registroNombre.getText().equals("")){
            labelAdvertenciaRegistro.setText("Debe ingresar un nombre valido.");
        } else if (comboRegiones.getSelectedIndex() == 0){
            labelAdvertenciaRegistro.setText("Debe Seleccionar una region.");
        } else {
            regiones.get(comboRegiones.getSelectedIndex()).getComuna(comboComunas.getSelectedIndex()).agregarCliente(registroNombre.getText(), registroRut.getText(), registroDireccion.getText() ,comboListaPlanes.getSelectedIndex(),comboRegiones.getSelectedIndex(), comboComunas.getSelectedIndex() );
            frameIngresoDatos.dispose();
            registroDireccion.setText("");
            registroNombre.setText("");
            registroRut.setText("");
            comboComunas.setSelectedIndex(0);
            comboListaPlanes.setSelectedIndex(0);
            comboRegiones.setSelectedIndex(0);
            }
        
    }//GEN-LAST:event_botonRegistrarClienteNuevoActionPerformed

    private void botonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarSesionActionPerformed
        
        rutIngresado = null;
        paneles.setEnabledAt(0, true);
        paneles.setEnabledAt(1, false);
        paneles.setEnabledAt(2, false);
        paneles.setSelectedIndex(0);
        ingresoRut.requestFocus();
    }//GEN-LAST:event_botonCerrarSesionActionPerformed

    private void botonVentanaEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVentanaEliminarClienteActionPerformed
        // TODO add your handling code here:
        
        frameEliminarCliente.setVisible(true);
        
    }//GEN-LAST:event_botonVentanaEliminarClienteActionPerformed

    private void botonListaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListaClienteActionPerformed
        // TODO add your handling code here:
        
        ventanaListaClientes.setVisible(true);
        
        int i,j,k;
        
        for(i = 1 ; i < 16 ; i++){
            panelListaClientes.append(regiones.get(i).getNombre() + ":\n");
            
            for(j = 0 ; j < regiones.get(i).getCantidadComunas() ; j++){
                panelListaClientes.append("      +" + regiones.get(i).getComuna(j).getNombre() + ":\n");
                
                for(k = 0 ; k < regiones.get(i).getComuna(j).cantidadClientes() ; k++){
                    panelListaClientes.append("             -" + regiones.get(i).getComuna(j).getCliente(k).getNombre() + ", ");
                    panelListaClientes.append(regiones.get(i).getComuna(j).getCliente(k).getRut()+ ", ");
                    panelListaClientes.append(regiones.get(i).getComuna(j).getCliente(k).getPlan()+ "\n");
                }
            }
        }
        
        panelListaClientes.setCaretPosition(0);
        panelListaClientes.setEditable(false);
    }//GEN-LAST:event_botonListaClienteActionPerformed

    private void listenerCerrarVentana(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_listenerCerrarVentana
        try {
            // TODO add your handling code here:

            generarReporte();
        } catch (IOException ex) {
            Logger.getLogger(PantallaIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_listenerCerrarVentana

    private void frameIngresoDatosWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_frameIngresoDatosWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_frameIngresoDatosWindowClosing

    private void botonEditarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarDatosActionPerformed
        // TODO add your handling code here:
        ventanaEditarDatos.setVisible(true);
    }//GEN-LAST:event_botonEditarDatosActionPerformed

    private void comboNuevaRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNuevaRegionActionPerformed
        // TODO add your handling code here:
        
        int reg = usuarioCliente.getRegion();
        int com = usuarioCliente.getComuna();
        
        regionSeleccionada = comboNuevaRegion.getSelectedIndex();
        int i;
        comboNuevaComuna.removeAllItems();
        
        if(regionSeleccionada > 0){
            for(i = 0 ; i < regiones.get(regionSeleccionada).getCantidadComunas() ; i++){
                comboNuevaComuna.addItem(regiones.get(regionSeleccionada).getComuna(i).getNombre());
            }
            
            regiones.get(reg).getComuna(com).getCliente(usuarioCliente.getRut()).setRegion(regionSeleccionada);
        } else {
            comboNuevaComuna.addItem("...");
        }
    }//GEN-LAST:event_comboNuevaRegionActionPerformed

    private void comboNuevaComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNuevaComunaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_comboNuevaComunaActionPerformed

    private void fieldNuevoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNuevoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNuevoNombreActionPerformed

    private void fieldNuevaDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNuevaDireccionActionPerformed
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_fieldNuevaDireccionActionPerformed

    private void botonAplicarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAplicarCambiosActionPerformed
        // TODO add your handling code here:
        int reg = usuarioCliente.getRegion();
        int com = usuarioCliente.getComuna();
        
        
        if(fieldNuevoNombre.getText().equals("")){
            regiones.get(reg).getComuna(com).getCliente(usuarioCliente.getRut()).setNombre(fieldNuevoNombre.getText());
        }
        
        if(fieldNuevaDireccion.getText().equals("")){
            regiones.get(reg).getComuna(com).getCliente(usuarioCliente.getRut()).setDireccion(fieldNuevaDireccion.getText());
        }
        
        if(regionSeleccionada > 0){
            regiones.get(regionSeleccionada).
                getComuna(com).agregarCliente(usuarioCliente.getNombre(), rutIngresado, usuarioCliente.getDireccion(), 
                    usuarioCliente.getPlan(), regionSeleccionada, comboNuevaComuna.getSelectedIndex());
            
            regiones.get(reg).getComuna(com).eliminarCliente(usuarioCliente.getRut());
        }
        
        ventanaEditarDatos.dispose();
    }//GEN-LAST:event_botonAplicarCambiosActionPerformed

    private void botonAceptarCambioPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarCambioPlanActionPerformed
        // TODO add your handling code here:
        
        if(comboCambiarPlan.getSelectedIndex() > 0){
            regiones.get(usuarioCliente.getRegion()).getComuna(usuarioCliente.getComuna()).getCliente(usuarioCliente.getRut())
                .setPlan(comboCambiarPlan.getSelectedIndex());
            
            frameCabiarPlan.dispose();
            popUp("Plan cambiado correctamente");
        }
        
    }//GEN-LAST:event_botonAceptarCambioPlanActionPerformed

    private void comboCambiarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCambiarPlanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCambiarPlanActionPerformed

    private void botonCambiarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCambiarPlanActionPerformed
        // TODO add your handling code here:
        
        frameCabiarPlan.setVisible(true);
    }//GEN-LAST:event_botonCambiarPlanActionPerformed

    private void botonCerrarPopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarPopUpActionPerformed
        // TODO add your handling code here:
        popUpFrame.dispose();
    }//GEN-LAST:event_botonCerrarPopUpActionPerformed

    private void botonCancelarSuscripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarSuscripcionActionPerformed
        // TODO add your handling code here:
        popUp("Gracias por su tiempo con nosotros");
        
        regiones.get(usuarioCliente.getRegion()).getComuna(usuarioCliente.getComuna())
                .eliminarCliente(usuarioCliente.getRut());
        
        botonCerrarSesion.doClick();
    }//GEN-LAST:event_botonCancelarSuscripcionActionPerformed

    private void fieldRutEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldRutEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldRutEliminarActionPerformed

    private void botonEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarClienteActionPerformed
        // TODO add your handling code here:
        
        Cliente eliminado = buscarCliente(fieldRutEliminar.getText(), regiones);
        
        if (eliminado == null){
            labelRutNoRegistrado.setText("Rut no registrado");
        } else {
            popUp(eliminado.getNombre() + "eliminad@ correctamente");
            
            frameEliminarCliente.dispose();
            
            regiones.get(eliminado.getRegion()).getComuna(eliminado.getComuna()).
                    eliminarCliente(eliminado.getRut());
        }
    }//GEN-LAST:event_botonEliminarClienteActionPerformed

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String args[]) throws FileNotFoundException {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /*
        int i;
        int codigo;
        String nombreComuna;
        String linea;
        String lineas[];
        */
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PantallaIngreso().setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PantallaIngreso.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PantallaIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonIngresoRut;
    private javax.swing.JLabel advertenciaRutNoRegistrado;
    private javax.swing.JButton botonAceptarCambioPlan;
    private javax.swing.JButton botonAgregarCliente;
    private javax.swing.JButton botonAgregarCliente1;
    private javax.swing.JButton botonAplicarCambios;
    private javax.swing.JButton botonCambiarPlan;
    private javax.swing.JButton botonCancelarSuscripcion;
    private javax.swing.JButton botonCerrarPopUp;
    private javax.swing.JButton botonCerrarSesion;
    private javax.swing.JButton botonCerrarSesionCliente;
    private javax.swing.JButton botonEditarDatos;
    private javax.swing.JButton botonEliminarCliente;
    private javax.swing.JButton botonListaCliente;
    private javax.swing.JButton botonRegistrarClienteNuevo;
    private javax.swing.JButton botonVentanaEliminarCliente;
    private javax.swing.JComboBox<String> comboCambiarPlan;
    private javax.swing.JComboBox<String> comboComunas;
    private javax.swing.JComboBox<String> comboListaPlanes;
    private javax.swing.JComboBox<String> comboNuevaComuna;
    private javax.swing.JComboBox<String> comboNuevaRegion;
    private javax.swing.JComboBox<String> comboRegiones;
    private javax.swing.JTextField fieldNuevaDireccion;
    private javax.swing.JTextField fieldNuevoNombre;
    private javax.swing.JTextField fieldRutEliminar;
    private javax.swing.JFrame frameCabiarPlan;
    private javax.swing.JFrame frameEliminarCliente;
    private javax.swing.JFrame frameIngresoDatos;
    private javax.swing.JTextField ingresoRut;
    private javax.swing.JLabel instruccionIngresoRut;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel labelAdvertenciaRegistro;
    private javax.swing.JLabel labelComunaCLiente;
    private javax.swing.JLabel labelDireccionCliente;
    private javax.swing.JLabel labelNombreAdmin;
    private javax.swing.JLabel labelNombreCLiente;
    private javax.swing.JLabel labelPopUp;
    private javax.swing.JLabel labelRegionCLiente;
    private javax.swing.JLabel labelRutCLiente;
    private javax.swing.JLabel labelRutNoRegistrado;
    private javax.swing.JPanel panelAdmin;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JPanel panelIngresoRut;
    private javax.swing.JTextArea panelListaClientes;
    private javax.swing.JTabbedPane paneles;
    private javax.swing.JFrame popUpFrame;
    private javax.swing.JTextField registroDireccion;
    private javax.swing.JTextField registroNombre;
    private javax.swing.JTextField registroRut;
    private javax.swing.JScrollPane scrollListaClientes;
    private javax.swing.JFrame ventanaEditarDatos;
    private javax.swing.JFrame ventanaListaClientes;
    // End of variables declaration//GEN-END:variables
}