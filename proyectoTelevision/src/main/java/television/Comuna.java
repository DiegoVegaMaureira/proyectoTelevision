package television;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Comuna implements Filtro {
    private String nombre;
    private int codigo;
    private int region;
    public boolean tieneClientes = false;
    private int cantidadCasas;
    public int casasInscritas;
    private int oferta;
    private ArrayList<Cliente> clientes = new ArrayList<>();

    public Comuna(String n,int codigo, int pob , int reg) {
        region = reg;
        this.codigo = codigo;
        nombre = n;
        cantidadCasas = pob;
        casasInscritas = 0;
        oferta = 100;
    }
    
    public int getRegion(){
        return region;
    }

    public String getNombre(){
        return nombre;
    }

    public double porcentajeCaptacion() {
        return (double) cantidadCasas/casasInscritas;
    }

    public void setOferta(int n) {
        this.oferta = n;

        int i;

        for(i = 0 ;  i < casasInscritas; i++){
            clientes.get(i).setOferta(n);
        }
    }

    public void setOferta(double n) {
        this.oferta = (int) n;

        int i;

        for(i = 0 ;  i < casasInscritas; i++){
            clientes.get(i).setOferta( (int) n);
        }
    }
    
    public void cambiarPlan(String rut , int plan){
        int i;
        
        for(i  = 0 ; i < clientes.size() ; i++){
            if(clientes.get(i).getRut().equals(rut)){
                clientes.get(i).setPlan(plan);
            }
        }
    }
    
    public void setOfertaCliente(String rut , int descuento){
        int i;
        
        for(i  = 0 ; i < clientes.size() ; i++){
            if(clientes.get(i).getRut().equals(rut)){
                clientes.get(i).setOferta(descuento);
            }
        }
    }
    
    public Cliente getCliente(int n){
        return this.clientes.get(n);
    }
    
    public Cliente getCliente(String r) {
        int i;

        for(i = 0 ; i < clientes.size() ; i++){
            if(clientes.get(i).getRut().equals(r)){
                return clientes.get(i);
            }
        }

        //en caso de no existir dentro del arreglo
        return null;
    }
    
    public int getIdCliente(String r) {
        int i;
        for(i = 0 ; i < clientes.size() ; i++)
            if(clientes.get(i).getRut().equals(r))
                return i;

        //en caso de no existir dentro del arreglo
        return -1;
    }
   
    public void mostrarListaClientes(){
        if (casasInscritas != 0){
            int i;

            System.out.println("Clientes de "+nombre+": ");

            for(i  = 0 ; i < clientes.size() ; i++){
                System.out.println("Cliente "+ (i + 1) + ":");
                System.out.println("Nombre      : "+ clientes.get(i).getNombre());
                System.out.println("Direccion   : "+ clientes.get(i).getDireccion());
                System.out.println("Plan        : "+ clientes.get(i).getPlan());
             //   System.out.println("Pago mensual: "+ clientes.get(i).getPrecio());
            }
        }else System.out.println("No se encuentran clientes.");
    }
    
    public void llenarArchivosTexto (){
        int i;
        String ruta = (("./src/txt/") + nombre + (".txt"));
        File file = new File(ruta);
        if (clientes.size() != 0 || file.exists()){
            try {
                
                
                // Si el archivo no existe es creado
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                //bw.write(casasInscritas+("\n"));
                for(i  = 0 ; i < clientes.size() ; i++){
                   // bw.write("Cliente "+ (i + 1) + ":");
                    bw.write(clientes.get(i).getNombre() + (","));
                    bw.write(clientes.get(i).getRut() + (","));
                    bw.write(clientes.get(i).getDireccion() + (","));
                    bw.write(clientes.get(i).getPlan() + (","));
                    bw.write(clientes.get(i).getRegion() + (","));
                    bw.write(clientes.get(i).getComuna() + ("\n"));
                   
                }
                bw.close();
        } catch (Exception e) {
            e.printStackTrace();
          }
        }    
    }
    
    public void leerClientes () throws IOException{
        
        String ruta = (("./src/txt/ ") + nombre + (".txt"));
        File file = new File(ruta);
        if (file.exists() ){
            Scanner lector = new Scanner(file);
            String cadena;
            String parts[];
            while(lector.hasNextLine()){
                cadena = lector.nextLine();
                parts = cadena.split(",");
                int plan = Integer.parseInt(parts[3]);
                int reg = Integer.parseInt(parts[4]);
                int com = Integer.parseInt(parts[5]);
                agregarCliente(parts[0],parts[1],parts[2],plan,reg,com);   
                casasInscritas++;       
            }

        }
    }
    
    public void generarReporte (){
        if (clientes.size() != 0){
            try {
                String ruta = ("./src/reporte/reporte.txt");
                File file = new File(ruta);
                // Si el archivo no existe es creado
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
                int i;
                //bw.write("Comuna: "+nombre+":\n");
                for(i  = 0 ; i < clientes.size() ; i++){
                    bw.write(clientes.get(i).getNombre() + (","));
                    bw.write(clientes.get(i).getRut() + (","));
                    bw.write(clientes.get(i).getDireccion() + (","));
                    bw.write(clientes.get(i).getPlan() + (","));
                    bw.write(clientes.get(i).getRegion()+ (","));
                    bw.write(clientes.get(i).getComuna()+ ("\n"));
                }
                bw.close();
        } catch (Exception e) {
            e.printStackTrace();
          }
        }
        
    }

/*
    public void agregarCliente(String nombre, String rut , String direccion , int plan , int reg , int com) throws IOException {

        if (casasInscritas < cantidadCasas) {
            tieneClientes = true;
            Cliente aux = new Cliente(nombre, rut , direccion , plan , region , com);
            clientes.add(aux);
            casasInscritas++;
            
            //se registra en el txt correspondiente
            String ruta = (("./src/txt/") + this.nombre + (".txt"));
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            Scanner s = new Scanner(file);
            String linea = (nombre + "," + rut + "," + direccion + "," + plan + "," + region + "," + com+ "\n");
            
            while(s.hasNextLine()){}   
            bw.write(linea);
            bw.close();

        } else {
            System.out.print("Todas las casas del sector son clientes");
        }
    }
    */

    
    public void agregarCliente(String nombre, String rut , String direccion , int plan , int reg , int com) {
        if (casasInscritas < cantidadCasas) {
            tieneClientes = true;
            Cliente aux = new Cliente(nombre, rut , direccion , plan , region , com);
            clientes.add(aux);
            casasInscritas++;
        } else {
            System.out.print("Todas las casas del sector son clientes");
        }
    }
    
    public void limpiarListaClientes(){
        this.clientes.clear();
    }
/*
    public void agregarCliente(String nombre, String rut  , int plan , int reg , int com) {

        if (casasInscritas < cantidadCasas) {
            tieneClientes = true;
            Cliente aux = new Cliente(nombre, rut , plan , reg , com);
            this.clientes.add(aux);
            casasInscritas++;

        } else {
            System.out.print("Todas las casas del sector son clientes");
        }
    }
*/

    public void eliminarCliente(String rut){
        int i;
        for(i = 0 ; i < clientes.size() ; i++){
            if(clientes.get(i).getRut().equals(rut)){
                this.clientes.remove(clientes.get(i));
                return;
            }
        }

    }
    
    public boolean mostrarCliente(String r) {
                int aux = getIdCliente(r);
                if (aux == -1){
                    //System.out.println("No existe un cliente con ese rut en nuestros registros.");
                    return false;
                }
                System.out.println("                    Cliente "+ (aux + 1) + ":");
                System.out.println("                    Nombre      : "+ clientes.get(aux).getNombre());
                System.out.println("                    Direccion   : "+ clientes.get(aux).getDireccion());
                System.out.println("                    Plan        : "+ clientes.get(aux).getPlan());
                System.out.println("                    Pago mensual: "+ clientes.get(aux).getPrecio());
                return true;
        
    }
     
    public void editarCliente(String rut){
            Scanner sc = new Scanner(System.in);
            boolean existeCliente = mostrarCliente(rut);
            if (!existeCliente)
                return;
            System.out.println("Ingrese el numero del dato que desea cambiar:");
            System.out.println("1) Rut");
            System.out.println("2) Nombre");
            System.out.println("3) Direccion");
            String opcion = sc.nextLine();
            int n = Integer.parseInt(opcion);
            int idCliente = getIdCliente(rut);
            
            switch (n){
                case 1:{
                    System.out.println("Ingrese el nuevo rut:");
                    String datoNuevo = sc.nextLine();
                    clientes.get(idCliente).setRut(datoNuevo);
                    System.out.println("Rut editado con exito!");
                    break;
                }
                case 2:{
                    System.out.println("Ingrese el nuevo nombre:");
                    String datoNuevo = sc.nextLine();
                    clientes.get(idCliente).setNombre(datoNuevo);
                    System.out.println("Nombre editado con exito!");
                    break;
                }
                case 3:{
                    System.out.println("Ingrese la nueva direccion:");
                    String datoNuevo = sc.nextLine();
                    clientes.get(idCliente).setDireccion(datoNuevo);
                    System.out.println("Direccion editada con exito!");
                    break;
                }
                case 0: break;
            }
    }
   @Override
    public void filtrarClientesPorCriterio(char letra){
        int i;
        char primeraLetra;
        for(i = 0 ; i < clientes.size() ; i++){
            primeraLetra = clientes.get(i).getNombre().charAt(0);
            if(primeraLetra == letra)
                mostrarCliente(clientes.get(i).getRut());
        }        
               
    }
    
    public int cantidadClientes(){
        return clientes.size();
    }

}
