package ServeurTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Reception implements Runnable {

	private DataInputStream in;
	private DataOutputStream out;
	private String message = null, login = null;

	public Reception(DataInputStream in, String login, DataOutputStream out) {

		this.in = in;
		this.login = login;
		this.out = out;
	}

	public void run() {

		while (true) {
			try {
				int length = in.readInt();
				if (length > 0) {
					byte[] message = new byte[length];
				    in.readFully(message, 0, message.length); // read the message
				    
				    String messageS = new String(message);

					ServeurTest.gestionnaireJoueur.updateJoueur(messageS);
					ServeurTest.compteurReception++;
				}
				else {
					deconnexion();
					break;
				}
			} catch (IOException e) {

				System.out.println(e.toString());
				deconnexion();
				break;
			}
		}
	}

	public void deconnexion() {
		try {
			System.out.println("DECONNEXION CLIENT");
			ServeurTest.gestionnaireJoueur.remove(login);
			in.close();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
