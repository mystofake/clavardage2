import java.io.*;

public class NetworkEvent implements Serializable{

	private User u;
	private String event;
	
	public NetworkEvent(User u, String event)
	{
		this.u = u;
		this.event = event;
		
	}
	
	public boolean isConnexion()
	{
		if(this.event.equals("Connexion"))
			return true;
		
		return false;
	}
	
	public boolean isDeconnexion()
	{
		if(this.event.equals("Deconnexion"))
			return true;
		
		return false;
	}
	
	public boolean isShareUser()
	{
		if(this.event.equals("ShareUser"))
			return true;
		
		return false;
	}
	
	public boolean isChangeUser()
	{
		if(this.event.equals("ChangeUsername"))
			return true;
		
		return false;
	}
	
	public String getEvent()
	{
		return this.event;
	}
	
	public User getUser()
	{
		return this.u;
	}
	
}
