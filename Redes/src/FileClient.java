import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileClient {

	private Socket client;
	private DataInputStream input;
	private PrintWriter output;
	private DataOutputStream output1;
	private BufferedReader command;

	public FileClient(String host) throws Exception {
		startConection(host);
		manageConection();
//		closeConection();
	}

	public static void main(String[] args) throws Exception {
		// FileClient client = new FileClient(args[0]);
		FileClient client = new FileClient("localhost");
	}

	private void startConection(String host) throws Exception {
		client = new Socket(host, 4444);
		input = new DataInputStream(client.getInputStream());
		output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
		command = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Client ONLINE");
	}
	
	
	private void manageConection() throws Exception{
		
//		while(true){
		
			String temp = "";
			
			System.out.print(">> ");
			temp = command.readLine();
			
			if(temp.contains("lock")){
				output.println(temp);
				System.out.print("\t> ");
				output.println(command.readLine());
				
				saveFile(input.readInt());
				
				String line = "";
				StringBuilder sb = new StringBuilder();
				System.out.print("\t> ");
				while(!(line = command.readLine()).equals("put")){
					System.out.print("\t> ");
					sb.append(line + "\n");
				}
				
				File file = new File("C://Users/theha/Desktop/Recived.txt");
				FileWriter writer = new FileWriter(file, true);
				
				writer.write(sb.toString());
				writer.close();
				
				
				
				output1 = new DataOutputStream(client.getOutputStream());
				getBackFile("C://Users/theha/Desktop/Recived.txt");
				
				System.out.print("\t> ");
				output.println(command.readLine());
					
			}
			else {
				output.println(temp);
				System.out.println();
				saveFile(input.readInt());
			}
			
//		}
		
		
	}
	
	private void getBackFile(String name) throws Exception{
		File file = new File(name);
		byte[] bytes = Files.readAllBytes(file.toPath());
		
//			System.out.println(Arrays.toString(bytes));
		
		output1.writeInt(bytes.length);
		output1.write(bytes);
		
		System.out.println("<<< File sent >>>");
	}
	
	private void saveFile(int length) throws Exception{
		if(length >= 0){
			byte[] bytes = new byte[length];
			input.readFully(bytes);
			
			Files.write(Paths.get("C://Users/theha/Desktop/Recived.txt"), bytes);
			
			System.out.println("<<< File recived >>>");
		}
		
		else
			System.out.println("<<< The recived data is empty >>>");
		
	}
	
	
	
	private void closeConection() throws Exception {
		output.close();
		input.close();
		command.close();
		client.close();
		System.out.println("Client OFFLINE");
	}

}
