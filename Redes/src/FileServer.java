import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileServer {
	
	private ServerSocket server;
	private Socket client;
	private BufferedReader input;
	private DataInputStream input1;
	private DataOutputStream output;
	private String locked = "";
	
	
	public FileServer() throws Exception{
		startConection();
		manageConection();
//		closeConection();
	}
	
	public static void main(String[] args) throws Exception{
		FileServer server = new FileServer();
	}
	
	/**
	 * Initialize the connection. Since server (ServerSocket), client (Socket), 
	 * input (BufferedReader), input1 (DataInputStream) and output (DataOutputStream).
	 * <p>
	 * Prints in console that the server is online and that the connection is on.
	 * 
	 *
	 * @throws Exception
	 */
	
	private void startConection() throws Exception{
		server = new ServerSocket(4444);
		System.out.println("Server ONLINE");
		client = server.accept();
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		input1 = new DataInputStream(client.getInputStream());
		output = new DataOutputStream(client.getOutputStream());
		System.out.println("Conection ON: "+ client);
		
	}
	
	/**
	 * Check input for the next cases:
	 * 		- get		Will return the text file.
	 * 		- lock		Will lock the access to the specified file then 
	 * 					will return it to modify in the client side.
	 * 					Will wait the for the command "unlock" to unlock (obviously) the file.
	 * 		- bye		Will close the connection.
	 * 		
	 * 					 
	 * 
	 * @throws Exception
	 */
	
	
	private void manageConection() throws Exception{
		String temp = input.readLine();
//		System.out.println(temp);
//		System.out.println(temp.contains("get"));
		
		if(temp.contains("get")){
			
			temp = temp.replace("get ", "");
			
			if(!isLocked(temp))
				getBackFile(temp);
			else{
				System.out.println("File locked");
				closeConection();
			}
		}
		
		else if(temp.contains("lock")){
			locked = temp;
			temp = temp.replace("lock ", "");
			
			if(input.readLine().equals("get"))
				getBackFile(temp);
			else
				System.out.println("Invalid action");
			
			saveFile(input1.readInt());
			
			if(input.readLine().equals("unlock"))
				locked = "";
		}
			
		else if(temp.contains("bye"))
			closeConection();
		
		
	}

	/**
	 * Save the text file in the specified path
	 * 
	 * 
	 * @param length	File size
	 * @throws Exception	See Java.io exceptions for Files interface
	 */
	
	private void saveFile(int length) throws Exception{
		if(length >= 0){
			byte[] bytes = new byte[length];
			input1.readFully(bytes);
			
			Files.write(Paths.get("C://Users/theha/Desktop/Text.txt"), bytes);
			
			System.out.println("<<< File recived >>>");
		}
		
		else
			System.out.println("<<< The recived data is empty >>>");
		
	}
	
	/**
	 * Sends to the client the bytes of the text file.
	 * 
	 * 
	 * @param name	File path
	 * @throws Exception
	 */
	
	
	private void getBackFile(String name) throws Exception{
		File file = new File(name);
		byte[] bytes = Files.readAllBytes(file.toPath());
		
//			System.out.println(Arrays.toString(bytes));
		
		output.writeInt(bytes.length);
		output.write(bytes);
		
		System.out.println("<<< File sent >>>");
	}
	
	/**
	 * Checks if the specified file is locked.
	 * 
	 * 
	 * @param file	File path
	 * @return	true	If the file is unlocked
	 * @return	false	If the file is locked	
	 */
	
	private boolean isLocked(String file){
		return file.equals(locked) ? true:false;
	}
	
	/**
	 * 
	 * Close all connections in the server side.
	 * 
	 * 
	 * @throws Exception
	 */
	
	private void closeConection() throws Exception{		
		output.close();
		input.close();
		client.close();
		server.close();
		System.out.println("Server OFFLINE");
	}
}
