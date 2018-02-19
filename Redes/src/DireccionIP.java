import java.net.InetAddress;
import java.net.UnknownHostException;

public class DireccionIP {


	public static void main(String[] args) throws UnknownHostException{
		if(args.length != 1){
			System.err.println("Usage: DireccionIP NombreHost");
			System.exit(1);
	    }
		InetAddress a = InetAddress.getByName(args[0]);
		System.out.println(a);
	  }

}
