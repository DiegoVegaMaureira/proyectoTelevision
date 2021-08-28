package television;

public class Cliente {
	private String nombre;
	private int distrito;
	private Planes plan[]; 
	private int oferta;
	
	public Cliente (String nom , int dist, int p) {
		nombre = nom;
		distrito = dist;
                plan  = new Planes[1];
		plan[0] = new Planes(p);
		oferta = 100;
	}
	
	public void setNombre(String n){
		this.nombre = n;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setDistrito(int n) {
		this.distrito = n;
	}
	
	public int getDistrito() {
		return distrito;
	}
	
	public void setPlan(int n) {
		this.plan[0] = new Planes(n);
	}
	
	public void setOferta(int n) {
		this.oferta = n;
	}
	
	public int getPrecio() {
		return (plan[0].getPrecioPlan() * oferta) / 100;
	}
}