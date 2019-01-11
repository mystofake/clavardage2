import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends NetworkInterface {

	//public Controler c;
	
public Client(User userDest, Controler c)
{
	super(c, userDest);
	//this.c = c;
	this.BeginChat(userDest.getAddress());
	this.CW = new ChatWindow(this);
	this.CW.createAndShowGUI();
}



public void BeginChat(InetAddress AddServer) {
	try {

	// Réception du port sur lequel on va communiquer
	Socket MySocket = new Socket(AddServer,1234);
	
	/*Added part : Envoie de l'identité du client*/
	ObjectOutputStream out_identity = NetworkFunctions.outObj(MySocket);
	ObjectInputStream in_identity = NetworkFunctions.inObj(MySocket);
	
	int port = 0;
	
	try {
		out_identity.writeObject(this.c.mainUser);
	}
	
	catch(IOException e)
	{
		System.out.println("ERROR Send identity");
	}
  	
	System.out.println("Identity sent");
	
	
	try {
	while (port == 0)
		port = (int) in_identity.readObject();
	}

	catch (ClassNotFoundException e)
	  {
		System.out.println(e.getMessage());
	  }

	System.out.println(port);
	
	

	MySocket.close();

	// Creation d'un chat client sur le port recu
	// Temporisation 
	try
	{
	    Thread.sleep(1000);
	}
	catch(InterruptedException ex)
	{
	    Thread.currentThread().interrupt();
	}
	Socket MySocket2 = new Socket(AddServer,(int)port);
	System.out.println("ChatClient : CONNECTED ON PORT "+ port);
	
	this.out2 = NetworkFunctions.outObj(MySocket2);
	this.in2 = NetworkFunctions.inObj(MySocket2);
	

	
	ThreadAffichage t_aff = new ThreadAffichage(MySocket2, this);
	
	
}
	catch (IOException e)
	{
		
			System.out.println("BeginChat : ERREUR !");
		
		
	}
	
}	
	

}