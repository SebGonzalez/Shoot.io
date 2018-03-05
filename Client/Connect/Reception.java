package Client.Connect;

import java.io.BufferedReader;
import java.io.IOException;

import Client.IHM.DisplayTaMere;

public class Reception implements Runnable {

	private BufferedReader in;
	private String message = "";

	public Reception(BufferedReader in) {

		this.in = in;
	}

	public void run() {

		while (true) {
			try {
				message = in.readLine();
				if (message != null) {
					DisplayTaMere.gestionnaireAdversaire.setReception(message);
				} else {
					deconnexion();
				}

			} catch (IOException e) {

				e.printStackTrace();
				deconnexion();
			}
		}
	}

	public void deconnexion() {
		System.out.println("Connexion terminé avec le serveur");
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
