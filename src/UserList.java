import java.io.*;
import java.net.*;
import java.util.ArrayList;

//Cette classe permet de stocker les Users qui sont sur le r�seau dans une UserList.
//Elle est serializable car il est possible d'envoyer la userList selon l'impl�mentation de la d�couverte r�seau choisie.

public class UserList implements Serializable{

	public  ArrayList<User> connectedUser;
	
	
	public UserList()
	{
		this.connectedUser = new ArrayList<User>(); 
	}
	
	public void addToConnectedUser(User newUser)
	{
		this.connectedUser.add(newUser);
	}
	
	public void removeToConnectedUser(User u)
	{
		int i =0;
		
		for(User u1 : this.connectedUser)
		{
			if(u.getPseudo().equals(u1.getPseudo()))
			{
				this.connectedUser.remove(i);
				break;
			}
			
			i++;
		}
		
	}
	
	public void removeToConnectedUserByAddress(User u)
	{
		int i =0;
		
		for(User u1 : this.connectedUser)
		{
			if(u.getAddress().equals(u1.getAddress()))
			{
				this.connectedUser.remove(i);
    			ListWindows.RemoveConnectedUser(u1);
			}
			i++;
		}
	}
	
	public void afficheConnectedUser()
	{
		System.out.println("Liste des utilisateurs : ");
		for (User user : connectedUser) {
			System.out.println(user.getPseudo());
		}
	}
	
	public boolean pseudoIsIn(User user1)
	{
		for (User user : connectedUser) {
			if (user.getPseudo().equals(user1.getPseudo()))
				return true;
		}
		return false;
	}
	
	public void recupUserList (ObjectInputStream in )
	  {	  
			try
			{

			connectedUser = (ArrayList<User>) in.readObject();
			//add to discussion
			}
		catch ( Exception e )
		{
			System.out.println("ERROR FUNCTION : recupUserList");
		}
	  }
	 
	 public static void SendUserList (ObjectOutputStream out, ArrayList<User> connectedUser)	
	 {
		  	try {
		  		out.writeObject(connectedUser);
		  	}
			
		  		catch(IOException e)
		  		{
		  			System.out.println("ERROR FUNCTION : SendUserList");
		  		}
	 }
	 
	 public String toString()
	 {
		 
		 String s = "Empty List";
			for (User user : this.connectedUser) {
				s = "UserList = " + user.getPseudo();
			}
			
		return s;
		 
	 }
}
