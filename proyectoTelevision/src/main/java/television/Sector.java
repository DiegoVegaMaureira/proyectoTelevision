package television;

public class Sector {
	private int numero;
	private int cantidadCasas;
	private int cantClientes;
	private int oferta;
	private Cliente clientes[];
	
	public Sector(int num, int pob) {
		numero = num;
		cantidadCasas = pob;
		cantClientes = 0;
		oferta = 100;
		clientes = new Cliente[1];
		
	}
	
	public double porcentajeCaptacion() {
		return (double) cantidadCasas/cantClientes;
	}
	
	public void agregarCliente() {
		cantClientes++;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public void setOferta(int n) {
		this.oferta = n;
                
                int i;
                
                for(i = 0 ;  i < cantClientes ; i++){
                    clientes[i].setOferta(n);
                }
	}
        
        public Cliente[] getListaCliente(){
            return clientes;
        }
	
	public Cliente buscarCliente(String r) {
            int i;
            
            for(i = 0 ; i < cantClientes ; i++){
                if(clientes[i].getRut().equals(r)){
                    return clientes[i];
                }
            }
            
            //en caso de no existir dentro del arreglo
            return null;
        }
        
        public void mostrarListaClientes(){
            int i;
            
            System.out.println("Distrito : "+ numero);
            
            for(i  = 0 ; i < cantClientes ; i++){
                System.out.println("          Cliente "+ (i + 1) + ":");
                System.out.println("                    Nombre      : "+ clientes[i].getNombre());
                System.out.println("                    Plan        : "+ clientes[i].getPlan());
                System.out.println("                    Pago mensual: "+ clientes[i].getPrecio());
            }
        }
	
	public void agregarCliente(String nombre, String rut , int plan) {
		
		if (cantClientes == 0) {
			clientes[0] = new Cliente(nombre, rut , numero, plan);
                        cantClientes++;
		}
		else if ((cantClientes > 0) && (cantClientes < cantidadCasas)) {
		
			Cliente aux[] = new Cliente[cantClientes + 1];
			int i;
			for (i = 0 ; i < clientes.length ; i++) {
				aux[i] = clientes[i];
			}
			
			aux[aux.length - 1] = new Cliente(nombre, rut , numero, plan);
			
			clientes = aux;
                        
                        cantClientes++;
		} else {
			System.out.print("Todas las casas del sector son clientes");
		}
	}
        
        public void eliminarCliente(String n){
            int i,j;
            
            for(i = 0 ; i < cantClientes ; i++){
                if(clientes[i].getRut().equals(n)){
                   for (j = i ; j < cantClientes-1 ; j++){
                       clientes[j] = clientes[j+1];
                   }
                   
                   this.cantClientes--;
                   return;
                }
            }
        }

}
