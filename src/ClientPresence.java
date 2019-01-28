import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class ClientPresence extends Thread {


	private static  String USER_AGENT = "Mozilla/5.0";

	private String GET_URL = "http://localhost:8080/test";

	private ObjectInputStream inObject = null;
	private ObjectOutputStream outObject = null;
	
	private Controler c;

	/*public static void main(String[] args) throws IOException {

		ClientPresence.notifyConnexion();
		//ClientPresence.getConnectedUser();


	}*/
	
	public ClientPresence(Controler c)
	{
		this.c = c;
	}
	
	public void setServerIP(String IPAddress)
	{
		this.GET_URL= "http://"+IPAddress+":8080/test";
	}
	
	public void notifyConnexion ()
	{
		System.out.println("Test : " + c.mainUser.getAddress().toString().substring(1));
		String URL = GET_URL + "/PresenceServer?status=Connexion&user=" +c.mainUser.getPseudo() +"&IP=" +c.mainUser.getAddress().toString().substring(1);
		String resp = null;
		int port;
		try {
			resp = sendGET(URL);
			port = Integer.parseInt(resp);
			System.out.println(port);
			
			if(port == 0 )
			{
				WarningWindow.ShowWarn("Pseudo déjà utilisé !");
			}
			else
			{
				Socket MySocket = new Socket("localhost",port);
				System.out.println("Notfiy : "+c.mainUser.getAddress().toString());
	
				
				String u = null;
	
				try 
				{
					outObject = new ObjectOutputStream (MySocket.getOutputStream());	
					inObject = new ObjectInputStream (MySocket.getInputStream());		
				}
				catch (IOException e)
				{
					System.out.println("ERROR FUNCTION : outStream");
				}
				
				try 
				{
	
						while (u == null)
							u = (String) inObject.readObject();
						
				}
				catch ( IOException e )
				{
					System.out.println("ERROR FUNCTION : recupMessage - IOException");
				}
				catch (ClassNotFoundException e)
				{
					System.out.println("ERROR FUNCTION : recupMessage - ClassnotfounException");
				}
				
				// On reçoit une chaine de type Evan@/195.5.1.1&&Loic@/193.168.1.2&&
	
				String[] tabUserList = u.split("&&");
	
				for(int i=0; i<tabUserList.length;i++)
				{
					// Chaque élément de tabUserList est de type Evan@/195.5.1.1
					String[] tabUser = tabUserList[i].split("@");
				
					// Un tabUser par User avec premier élément : pseudo - deuxième élément : IP
					User Test = new User(tabUser[0],InetAddress.getByName(tabUser[1].substring(1)), true);
					System.out.println(tabUser[0]+ "@" + tabUser[1]);
					if(!(c.userList.pseudoIsIn(Test)) && !(c.mainUser.getPseudo().equals(Test.getPseudo())))
					{	
						c.userList.addToConnectedUser(Test);
					}
				}
				ListWindows.PrintConnectedUser(c);
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void notifyDeconnexion()
	{
		
		String URL = GET_URL + "/PresenceServer?status=Deconnexion&user=" +c.mainUser.getPseudo();
		String resp = null;
		
		try {
			resp = sendGET(URL);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void getConnectedUser ()
	{
		String URL = GET_URL + "/PresenceServer?status=Check";
		String resp = null;
		try {
			resp = sendGET(URL);
			System.out.println(resp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	private static String sendGET(String URL) throws IOException {
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		String resp = null;
		//System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			resp = response.toString();
			//System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
		return resp;
	}
	
	
}
