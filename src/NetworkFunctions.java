import java.io.*;
import java.net.Socket;


public class NetworkFunctions {

	
	
	
	
	public static ObjectOutputStream outObj(Socket MySocket)
	{
		ObjectOutputStream out = null;
		try {
		out = new ObjectOutputStream(MySocket.getOutputStream());
		
		}
		catch (IOException e)
		{
			System.out.println("ERROR FUNCTION : outObj");
		}
		return(out);
	}
	
	public static ObjectInputStream inObj(Socket MySocket)
	{
		ObjectInputStream in = null;
		try {
		in = new ObjectInputStream(MySocket.getInputStream());
		
		}
		catch (IOException e)
		{
			System.out.println("ERROR FUNCTION : inObj");
		}
		return(in);
	}
	
	
	public static BufferedReader inStream(Socket MySocket)
	{
		BufferedReader in = null;
		try {
		in = new BufferedReader (new InputStreamReader(MySocket.getInputStream()));
		
		}
		catch (IOException e)
		{
			System.out.println("ERROR FUNCTION : inStream");
		}
		return(in);
	}
	
	public static PrintWriter outStream(Socket MySocket)
	{
		PrintWriter out = null;
		try {
		out = new PrintWriter (MySocket.getOutputStream(),true);		
		}
		catch (IOException e)
		{
			System.out.println("ERROR FUNCTION : outStream");
		}
		return(out);
	}
	
	public static byte[] convertToBytes(Object object) throws IOException { 
	    try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	     ObjectOutput out = new ObjectOutputStream(bos)) { 
	     out.writeObject(object); 
	     return bos.toByteArray(); 
	    } 
	} 
	
	public static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException { 
	    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); 
	     ObjectInput in = new ObjectInputStream(bis)) { 
	     return in.readObject(); 
	    } 
	} 

	
}
