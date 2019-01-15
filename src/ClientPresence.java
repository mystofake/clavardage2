import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientPresence {


	private static  String USER_AGENT = "Mozilla/5.0";

	private static  String GET_URL = "http://localhost:8080/test";


	public static void main(String[] args) throws IOException {

		ClientPresence.notifyConnexion();
		//ClientPresence.getConnectedUser();


	}
	
	public static void notifyConnexion ()
	{
		String URL = GET_URL + "/toto?status=Connexion&user=Evan&IP=10.5.2.2";
		String resp = null;
		try {
			resp = sendGET(URL);
			System.out.println(resp);
			String[] tabUser = resp.split("&&");
			System.out.println(tabUser[2]);
			
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static void getConnectedUser ()
	{
		String URL = GET_URL + "/toto?status=Check";
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
