package com.sdzee.servlets;
import java.io.Serializable;
import java.net.*;

public class UserPresence implements Serializable {
	private String userName;
	private boolean onlineState;
	private InetAddress addr;
	
	public UserPresence(String userName, InetAddress addr)
	{
		this.userName = userName;
		this.addr = addr;
		this.onlineState = true;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public String toString()
	{
		return this.userName + "@"+ this.addr.toString();
	}
}
