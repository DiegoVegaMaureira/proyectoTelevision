package television;

public class Planes {
	private int id;
	private int precio;
	
	public Planes ( int p ) {
		id = p;
		
		if (p == 1) {
			precio = 14990;
		}
		
		else if (p == 2) {
			precio = 17990;
		}
		
		else if (p == 3) {
			precio = 19990;
		}
		
		else if (p == 4) {
			precio = 24990;
		}
		
		else {
			precio = 0;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public int getPrecioPlan() {
		return precio;
	}
}