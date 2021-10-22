package television;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    
    public void mostrarRegiones(HashMap<Integer,Region> regiones){
        int i;
        
        for(i = 1 ; i <= regiones.size() ; i++){
            System.out.print((i)+")"+regiones.get(i).getNombre());
        }
    }
   
    
    
    public static void main (String[] args) throws IOException {
        
       
        try{
            File archivo = new File("./src/reporte/reporte.txt");
            archivo.delete();;
        }catch(Exception e){
           System.out.println(e);
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        boolean running = true;
        
       
       File archivo = new File("./src/txt/comunas.txt");
       
       Scanner s = new Scanner(archivo);
       
       HashMap<Integer,Region> r = new HashMap<>();
       
        r.put(1 , new Region(1 , "Region de Tarapacá"));
        r.put(2 , new Region(2 , "Region de Antofagasta"));
        r.put(3 , new Region(3 , "Region de Atacama"));
        r.put(4 , new Region(4 , "Region de Coquimbo")) ;
        r.put(5 , new Region(5 , "Region de Valparaiso"));
        r.put(6 , new Region(6 , "Region de O'Higgins"));
        r.put(7 , new Region(7 , "Region de Maule"));
        r.put(8 , new Region(8 , "Region de BioBio"));
        r.put(9 , new Region(9 , "Region de Araucania"));
        r.put(10, new Region(10, "Region de Los Lagos"));
        r.put(11, new Region(11, "Region de Aysén"));
        r.put(12, new Region(12, "Region de Magallanes"));
        r.put(13, new Region(13, "Region Metropolitana de Santiago"));
        r.put(14, new Region(14, "Region de Los Rios"));
        r.put(15, new Region(15, "Region de Arica y Parinacota"));
        
        int i;
        int codigo;
        String nombreComuna;
        String linea;
        
       
        
        while(s.hasNextLine()){
            linea = s.nextLine();
            String lineas[] = linea.split(",");
            
            codigo = Integer.parseInt(lineas[0]) / 1000;
            nombreComuna = lineas[1];
            
            r.get(codigo).agregarComuna(nombreComuna, 100);
        }
        for (i = 1; i <= 15 ; i++)
                r.get(i).leerClientes();
        
        while(running) {
            System.out.println("1) Inscribir cliente");
            System.out.println("2) Dar de baja ciente");
            System.out.println("3) Editar datos de cliente");
            System.out.println("4) Cambiar de plan");
            System.out.println("5) Captacion del sector");
            System.out.println("6) Oferta a cliente");
            System.out.println("7) Oferta a distrito");
            System.out.println("8) Mostrar clientes");
            System.out.println("9) Salir y generar reporte en archivo .txt sobre los clientes");
            System.out.println("10) Eliminar comuna");
            System.out.println("11) Mostrar comunas");
            System.out.println("12) Agregar comuna");
            System.out.println("13) Filtrar clientes de acuerdo a la inicial de su nombre");
            System.out.println("14) Mostrar comuna con mas clientes");
            System.out.println("0) Salir");

            int opcion = Integer.parseInt(input.readLine());
            
            
            
            if(opcion == 0){
                
                for (i = 1; i <= 15 ; i++)
                    r.get(i).guardarClientesArchivo();
                break;
            }if (opcion == 9){
                for (i = 1; i <= 15 ; i++)
                    r.get(i).guardarClientesArchivo();

                for (i = 1; i <= 15 ; i++)
                    r.get(i).generarReporte();
                System.exit(0);
            }if (opcion == 11){
                for (i = 1 ; i <= 15 ; i++){
                    r.get(i).mostrarComunas();
                }
                System.out.println("Comunas mostradas con exito!");
                continue;
            }
            if (opcion == 12){
                String nombreAux;
                int region;
                System.out.println("Indique el nombre de la nueva comuna: ");
                nombreAux = input.readLine();
                System.out.println("Indique a que region pertence: ");
                region = Integer.parseInt(input.readLine());
                r.get(region).agregarComuna(nombreAux, 100);
                continue;
            }
            if (opcion == 13){
                Scanner sc = new Scanner(System.in);
                System.out.println("Indique la primera letra del nombre que busca: ");
                char letra = sc.next().charAt(0);
                for (i = 1; i <= 15 ; i++)
                    r.get(i).filtrarClientesPorCriterio(letra);
                continue;
            }
            if (opcion == 14){
                Comuna aux = r.get(1).comunaMasClientes();
                for (i = 1; i <= 15 ; i++){
                    if (r.get(i).comunaMasClientes().cantidadClientes() > aux.cantidadClientes())
                        aux = r.get(i).comunaMasClientes();
                }                
                System.out.println("La comuna con mas clientes es: " + aux.getNombre());
                continue;
            }
            
            for(i = 1 ; i <= 15 ; i++){
                System.out.println(i+")"+r.get(i).getNombre());
            }
            System.out.println("Indique numero de la region donde desea trabajar: ");
            int n = Integer.parseInt(input.readLine());
            System.out.println(r.get(n).getNombre());
            r.get(n).mostrarComunas();
            System.out.println("Indique el numero de la comuna donde desea trabajar: ");
            int comuna = Integer.parseInt(input.readLine()) - 1;
            
            //inscribir cliente
            switch (opcion) {
                case 1:
                    {
                        System.out.println("Nombre cliente (Nombre y Apellido): ");
                        String nombre = input.readLine();
                        System.out.println("Rut cliente (12345678-9): ");
                        String rut = input.readLine();
                        System.out.println("(1) Plan via internet, sin cableado 100 canales $14990 ");
                        System.out.println("(2) Plan via internet, sin cableado 120 canales (20HD) $17990 ");
                        System.out.println("(3) Plan cableado con antena satelital 150 canales (50HD) $19990 ");
                        System.out.println("(4) Plan cableado con antena satelital 200 canales (100HD) $24990 ");
                        System.out.println("Ingrese numero del plan inscrito: ");
                        int plan = Integer.parseInt(input.readLine());
                        System.out.println("Direccion cliente(En caso de ser un plan via internet ingresar '0'): ");
                        String direccion = input.readLine();
                        if (direccion.equals("0"))
                            direccion = "No registra direccion.";
                        r.get(n).getComuna(comuna).agregarCliente(nombre, rut,direccion, plan);
                        break;
                    }
                case 2:
                    {
                        System.out.println("Rut: ");
                        String rut = input.readLine();
                        r.get(n).getComuna(comuna).eliminarCliente(rut);
                        break;
                    }
                case 3:
                    {
                        System.out.println("Rut: ");
                        String rut = input.readLine();
                        r.get(n).getComuna(comuna).editarCliente(rut);
                        break;
                    }
                case 4:
                    {
                        System.out.println("Rut: ");
                        String inp = input.readLine();
                        System.out.println("Nuevo plan");
                        int p = Integer.parseInt(input.readLine());
                        System.out.println("Descuento: ");
                        int d = Integer.parseInt(input.readLine());
                        r.get(n).getComuna(comuna).cambiarPlan(inp, p);
                        r.get(n).getComuna(comuna).setOfertaCliente(inp, d);
                        break;
                    }
                case 5:
                    {
                        System.out.println("Porcentaje captacion: "+ r.get(n).pocentajeCaptacion()+"%");
                        break;
                    }
                case 6:
                    {
                        System.out.println("Rut: ");
                        String inp = input.readLine();
                        System.out.println("Descuento: ");
                        int d = Integer.parseInt(input.readLine());
                        r.get(n).getComuna(comuna).setOfertaCliente(inp, d);
                        break;
                    }
                case 7:
                    {
                        System.out.println("Descuento: ");
                        int d = Integer.parseInt(input.readLine());
                        r.get(n).setOferta(d);
                        break;
                    }
                case 8:
                    {
                    r.get(n).getComuna(comuna).mostrarListaClientes();
                    break;
                    }
                case 10:{
                    
                    r.get(n).eliminarComuna(comuna);
                    break;
                }
                
                     
            }
        }
    }
}