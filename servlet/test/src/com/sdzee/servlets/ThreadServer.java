package com.sdzee.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServer extends Thread {

	int port;
	ObjectInputStream inObject = null;
	ObjectOutputStream outObject = null;

	public ThreadServer(int port)
	{
		this.port = port;
		//this.run();
	}
	
	public void init()
	{
		try 
		{
			ServerSocket MyServer = new ServerSocket(this.port);
			Socket MySocket = MyServer.accept();
			MyServer.close();
			outObject = new ObjectOutputStream (MySocket.getOutputStream());	
			inObject = new ObjectInputStream (MySocket.getInputStream());	
			
			//outObject.writeObject("Test");
		}
		catch (IOException e)
		{
			System.out.println("ERROR FUNCTION : outStream");
		}
	}
	
	public ObjectOutputStream getOutObject()
	{
		return this.outObject;
	}
	
	public void run()
	{
		this.init();
		
		
		
	}
}
