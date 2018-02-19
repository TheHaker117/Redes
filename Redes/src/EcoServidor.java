import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.util.Arrays;

public class EcoServidor{
	public static final int PORT = 4444;
	
	
	public static void main(String[] args) throws IOException {
		// Establece el puerto en el que escucha peticiones
		ServerSocket socketServidor = null;
		
		try{
			socketServidor = new ServerSocket(PORT);
		}
		catch(IOException e){
			System.out.println("No puede escuchar en el puerto: " + PORT);
			System.exit(-1);
		}
		
		Socket socketCliente = null;
		BufferedReader entrada = null;
//		PrintWriter salida = null;
		DataOutputStream output = null;
		
		System.out.println("Escuchando: " + socketServidor);
		
		try{
			// Se bloquea hasta que recibe alguna petición de un cliente
			// abriendo un socket para el cliente
			socketCliente = socketServidor.accept();
			System.out.println("Connexión acceptada: "+ socketCliente);
			// Establece canal de entrada
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			// Establece canal de salida
//			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
			output = new DataOutputStream(socketCliente.getOutputStream());
			
			String temp = entrada.readLine();
			
			System.out.println(temp);
			File file = new File(temp);
			byte[] bytes = Files.readAllBytes(file.toPath());
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			FileChannel fchan = raf.getChannel();
			FileLock flock = fchan.lock();
			
			
			
			System.out.println(Arrays.toString(bytes));
			
			output.writeInt(bytes.length);
			output.write(bytes);
			
			flock.release();
			fchan.close();
			
			
			
			
			
			/*FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			
			
			while((temp = buffer.readLine()) != null)
				salida.println(temp);
			
			buffer.close();
			reader.close();
			*/
			
			/*
			// Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios"
			while(true){
				String str = entrada.readLine();
				System.out.println("Cliente: " + str);
				salida.println(str);
				if(str.equals("Adios"))
					break;
			}*/
			
		}
		catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}
		
		output.close();
//		salida.close();
		entrada.close();
		socketCliente.close();
		socketServidor.close();
	}
}
