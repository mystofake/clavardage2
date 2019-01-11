import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NetworkInterface extends Thread{
	
	public ChatWindow CW;
	public Controler c;
	public  ObjectOutputStream out2;
	public ObjectInputStream in2;
	public User userDest;
	
	public NetworkInterface(Controler c, User userDest)
	{
		this.c =c; 
		this.userDest = userDest;
	}
	
	

}
