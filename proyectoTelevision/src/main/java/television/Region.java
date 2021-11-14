/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package television;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Diego
 */
public class Region implements Filtro{
    private int numero;
    public boolean tieneClientes = false;
    private String nombre;
    private HashMap<Integer,Comuna> comunas = new HashMap<>(); // CAMBIAR A ARRAYLIST
    
    public Region(int num , String n){
        numero = num;
        nombre = n;
    }
    
    public HashMap<Integer,Comuna> getListaComunas(){
        return comunas;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public Comuna getComuna(int comuna){
        return comunas.get(comuna);
    }
    
    public int getCantidadComunas(){
        return comunas.size();
    }
    
    public void agregarComuna(String n , int cod, int pob){
        Comuna aux = new Comuna(n, comunas.size(), pob, numero);
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
    
    public void leerClientes() throws IOException{   
        int i;
        for (i = 0 ; i < comunas.size() ; i++)
            this.comunas.get(i).leerClientes();
    }
    
    public void generarReporte(){
        int i;
        for (i = 0 ; i < comunas.size() ; i++)
            this.comunas.get(i).generarReporte();
    }
    @Override
    public void filtrarClientesPorCriterio(char letra){
        int i;
        for (i = 0 ; i < comunas.size() ; i++)
            this.comunas.get(i).filtrarClientesPorCriterio(letra);
    }
    
    public Comuna comunaMasClientes(){
        int i;
        Comuna aux = this.comunas.get(0);
        for (i = 0 ; i < comunas.size() ; i++){
            if( this.comunas.get(i).cantidadClientes() > aux.cantidadClientes())
                aux = this.comunas.get(i);
        }        
        return aux;
    }
    
    public boolean mostrarCliente(String r){
        int i;
        for (i = 0; i < comunas.size() ; i++){
            if(this.comunas.get(i).mostrarCliente(r)){
                return true;
            }
                
                
        }
        return false;
    }
}
