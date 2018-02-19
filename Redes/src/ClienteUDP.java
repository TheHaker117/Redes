import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClienteUDP {
	
	// Los argumentos proporcionan el mensaje y el nombre del servidor
	public static void main(String args[]){
		
		try{
			DatagramSocket socketUDP = new DatagramSocket();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(">> ");
			byte[] mensaje = reader.readLine().getBytes();
			
			
			
//			byte[] mensaje = args[0].getBytes();
//			InetAddress hostServidor = InetAddress.getByName(args[1]);
			InetAddress hostServidor = InetAddress.getByName("localhost");
			int puertoServidor = 6789;
			
			// Construimos un datagrama para enviar el mensaje al servidor
//			DatagramPacket peticion = new DatagramPacket(mensaje, args[0].length(), hostServidor, puertoServidor);
			DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puertoServidor);
			
			
			// Enviamos el datagrama
			socketUDP.send(peticion);
			
			// Construimos el DatagramPacket que contendrá la respuesta
			byte[] bufer = new byte[1000];
			DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
			
			socketUDP.setSoTimeout(5000);
			socketUDP.receive(respuesta);
			
			// Enviamos la respuesta del servidor a la salida estandar
			System.out.println("~>> Server time: " + new String(respuesta.getData()));
			System.out.println("~>> Client time:" + getTime());
			
			// Cerramos el socket
			socketUDP.close();
			

		}
		catch(SocketException e){ System.out.println("Socket: " + e.getMessage());}
		catch(IOException e){ System.out.println("IO: " + e.getMessage());}
		
	}
	
	private static String getTime(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        
        return sdf.format(cal.getTime()).toString();
	}
	
}