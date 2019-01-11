import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


//La classe Message permet de recueillir les messages � envoyer ainsi que toutes les m�tadonn�es qui les accompagent.
//Elle est serializable puisqu'elle sera envoy�e � chaque nouveau message envoy�.

public class Message implements Serializable {

  private String pdu;
  private User userDest;
  private User userOri;
  private String dateEnvoi;
  private boolean deco;
  private boolean identity;

  public Message() {}
  
  
  public Message(String pdu, User userDest, User userOri)
  {
	  this.pdu=pdu;
	  this.userDest = userDest;
	  this.userOri = userOri;
	  this.dateEnvoi = recupDate();
	  this.deco = false;
  }
  
  
  public void SendMessage (ObjectOutputStream out)	
 {
	  	
	  	this.dateEnvoi=recupDate();
	  	try {
	  		out.writeObject(this);
	  	}
		
  		catch(IOException e)
  		{
  			System.out.println("ERROR FUNCTION : SendMessage");
  		}
	  	
	  	Database.write(this,true);
 }

  public void setDeco()
  {
	  this.deco = true;
  }
  
  public void setIdentity()
  {
	  this.identity = true;
  }
	

  public String recupDate()
  {
	  	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String reportDate = dateFormat.format(date);
		return(reportDate);
  }
  
  public static Message recupMessage(ObjectInputStream in)
  {	  
	  Message mess = null;
	  try {

		while (mess == null)
			mess = (Message) in.readObject();
		//add to discussion
		}
	catch ( IOException e )
	{
		System.out.println("ERROR FUNCTION : recupMessage - IOException");
	}
	catch (ClassNotFoundException e)
	  {
		System.out.println("ERROR FUNCTION : recupMessage - ClassnotfounException");
	  }
  	return(mess);
  }
  
  public boolean getDeco()
  {
	  return this.deco;
  }
  
  public boolean getIdentity()
  {
	  return this.identity;
  }
  
  public User getUserSrc()
  {
	  return this.userOri;
  }
  
  public User getUserDest()
  {
	  return this.userDest;
  }  
  
  public String getPDU()
  {
	  return this.pdu;
  }
  
  public String toString()
  {
	  return("|" + this.dateEnvoi + "| " + this.userOri.getPseudo()+ " : " + this.pdu);
  }
}
