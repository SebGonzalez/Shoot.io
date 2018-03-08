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

				String messageS = in.readLine();
				//System.out.println("Message : " + messageS);

				if(messageS != null)
					ServeurTest.gestionnaireJoueur.updateJoueur(messageS);
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
