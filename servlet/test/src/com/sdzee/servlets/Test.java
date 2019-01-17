package com.sdzee.servlets;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.*;

public class Test extends HttpServlet {
	
	int port = 1500;
	public ArrayList <UserPresence> ConnectedUser = new ArrayList<UserPresence>();
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter out = response.getWriter();

		if(request.getParameter("status").equals("Connexion"))
		{
			port++;
			if(ConnectedUser.contains(request.getParameter("user")))
			{
				System.out.println("Deja Connecté !");
			}
			else
			{
				ConnectedUser.add(new UserPresence(request.getParameter("user"),InetAddress.getByName(request.getParameter("IP"))));
			}

			out.println(port);
			out.close();
			
			ThreadServer ts = new ThreadServer(port);
			ts.init();
			

			

			
			
		}
		else if(request.getParameter("status").equals("Check"))
		{

			for(UserPresence u1 : ConnectedUser)
			{
				out.println(u1);
			}
		
		}
		else
		{

			out.println("<p>Requete inconnue de : " + request.getParameter("user")+" </p>");

		}

	}

}
