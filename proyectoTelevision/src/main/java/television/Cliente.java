package television;

public class Cliente extends Persona {
    private int idCliente;
    private String direccion;
    private Plan plan; 
    private int oferta;

    public Cliente (String nom , String r , String d , int p) {
        nombre = nom;
        rut = r;
        direccion = d;
        plan  = new Plan(p);
    }

    public Cliente (String nom , String r  , int p) {
        nombre = nom;
        rut = r;
        direccion = null;
        plan  = new Plan(p);
    }
    
    public int getIdCliente(){
        return idCliente;
    }
    
    public void setIdCliente (int id){
        this.idCliente = id;
    }
    
    public String getDireccion() {
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
    
   
        
    
}