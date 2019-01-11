
import java.io.*;
import java.net.*;


//La classe User récapitule les données essentielles sur un User du système de Clavardage.
//Elle est serializable puisqu'elle sera broadcastée à chaque nouvelle connexion.

public class User implements Serializable{

	private String pseudo;
	private boolean onlineState;
	private InetAddress AddUser;
	
	public User(String pseudo, InetAddress AddUser) {
		this.pseudo = pseudo;  
		this.onlineState = true;
		this.AddUser = AddUser;
	}
	
	public User(String pseudo) {
		this.pseudo = pseudo;  
		this.onlineState = true;
		try {
		this.AddUser = InetAddress.getLocalHost();
		}
		catch (IOException e)
		{
			System.out.println("Erreur lors de l'obtention de l'adresse User");
		}
	}
	public String getPseudo()
	{
		return(this.pseudo);
	}
	
	public InetAddress getAddress()
	{
		return(this.AddUser);
	}
	
	public void SetAddress(InetAddress add)
	{
		this.AddUser = add;
	}
	
	 public static User recupUser(ObjectInputStream in)
	  {	  
		  User user = null;
		  try {

			user = (User) in.readObject();
			//add to discussion
			}
		catch ( Exception e )
		{
			System.out.println("ERROR FUNCTION : recupUser");
		}
	  	return(user);
	  }
	 
	 public void SendUser (ObjectOutputStream out)	
	 {
		  	try {
		  		out.writeObject(this);
		  	}
			
		  		catch(IOException e)
		  		{
		  			System.out.println("ERROR FUNCTION : SendUser");
		  		}
	 }
	 
	 public String toString()
	 {
		 
		 return this.getPseudo();
	 }
		
}