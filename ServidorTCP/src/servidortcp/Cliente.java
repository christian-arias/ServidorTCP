package servidortcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.ResultSet;

/**
 *
 * @author Christian Arias
 * @email christian_arias_89@homail.com
 */
public class Cliente implements Runnable { //Implementa la interfaz Runnable
    public Socket s;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    
    //Constructor de clase Cliente
    public Cliente(){
    }

    public void run() {
        try{
            s = new Socket("localhost", 5555);
            this.recibir();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    //Cerrar todos los buffers y el socket para evitar que el puerto quede abierto
    public void cerrarCliente(){
        try{
            oos.close();
            ois.close();
            s.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void recibir(){
        try{
            BufferedInputStream bis = new BufferedInputStream(this.s.getInputStream());
            InputStreamReader isr = new InputStreamReader(bis,"US-ASCII");
            StringBuffer instr = new StringBuffer();
            int c;
            while ( (c = isr.read()) != 37) instr.append( (char) c);

            System.out.println(instr);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        
    }
}