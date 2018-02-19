
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EcoCliente {
	
	public static void main(String[] args)  throws IOException{
		Socket socketCliente = null;
//		BufferedReader entrada = null;
		DataInputStream input = null;
		PrintWriter salida = null;
		
		// Creamos un socket en el lado cliente, enlazado con un
		// servidor que está en la misma máquina que el cliente
		// y que escucha en el puerto 4444
		
		try{
			socketCliente = new Socket("localhost", 4444);
//			socketCliente = new Socket(args[0], 4444);
			
			// Obtenemos el canal de entrada
			input = new DataInputStream(socketCliente.getInputStream());
//			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			// Obtenemos el canal de salida
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),true);
		}
		catch(IOException e){
			System.err.println("No puede establer canales de E/S para la conexión");
			System.exit(-1);
		}
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String linea;
		
		// El programa cliente no analiza los mensajes enviados por el
		// usario, simplemente los reenvía al servidor hasta que este
		// se despide con "Adios"
		
		try{
			// Leo la entrada del usuario
//			linea = stdIn.readLine();
			// La envia al servidor
			salida.println("C:/Users/theha/Desktop/Text.txt");
			// Envía a la salida estándar la respuesta del servidor
//			linea = entrada.readLine();
//			System.out.println("Respuesta servidor: " + linea);
			
			int length = input.readInt();
			if(length >= 0){
				byte[] bytes = new byte[length];
				input.readFully(bytes);
				
				StringBuilder sb = new StringBuilder();
				sb.append(stdIn.readLine());
				
				Files.write(Paths.get("C://Users/theha/Desktop/Recived.txt"), bytes);
				File file = new File("C://Users/theha/Desktop/Recived.txt");
				FileWriter writer = new FileWriter(file, true);
				
				writer.write(sb.toString());
				writer.close();
				
				
				
			}
			
			
			
			/*while(true){
				// Leo la entrada del usuario
				linea = stdIn.readLine();
				// La envia al servidor
				salida.println(linea);
				// Envía a la salida estándar la respuesta del servidor
				linea = entrada.readLine();
				System.out.println("Respuesta servidor: " + linea);
				// Si es "Adios" es que finaliza la comunicación
				if(linea.equals("Adios"))
					break;
			}*/
		}
		catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}
		
		// Libera recursos
		salida.close();
		input.close();
//		entrada.close();
		stdIn.close();
		socketCliente.close();
	}
}