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
    
    public Admin (String n,String r){
        this.setNombre(n);
        this.setRut(r);
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
