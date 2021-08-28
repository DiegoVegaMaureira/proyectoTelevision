package television;

import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        boolean running = true;

        Sector distritos[] = new Sector[5];

        distritos[0] = new Sector(1, 5);
        distritos[1] = new Sector(2, 3);
        distritos[2] = new Sector(3, 4);
        distritos[3] = new Sector(4, 2);
        distritos[4] = new Sector(5, 7);

        while(running) {
            System.out.println("1) Inscribir cliente");
            System.out.println("2) Dar de baja ciente");
            System.out.println("3) Cambiar de plan");
            System.out.println("4) Captacion del sector");
            System.out.println("5) Oferta a cliente");
            System.out.println("6) Oferta a distrito");
            System.out.println("7) Salir");

            int in = Integer.parseInt(input.readLine());

            if(in == 1) {
                System.out.println("Distrito: ");
                int dist = Integer.parseInt(input.readLine());

                System.out.println("Nombre cliente: ");

                String nombre = input.readLine();

                System.out.println("Plan inscrito: ");

                int plan = Integer.parseInt(input.readLine());

                distritos[dist-1].agregarCliente(nombre,plan);

            }

            else if (in == 7) {
                running = false;
            }
        }

        System.out.print(distritos[0].getCliente(0).getPrecio());


    }

}