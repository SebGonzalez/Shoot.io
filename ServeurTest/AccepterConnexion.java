package ServeurTest;

import java.io.*;
import java.net.*;

public class AccepterConnexion implements Runnable {

	private ServerSocket socketserver = null;
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	public Thread t1;

	public AccepterConnexion(ServerSocket ss) {
		socketserver = ss;
	}

	public void run() {

		try {
			while (true) {

				socket = socketserver.accept();
				//socket.setTcpNoDelay(true);
				System.out.println("Une bite se connecte  ");
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());

				String login = in.readLine();

				String[] loginSplit = login.split("/");
				//System.out.println("login : " + login);
				
				String messageInitial = ServeurTest.gestionnaireJoueur.envoiePosInitiale();
				ServeurTest.gestionnaireJoueur.addJoueur(new Personnage(loginSplit[0], Double.parseDouble(loginSplit[1]), Double.parseDouble(loginSplit[2])), out);

				
				messageInitial += ServeurTest.gestionnaireMerde.envoieAll();
				//System.out.println("oui :  " + messageInitial.length() + " " + messageInitial);

				out.writeBytes("I" + messageInitial + "\n");
				//out.flush();

				Thread threadReception = new Thread(new Reception(in, loginSplit[0], out));
				threadReception.start();

			}
		} catch (IOException e) {
			System.err.println("Erreur serveur");
		}

	}
}
