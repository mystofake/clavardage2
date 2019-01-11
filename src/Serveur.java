import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.Scanner;



public class Serveur extends NetworkInterface {

	int port;
		
	public Serveur(int port, Controler c, User userDest)
	{
		super(c, userDest);
		this.port = port;
		this.start();
		this.CW = new ChatWindow(this);
		this.CW.createAndShowGUI();




	}
	public void run()
	{
		this.initServ();
	}
	public void initServ()
	{
		
		try {
		ServerSocket MyServer = new ServerSocket(this.port);
		System.out.println("ChatServer : LAUNCHED ON PORT " + this.port);
		Socket MySocket = MyServer.accept();
		MyServer.close();
		System.out.println("ChatServer : CONNECTED");
		
		
		this.out2 = NetworkFunctions.outObj(MySocket);
		this.in2 = NetworkFunctions.inObj(MySocket);
		
		
		ThreadAffichage t_aff = new ThreadAffichage(MySocket, this);


		
		}
		catch (IOException e)
		{
		}
		
		System.out.println("ChatServer : CONNECTED");
	}
	

}


