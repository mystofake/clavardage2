import java.io.*;

//Classe racine � lancer pour d�marrer le programme de clavardage.

public class Launcher {


	public static void main(String[] args) {
		Controler c = new Controler();

		ConnexionWindows ConW = new ConnexionWindows(c);
	    PrintStream original = System.out;
		while(c.ready) {System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }}));System.out.println("");			}
		System.setOut(original);
		ThreadNetworkMonitoring threadmonitoring = new ThreadNetworkMonitoring(c);
		LoadBalancer loadBalancer = new LoadBalancer(c);
		Database.createDB(c);
		System.out.println("Welcome " + c.mainUser.getPseudo() + " !");
		ListWindows.initialize(c);
		ListWindows.PrintMainUser(c.mainUser);

		c.init();
		}

}

