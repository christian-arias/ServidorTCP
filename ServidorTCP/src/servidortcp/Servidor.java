/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidortcp;

import java.net.ServerSocket;
import java.net.Socket;
import javax.sql.DataSource;

/**
 *
 * @author ChristianXavier
 */
public class Servidor implements Runnable{
    public ServerSocket ss;
    private Socket s;
    
    //Constructor de la clase Servidor
    public Servidor(){
    }

    public void run() {
        try{
            ss = new ServerSocket(5555);
            System.out.println("Servidor> Servidor inició exitosamente");
            System.out.println("Servidor> Esperando conexiones");
            
            while (true){
                //Espera y acepta un nuevo Cliente
                s = ss.accept();
                System.out.println("Servidor> Cliente conectado");
                
                //Se instancia una nueva clase Cliente y se lanza en un hilo aparte
                HiloServidor hc = new HiloServidor(s);
                Thread hilo = new Thread(hc);
                hilo.start();
            }
        } catch (Exception ex){
            cerrarServidor();
            ex.printStackTrace();
        }
    }
    
    public void cerrarServidor(){
        try{
            s.close();
            ss.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}