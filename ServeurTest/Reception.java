package ServeurTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Reception implements Runnable {

	private BufferedReader in;
	private PrintWriter out;
	private String message = null, login = null;

	public Reception(BufferedReader in, String login, PrintWriter out) {

		this.in = in;
		this.login = login;
		this.out = out;
	}

	public void run() {

		while (true) {
			try {
				message = in.readLine();
				if(message == null) {
					deconnexion();
					break;
				}
				
				ServeurTest.gestionnaireJoueur.updateJoueur(message);
				ServeurTest.compteurReception++;

			} catch (IOException e) {

				System.out.println(e.toString());
				deconnexion();
				break;
			}
		}
	}
	
	public void deconnexion() {
		try {
			ServeurTest.gestionnaireJoueur.remove(login);
			in.close();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
