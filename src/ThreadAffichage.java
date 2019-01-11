import java.io.*;
import java.net.*;

public class ThreadAffichage extends Thread{
	
	private Socket mySocket;
    private volatile boolean running = true;
    public NetworkInterface nw;
    


	public ThreadAffichage(Socket mySocket, NetworkInterface nw)
	{
		this.mySocket = mySocket;
		this.nw = nw;
		start();
		
	}
    public void terminate() {
        running = false;
    }

	
	public void run()
	{
		
		try {
			
			System.out.println("THREAD AFFICHAGE LANCE");

			while(running)
			{			

					Message messtest = Message.recupMessage(this.nw.in2);
					
					if(nw.userDest==null && messtest.getIdentity())	//Si pas de userDest défini auparavant (cas du serveur) ET message avec le flag identity
						nw.userDest = messtest.getUserDest(); // On défini le userDest du serveur 
					
					System.out.println(messtest);
					if(!messtest.getDeco() && !messtest.getIdentity())
						Database.write(messtest,false);
					
					if(messtest.getDeco())
					{
						running = false;
						this.nw.CW.writeDecoMessage(messtest);
						
						try
						{
						    Thread.sleep(2000);
						}
						catch(InterruptedException ex)
						{
						    Thread.currentThread().interrupt();
						}
						
						this.nw.CW.closeWindow();
						
						
					}
					else
					{
						this.nw.CW.writeReceivedMessage(messtest);
					}
			

			}
		}
		catch (Exception e){
			
			
			
			System.out.println("Erreur Thread Affichage");
		}
		
	}
}
