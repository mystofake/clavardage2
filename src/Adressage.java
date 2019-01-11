import java.net.*;
import java.util.*;
import java.math.BigInteger;
import java.net.NetworkInterface;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

// La classe Adressage permet de sélectionner la bonne interface réseau pour la communication ainsi que d'obtenir l'adresse de broadcast du réseau local.

public class Adressage {
	
	
		public static InetAddress getIP()
        {
			InetAddress addrnull = null;
			try
			{
				Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
	
				for (; n.hasMoreElements();)
	            {
	                    NetworkInterface e = n.nextElement();
	                    Enumeration<InetAddress> a = e.getInetAddresses();
	                    for (; a.hasMoreElements();)
	                    {
								
	                            InetAddress addr = a.nextElement();
	                            
	                            if(addr.toString().substring(1, 8).equals("192.168") || addr.toString().substring(1, 5).equals("10.1"))
	                            {	
	                            	return addr;
	                            }

	                    }
	            }
			}
			catch (SocketException e)
			{
				System.out.println("Erreur getIP()");
			}
			System.out.println("No IP start with 192.168 or 10.1");
        	return addrnull;

    }
      
		public static InetAddress getBroad(InetAddress add) throws SocketException {
           
    	   InetAddress adrbroadcast=null;
    	   String adresse=null;
    	   if(add.toString().substring(1, 8).equals("192.168"))
    		   {
    		   		adresse = add.toString().substring(1).concat("/24");
    		   }
    	   if(add.toString().substring(1, 5).equals("10.1"))
		   		{
		   			adresse = add.toString().substring(1).concat("/16");
		   		}
    	   System.out.println(adresse);
    	   //String adresse = "192.168.1.15"+"/24";
    	   String[] parties = adresse.split("/");
           String ip = parties[0];
           int prefixe;
           if (parties.length < 2) {
               prefixe = 0;
           } else {
               prefixe = Integer.parseInt(parties[1]);
           }
           //System.out.println("Addresse =\t" + ip+"\nPrefixe =\t" + prefixe);
          
           //convertir le masque entier en un tableau de 32bits
           int masque = 0xffffffff << (32 - prefixe);
           int valeur = masque;
           byte[] bytes_masque = new byte[]{
                   (byte)(valeur >>> 24), (byte)(valeur >> 16 & 0xff), (byte)(valeur >> 8 & 0xff), (byte)(valeur & 0xff) };

           try {
                 //masque
                 InetAddress netAddr = InetAddress.getByAddress(bytes_masque);
                 //System.out.println("Masque =\t" + netAddr.getHostAddress());
                        
            /*************************
             * Adresse rï¿½seau
             */
            //Convertir l'adresse IP en long
            long ipl = ipToLong(ip);
                
            //Convertir l'IP en un tableau de 32bits
            byte[] bytes_ip = new byte[]{
             (byte) ((ipl >> 24) & 0xFF),
            (byte) ((ipl >> 16) & 0xFF),
             (byte) ((ipl >> 8 ) & 0xFF),
             (byte) (ipl & 0xFF)};
           
            //Le ET logique entre l'adresse IP et le masque
            byte[] bytes_reseau = new byte[]{
                    (byte) (bytes_ip[0] & bytes_masque[0]),
                    (byte) (bytes_ip[1] & bytes_masque[1]),
                    (byte) (bytes_ip[2] & bytes_masque[2]),
                    (byte) (bytes_ip[3] & bytes_masque[3]),
            };
            //adresse rï¿½seau obtenue
            InetAddress adr_reseau = InetAddress.getByAddress(bytes_reseau);
            //System.out.println("Adresse rï¿½seau =\t"+adr_reseau.getHostAddress());
           
            /********************************
             *Adresse de diffusion broadcast            
            */
            //adresse rï¿½seau - masque inversï¿½ ~val & 0xff
            //inverser le masque
            bytes_masque = new byte[]{
                    (byte) (~bytes_masque[0] & 0xff),
                    (byte) (~bytes_masque[1] & 0xff),
                    (byte) (~bytes_masque[2] & 0xff),
                    (byte) (~bytes_masque[3] & 0xff),
            };
            //System.out.println("Masque inverse (Wildcard) =\t"+InetAddress.getByAddress(bytes_masque).getHostAddress());
           
            byte[] bytes_broadcast = new byte[]{
                    (byte) (bytes_reseau[0] | bytes_masque[0]),
                    (byte) (bytes_reseau[1] | bytes_masque[1]),
                    (byte) (bytes_reseau[2] | bytes_masque[2]),
                    (byte) (bytes_reseau[3] | bytes_masque[3]),
            };
            //adresse Broadcast obtenue
            adrbroadcast = InetAddress.getByAddress(bytes_broadcast);
           System.out.println("Adresse de diffusion (Broadcast) =\t"+adrbroadcast.getHostAddress());
           
             } catch (UnknownHostException e) {
                    e.printStackTrace();
             }
           	return adrbroadcast;
       }
       
       public static long ipToLong(String ipAddress) { 
           long result = 0;   
           String[] ipAddressInArray = ipAddress.split("\\.");

           for (int i = 3; i >= 0; i--) {
                long ip = Long.parseLong(ipAddressInArray[3 - i]);
                result |= ip << (i * 8);
           }
           return result;
  }
}