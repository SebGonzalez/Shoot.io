package ServeurTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ServeurTest {
	public static ServerSocket ss = null;
	public static Thread threadNewClient;
	public static GestionnaireJoueur gestionnaireJoueur = new GestionnaireJoueur();
	public static GestionnaireMerde gestionnaireMerde = new GestionnaireMerde();
	
	public static int compteurReception = 0;
	public static int compteurEmission = 0;

	public static void main(String[] args) {

		if(args.length != 2) { System.out.println("Usage : java -jar Serveur.jar [ms] [port]"); System.exit(0); }
		try {
			ss = new ServerSocket(Integer.parseInt(args[1]));
			System.out.println("Le serveur est l'écoutet sur le port : " + ss.getLocalPort());

			threadNewClient = new Thread(new AccepterConnexion(ss));
			threadNewClient.start();

			Thread threadEmission = new Thread(new Emission(Integer.parseInt(args[0])));
			threadEmission.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
