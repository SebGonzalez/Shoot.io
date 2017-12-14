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
				String messageSplit[] = message.split("/");

				ServeurTest.gestionnaireJoueur.setPosition(messageSplit[0], Double.parseDouble(messageSplit[1]),
						Double.parseDouble(messageSplit[2]));

			} catch (IOException e) {

				System.out.println(e.toString());
				try {
					ServeurTest.gestionnaireJoueur.remove(login);
					in.close();
					out.close();
					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
