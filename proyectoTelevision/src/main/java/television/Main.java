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
            System.out.println("7) Lista de clientes del distrito");
            System.out.println("0) Salir");

            int in = Integer.parseInt(input.readLine());
            
            //inscribir cliente
            if(in == 1) {
                System.out.println("Distrito: ");
                int dist = Integer.parseInt(input.readLine());

                System.out.println("Nombre cliente: ");
                String nombre = input.readLine();
                
                System.out.println("Rut cliente: ");
                String rut = input.readLine();

                System.out.println("Plan inscrito: ");
                int plan = Integer.parseInt(input.readLine());

                distritos[dist-1].agregarCliente(nombre,rut,plan);

            }
            
            //dar de baja
            else if(in == 2){
                System.out.println("Distrito: ");
                int n = Integer.parseInt(input.readLine());
                
                System.out.println("Rut: ");
                String r = input.readLine();
                
                distritos[n-1].eliminarCliente(r);
                
            }
            
            //cambiar de plan a cliente
            else if(in == 3){
                System.out.println("Distrito: ");
                int n = Integer.parseInt(input.readLine());
                
                System.out.println("Rut: ");
                String r = input.readLine();
                
                System.out.println("Nuevo plan");
                int p = Integer.parseInt(input.readLine());
                
                System.out.println("Descuento: ");
                int d = Integer.parseInt(input.readLine());
                
                distritos[n-1].getCliente(r).setPlan(p);
                distritos[n-1].getCliente(r).setOferta(d);
            }
            
            //porcentaje de captacion del distrito
            else if(in == 4){
                System.out.println("Distrito: ");
                int n = Integer.parseInt(input.readLine());
                
                System.out.println("Porcentaje captacion: "+ distritos[n-1].porcentajeCaptacion()+"%");
            }
            
            //oferta a cliente
            else if(in == 5){
                System.out.println("Distrito: ");
                int n = Integer.parseInt(input.readLine());
                
                System.out.println("Rut: ");
                String r = input.readLine();
                
                System.out.println("Descuento: ");
                int d = Integer.parseInt(input.readLine());
                
                distritos[n-1].getCliente(r).setOferta(d);
            }
            
            //oferta a distrito
            else if(in == 6){
                System.out.println("Distrito: ");
                int n = Integer.parseInt(input.readLine());
                
                System.out.println("Descuento: ");
                int d = Integer.parseInt(input.readLine());
                
                distritos[n-1].setOferta(d);
                    
            }
            
            //lista de clientes del distrito
            else if(in == 7){
                System.out.println("Distrito: ");
                int n = Integer.parseInt(input.readLine());
                
                distritos[n-1].mostrarListaClientes();
            }

            else if (in == 0) {
                running = false;
            }
        }
    }

}