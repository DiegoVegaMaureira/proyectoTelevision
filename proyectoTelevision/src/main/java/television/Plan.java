package television;

public class Plan {
    private int id;
    private int precio;

    public Plan ( int p ) {
        id = p;

        switch (p) {
            case 1:
                precio = 14990;
                break;
            case 2:
                precio = 17990;
                break;
            case 3:
                precio = 19990;
                break;
            case 4:
                precio = 24990;
                break;
            default:
                precio = 0;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public int getPrecioPlan() {
        return precio;
    }
}