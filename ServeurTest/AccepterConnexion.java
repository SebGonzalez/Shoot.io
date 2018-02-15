package ServeurTest;

import java.io.*;
import java.net.*;

import Client.Util.Personnage;


public class AccepterConnexion implements Runnable{

	private ServerSocket socketserver = null;
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public Thread t1;
	public AccepterConnexion(ServerSocket ss){
	 socketserver = ss;
	}
	
	public void run() {
		
		try {
			while(true){
				
			socket = socketserver.accept();
			socket.setTcpNoDelay(true);
			System.out.println("Une bite se connecte  ");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			String login = in.readLine();
			String[] loginSplit = login.split("/");
			System.out.println("login : " + login);
			ServeurTest.gestionnaireJoueur.addJoueur(new Personnage(loginSplit[0], Double.parseDouble(loginSplit[1]),  Double.parseDouble(loginSplit[2])), out);
			//ServeurTest.addClient(out);
			out.println(ServeurTest.gestionnaireJoueur.envoiePos(loginSplit[0]));
			
			Thread threadReception = new Thread(new Reception(in,loginSplit[0], out));
			threadReception.start();
			
			}
		} catch (IOException e) {	
			System.err.println("Erreur serveur");
		}
		
	}
}
