import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServidorUDP {
	
	public static void main (String args[]){
		try{
	    	DatagramSocket socketUDP = new DatagramSocket(6789);
	    	byte[] bufer = new byte[1000];
	    	
	    	while(true){
	    		
	    		// Construimos el DatagramPacket para recibir peticiones
	    		DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
	    		
	    		// Leemos una petición del DatagramSocket
	    		socketUDP.receive(peticion);
	    		
	    		System.out.print("Datagrama recibido del host: " + peticion.getAddress());
	    		System.out.println(" desde el puerto remoto: " + peticion.getPort());
	    		
	    		byte[] time = getTime().getBytes();
	    		
	    		// Construimos el DatagramPacket para enviar la respuesta
	    		DatagramPacket respuesta = new DatagramPacket(time, time.length, 
	    				peticion.getAddress(), peticion.getPort());
	    		
	    		// Enviamos la respuesta, que es un eco
	    		// En principio, ya no es un eco. Ahora envia la hora super pro
	    		new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							Thread.sleep(3000);
							socketUDP.send(respuesta);
						}
						catch(Exception e){e.printStackTrace();}
						
					}
	    			
	    		}).start();;
	    		
	    	}
		}
	    catch(SocketException e){ System.out.println("Socket: " + e.getMessage());}
	    catch (IOException e){ System.out.println("IO: " + e.getMessage());}
		
    }
	
	private static String getTime(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        
        return sdf.format(cal.getTime()).toString();
	}
	
	
	
	
}