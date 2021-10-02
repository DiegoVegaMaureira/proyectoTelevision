/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package television;

import java.util.HashMap;

/**
 *
 * @author Diego
 */
public class Region {
    private int numero;
    public boolean tieneClientes = false;
    private String nombre;
    private HashMap<Integer,Comuna> comunas = new HashMap<>();
    
    public Region(int num , String n){
        numero = num;
        nombre = n;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public Comuna getComuna(int comuna){
        return comunas.get(comuna);
    }
    
    public void agregarComuna(String n , int pob){
        Comuna aux = new Comuna(n, pob);
        this.comunas.put(comunas.size(), aux);
    }
        
        public void eliminarComuna(int identificador){
        int i;

        comunas.remove(identificador);
        for(i = identificador ; i < comunas.size() ; i++){
            comunas.put(i, comunas.get(i+1));
        }

       // comunas.remove(i);
    }
    
    public void guardarClientesArchivo(){
        int i;
        for (i = 0; i < comunas.size() ; i++){
            this.comunas.get(i).llenarArchivosTexto();
        }
    }
    public float pocentajeCaptacion(){
        int i;
        
        float suma = 0;
        for(i = 0 ; i < comunas.size() ; i++){
            suma += comunas.get(i).porcentajeCaptacion();
        }
        
        return suma;
    }
    
    public void setOferta(int d){
        int i;
        
        for(i = 0 ; i < comunas.size() ; i++){
            this.comunas.get(i).setOferta(d);
        }
    }
    
    public void mostrarComunas(){
        int i;
        
        for(i = 0 ; i < comunas.size() ; i++){
            System.out.println((i+1+")"+comunas.get(i).getNombre()));
        }
    }
    
    public void limpiarListaClientes(){
        int i;
        for (i = 0 ; i < comunas.size() ; i++)
            this.comunas.get(i).limpiarListaClientes();
    }
    
    public void leerClientes(){   
        int i;
        for (i = 0 ; i < comunas.size() ; i++)
            this.comunas.get(i).leerClientes();
    }
    
    public void generarReporte(){
        int i;
        for (i = 0 ; i < comunas.size() ; i++)
            this.comunas.get(i).generarReporte();
    }
}
