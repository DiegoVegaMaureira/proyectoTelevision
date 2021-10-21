/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package television;

/**
 *
 * @author Diego
 */
public abstract class Sector {
    private String nombre;
    public boolean tieneClientes = false;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public abstract void mostrarListaClientes();

}
