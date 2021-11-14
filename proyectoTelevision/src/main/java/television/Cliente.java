package television;

public class Cliente extends Persona {
    private int idCliente;
    private String direccion;
    private Plan plan; 
    private int oferta;
    private int region;
    private int comuna;

    public Cliente (String nombre , String rut , String direccion , int plan , 
            int region , int comuna) {
        this.setNombre(nombre);
        this.setRut(rut);
        direccion = direccion;
        this.plan  = new Plan(plan);
        this.region = region;
        this.comuna = comuna;
    }

    public Cliente (String n , String r  , int p , int region , int comuna) {
        this.setNombre(n);
        this.setRut(r);
        direccion = null;
        plan  = new Plan(p);
        this.region = region;
        this.comuna = comuna;
    }
    
    public int getIdCliente(){
        return idCliente;
    }
    
    public void setIdCliente (int id){
        this.idCliente = id;
    }
    
    public String getDireccion() {
        if(direccion == null){
            return "Plan por internet";
        }
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    @Override
    public boolean esAdmin(){
        return false;
    }

    public void setPlan(int n) {
        this.plan = new Plan(n);
    }

    public int getPlan(){
        return plan.getId();
    }

    public void setOferta(int n) {
        this.oferta = 100 - n;
    }

    public int getPrecio() {
        return (plan.getPrecioPlan() * oferta) / 100;
    }
    
    public int getRegion(){
        return region;
    }
    
    public int getComuna(){
        return comuna;
    }
   
        
    
}