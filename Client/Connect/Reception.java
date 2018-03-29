package Client.Connect;

import java.io.DataInputStream;
import java.io.IOException;

import Client.IHM.DisplayTaMere;

public class Reception implements Runnable {

	private DataInputStream in;
	private String message = "";

	public Reception(DataInputStream in) {

		this.in = in;
	}

	public void run() {

		while (true) {
			try {

				String messageS = in.readLine();
				if(messageS.length() > 1 && messageS.charAt(0) == 'I') {
					System.out.println("Avant critique : " + messageS);
					DisplayTaMere.gestionnaireAdversaire.updateDonneCritique(messageS.substring(1));
				}

				DisplayTaMere.gestionnaireAdversaire.setReception(messageS);

			} catch (IOException e) {

				e.printStackTrace();
				deconnexion();
				break;
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
		
	}

}
