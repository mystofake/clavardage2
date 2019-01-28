import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Controler {
	
	public boolean connected = true;
	public boolean ready = true;
	public User mainUser;
	public UserList userList;
	public boolean presence_server;
	public String addPresenceServer;
	public ClientPresence cliPresence;

	

	
	public Controler(){
		
		userList = new UserList();
	
	}
	
		
	/* Send Connected to the others users */
	public void init() 
	{
	    final int BroadCastPort = 1236;
	    final int multiCastPort = 1235;
	    
	try {
		
		DatagramSocket dSock2 = new DatagramSocket(BroadCastPort);
		
		//mainUser.SetAddress(Adressage.getIP()); Already done before
		System.out.println("Set IP : " + mainUser.getAddress());
		NetworkEvent NwEv = new NetworkEvent(mainUser,"Connexion");
		
		System.out.println(mainUser.getAddress());
		byte data[] = NetworkFunctions.convertToBytes(NwEv);
		DatagramPacket toSend =new DatagramPacket(data,data.length,Adressage.getBroad(mainUser.getAddress()),multiCastPort);
		dSock2.send(toSend);
		
		dSock2.close();	
	    
		
	}
	catch (IOException e) {
		System.out.println("ERROR FUNCTION : ControllerInit");}
	}
	
	/* Send Disconnected to the others users */	
	public void deconnect()
	{
		try			{
			DatagramSocket dSock1 = new DatagramSocket(1237);
			NetworkEvent NwEv2 = new NetworkEvent(this.mainUser,"Deconnexion");
			byte data[] = NetworkFunctions.convertToBytes(NwEv2);
			DatagramPacket toSend = new DatagramPacket(data,data.length,Adressage.getBroad(this.mainUser.getAddress()),1235);
			dSock1.send(toSend);
			this.connected = false;
			dSock1.close();
			if(this.presence_server)
			{
				cliPresence.notifyDeconnexion();
			}
			}
		catch (Exception e)			{
			System.out.println("Erreur lors de la deconnexion");
			}
	}
	
	/* Disconnection of the current user - Create new User - Send connected */
	public void changeUsername(String pseudo)
	{
		try		{
			this.deconnect();
			if(this.presence_server)
			{
				cliPresence.notifyDeconnexion();
			}
			this.mainUser = new User(pseudo);
			this.mainUser.SetAddress(Adressage.getIP());
			this.connected = true;


			this.init();
			if(this.presence_server)
			{
				this.initCliPresence();
			}
		}
		catch (Exception e)		{
			System.out.println("Erreur lors du changement de pseudo");
		}
	}
	
	public void initCliPresence()
	{
		
		cliPresence = new ClientPresence(this);
		try {
			cliPresence.notifyConnexion();
			cliPresence.start();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			WarningWindow.ShowWarn("Can't contact presence server");
		}
	}
	
}
