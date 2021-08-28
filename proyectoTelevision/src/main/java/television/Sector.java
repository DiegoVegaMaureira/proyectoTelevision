package television;

public class Sector {
	private int numero;
	private int cantidadCasas;
	private int cantClientes;
	private int oferta;
	public Cliente clientes[];
	
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
	}
	
	public Cliente getCliente(int n) {
		return clientes[n];
	}
	
	public void agregarCliente(String nombre , int plan) {
		
		if (cantClientes == 0) {
			clientes[0].setNombre(nombre);
			clientes[0].setPlan(plan);
			clientes[0].setDistrito(numero);
		}
		else if ((cantClientes > 0) && (cantClientes < cantidadCasas)) {
		
			Cliente aux[] = new Cliente[cantClientes + 1];
			int i;
			for (i = 0 ; i < clientes.length ; i++) {
				aux[i] = clientes[i];
			}
			
			aux[aux.length - 1] = new Cliente(nombre, numero, plan);
			
			clientes = aux;
		} else {
			System.out.print("Todas las casas del sector son clientes");
		}
	}

}
