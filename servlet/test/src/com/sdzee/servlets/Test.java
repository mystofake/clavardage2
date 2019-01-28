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
		String StringUserList="";
		boolean alreadyUsed = false;

		if(request.getParameter("status").equals("Connexion"))
		{
			port++;
			
			for(UserPresence parsedUser : ConnectedUser)
			{
				if(parsedUser.getUserName().equals(request.getParameter("user")))
					alreadyUsed = true;
			}
			
			if(alreadyUsed)
			{
				System.out.println("Pseudo déjà utilisé !");
				
				//On envoie 0 pour dire que le pseudo est déjà utilisé
				out.println(0);
				out.close();
				
			}
			else
			{
				// Ajout de l'utilisateur qui vient de se connecter
				UserPresence u = new UserPresence(request.getParameter("user"),InetAddress.getByName(request.getParameter("IP")));
				
				
				// Envoi du port sur lequel l'utilisateur doit se connecter
				out.println(port);
				out.close();

				ConnectedUser.add(u);

				u.setThreadServer(new ThreadServer(port));
				u.getThreadServer().init();
				
				
				// Envoi de la UserList sous forme de String
				for(UserPresence up : ConnectedUser)
				{
					StringUserList = StringUserList.concat(up + "&&");

				}

				
				u.getThreadServer().getOutObject().writeObject(StringUserList);
				u.getThreadServer().getOutObject().flush();
			}



			
		}
		else if(request.getParameter("status").equals("Check"))
		{

			for(UserPresence up : ConnectedUser)
			{
				StringUserList = StringUserList.concat(up + "&&");

			}
			
			out.println(StringUserList);

		
		}
		
		else if(request.getParameter("status").equals("Deconnect"))
		{

			for(int i = 0; i<ConnectedUser.size();i++)
			{
				if(ConnectedUser.get(i).getUserName().equals(request.getParameter("user")))
					ConnectedUser.remove(i);
			}
		
		}
		else
		{

			out.println("<p>Requete inconnue de : " + request.getParameter("user")+" </p>");

		}

	}

}
