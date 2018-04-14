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
				//System.out.println("Message reçu : " + login);

				System.out.println(login);
				while(login.charAt(login.length()-1) != 'E') {
					String tmp = in.readLine();
					System.out.println("TMPPPPP : " + tmp);
					login += tmp;
				}
				String[] loginSplit = login.split("/");
				//System.out.println("login : " + login);
				
				String messageInitial = ServeurTest.gestionnaireJoueur.envoiePosInitiale();
				ServeurTest.gestionnaireJoueur.addJoueur(new Personnage(loginSplit[0], Double.parseDouble(loginSplit[1]), Double.parseDouble(loginSplit[2]), Integer.parseInt(loginSplit[3]), Integer.parseInt(loginSplit[4])), out);

				
				messageInitial += ServeurTest.gestionnaireMerde.envoieAll();
				//System.out.println("oui :  " + messageInitial.length() + " " + messageInitial);

				out.writeBytes("I" + messageInitial + "E\n");
				out.flush();

				Thread threadReception = new Thread(new Reception(in, loginSplit[0], out));
				threadReception.start();

			}
		} catch (IOException e) {
			System.err.println("Erreur serveur");
		}

	}
}
