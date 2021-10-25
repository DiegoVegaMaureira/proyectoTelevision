/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package television;

/**
 *
 * @author rorro
 */
public class Admin extends Persona {
    int clave;
    
    public Admin (String n,String r){
        rut = r;
        nombre = r;        
    }
    /**
     *
     * @return
     */
    @Override
    public boolean esAdmin(){
        return true;
    }  
    
    
}
