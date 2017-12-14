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

	public static void main(String[] args) {

		try {
			ss = new ServerSocket(18000);
			System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());

			threadNewClient = new Thread(new AccepterConnexion(ss));
			threadNewClient.start();

			Thread threadEmission = new Thread(new Emission(Integer.parseInt(args[0])));
			threadEmission.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
