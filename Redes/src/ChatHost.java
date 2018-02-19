import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JTextArea;

public class ChatHost{
	
	private InetAddress grupo;
	private MulticastSocket socket;
	private JTextArea t_history; 
	
	public ChatHost(JTextArea t_history){
		try{
			this.t_history = t_history;
			grupo = InetAddress.getByName("239.255.255.253");
			socket = new MulticastSocket(6789);
			socket.joinGroup(grupo);
		}
		catch(Exception e){e.printStackTrace();}
		
		
	}
	
	
	public void sendMessage(String message){
		try{
			byte[] bytes = message.getBytes();
			DatagramPacket mensajeSalida = new DatagramPacket(bytes, bytes.length, grupo, 6789);
			socket.send(mensajeSalida);
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void startListening(){
		try{
			byte[] bufer = new byte[1000];
			String linea;
			
			// Se queda a la espera de mensajes al grupo, hasta recibir "Adios"
			while(true){
				DatagramPacket mensajeEntrada = new DatagramPacket(bufer, bufer.length);
				socket.receive(mensajeEntrada);
				linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());

				t_history.append("\n" + linea);
				
			}
		}
		
		catch(Exception e){e.printStackTrace();}
	}
	
	
	public void closeChat(){
		try {
			socket.leaveGroup(grupo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
