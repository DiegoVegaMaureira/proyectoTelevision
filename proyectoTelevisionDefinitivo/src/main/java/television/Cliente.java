package television;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String rut;
    private String direccion;
    private Planes plan; 
    private int oferta;

    public Cliente (String nom , String r , String d , int p) {
        nombre = nom;
        rut = r;
        direccion = d;
        plan  = new Planes(p);
    }

    public Cliente (String nom , String r  , int p) {
        nombre = nom;
        rut = r;
        direccion = null;
        plan  = new Planes(p);
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

    public void setNombre(String n){
        this.nombre = n;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRut(String r){
        this.rut = r;
    }

    public String getRut(){
        return rut;
    }

    public void setPlan(int n) {
        this.plan = new Planes(n);
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