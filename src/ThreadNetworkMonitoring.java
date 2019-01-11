import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Ce thread est le thread qui recueille sur le port 1235 tous les broadcasts r�alis�s par les users qui viennent de se connecter et il renvoie
// la classe User du mainUser.

public class ThreadNetworkMonitoring extends Thread {

	public Controler c;
	public String pseudo;
	
	
	public ThreadNetworkMonitoring(Controler c)
	{
			this.c = c;
			start();
		
	}
	
	public void run() 
	{
	
	try {
		
		//ServerSocket NetworkWatcher = new ServerSocket(1235); // Serveur d'inizialition 
		System.out.println("NetworkWatcher : LAUNCHED ON PORT 1235");
        String multiCastAddress = "230.0.0.0";
        String multiCastAddress1 = "231.0.0.0";
        final int multiCastPort = 1235;
        final int bufferSize = 1024; //Maximum size of transfer object
		boolean first_deco = true;
        
        
        DatagramSocket dSock1 = new DatagramSocket(multiCastPort);
        
		while(true)
			{
			
	        	/*******Recup UserConnected************/
				byte[] buffer = new byte[bufferSize];

				
				
				
				
				//DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
				
				System.out.println("NetworkWatcher : Waiting for datagram");
				dSock1.receive(new DatagramPacket(buffer, bufferSize));
				System.out.println("NetworkWatcher : Datagram received");
				 
				NetworkEvent NwEv = null;
				
				try {
					
				    Object readObject = NetworkFunctions.convertFromBytes(buffer);
				    
				    NwEv = (NetworkEvent) readObject;
				    

				    //if(ReceivedUser.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress())==false)
				    	//{
				    		//if (ReceivedUser.getPseudo().equals(c.mainUser.getPseudo()))
				    		//{
				    		//ListWindows.PrintMainUser(new User("ERROR"));
				    		//c.userList.addToConnectedUser(ReceivedUser);
				    		//}
				    		//else
				    		//{
				    
				    System.out.println(NwEv.getEvent());
				    

				    if(NwEv.isConnexion() && !(NwEv.getUser().getAddress().equals(c.mainUser.getAddress())))
				    	{
				    
				    		
					    	System.out.println(NwEv.getUser().getPseudo() + " CONNECTED @ : " + NwEv.getUser().getAddress());
					    	if(!c.userList.pseudoIsIn(NwEv.getUser()))
					    		c.userList.addToConnectedUser(NwEv.getUser());
					    	
					    	
					    	if(c.connected)
					    	{
					    		NetworkEvent NwEv2 = new NetworkEvent(c.mainUser,"ShareUser");
								byte data[] = NetworkFunctions.convertToBytes(NwEv2);
								DatagramPacket toSend =new DatagramPacket(data,data.length,NwEv.getUser().getAddress(),multiCastPort);
								dSock1.send(toSend);
					    	}
					    	
							

				    	}
				    
				    if(NwEv.isShareUser())
					    {
				    		if(!NwEv.getUser().getPseudo().equals(c.mainUser.getPseudo()))
				    		{
					    		System.out.println("Receive shared user : " + NwEv.getUser().getPseudo() + " CONNECTED");
						    	if(!c.userList.pseudoIsIn(NwEv.getUser()))
						    		c.userList.addToConnectedUser(NwEv.getUser());
				    	
				    		}
				    		
				    		if(NwEv.getUser().getPseudo().equals(c.mainUser.getPseudo()) && !NwEv.getUser().getAddress().equals(c.mainUser.getAddress()))
		    				{
				    			System.out.println("Username already used - Change username");
				    			
				    			c.deconnect();
		    				}
				    		
					    }
				    
				    if(NwEv.isDeconnexion() && !NwEv.getUser().getAddress().equals(c.mainUser.getAddress()))
			    		{
				    		System.out.println("Receive deconnect user : " + NwEv.getUser().getPseudo() + " DECONNECTED");
				    		c.userList.removeToConnectedUser(NwEv.getUser());
				    		ListWindows.RemoveConnectedUser(NwEv.getUser());
			    		}
				    
				    
				    // Si connexion + meme pseudo + discrimination sur adresse ip pour pas se rep a soi meme

				    
				    /*if(NwEv.isChangeUser() && !NwEv.getUser().getPseudo().equals(c.mainUser.getPseudo()))
			    		{
				    		System.out.println("Receive Changed user : " + NwEv.getUser().getPseudo() + " CHANGED USERNAME");
				    		c.userList.removeToConnectedUserByAddress(NwEv.getUser());
				    		c.userList.addToConnectedUser(NwEv.getUser());
			    		}*/

				    		//}
						//}
				   
					} 
				catch (Exception e) {
				    System.out.println("NetworkWatcher : Erreur ! ");
				}
				
				ListWindows.PrintConnectedUser(c);
	
			}
		        	
		}
		
		
	catch (IOException e) {
		
		System.out.println("ERROR FUNCTION : NetworkWatcher");
		}
	
	}
}
