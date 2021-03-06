package servidortcp;

import java.awt.Event;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author Christian Arias
 * @email christian_arias_89@homail.com
 */
public class HiloServidor implements Runnable{
    private Socket s;
    private BufferedInputStream bis;
    private InputStreamReader isr;
    private StringBuffer instr;
    private boolean morir = false;
    
    public HiloServidor(Socket s) throws IOException{
        this.s = s;
        bis = new BufferedInputStream(this.s.getInputStream());
        isr = new InputStreamReader(bis);
    }

    public void run() {
        try{
            System.out.println("Hilo " + Thread.currentThread().getId() + "> Conexión recibida desde " + s.getInetAddress());
            while(!morir){
                this.recibir();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void recibir(){
        try{
            StringBuffer instr = new StringBuffer();
            
            int c;
            while ( (c = isr.read()) != 37) instr.append( (char) c);
            String recibido = instr.toString();
            
            if (recibido.equals("F")) System.out.println(instr);
            
            //Si recibo "C" indica que se enviara el UID de la tarjeta
            if (recibido.equals("C")){
                instr = new StringBuffer();

                /**
                 * Empiezo a recibir el UID de la tarjeta. Utilizo BufferedInput
                 * Stream ya que me permite recibir los bytes sin convertir a un
                 * set de caracteres ASCII lo cual provocaria que se reciba
                 * simbolos raros o numeros erroneos
                */
                Thread.sleep(200);
                System.out.println("Hilo " + Thread.currentThread().getId() + "> " + bis.available() + " bytes disponibles"); //Cuenta el número de bytes disponibles para ser leidos
                if (bis.available() == 5){
                    while ( (c = bis.read()) != 37 ) instr.append(Integer.toString(c));
                    System.out.println("Hilo " + Thread.currentThread().getId() + "> " + "UID: " + instr);
                } else {
                    System.out.println("Acerque nuevamente");
                    isr.close();
                    bis.close();
                    this.s.close();
                    morir = true;
                }
            } 
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }    
}