package ServeurTest;

import java.io.*;
import java.net.*;

import Client.Util.Personnage;


public class AccepterConnexion implements Runnable{

	private ServerSocket socketserver = null;
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

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
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			int length = in.readInt();
			if(length > 0) {
				byte[] message = new byte[length];
			    in.readFully(message, 0, message.length); // read the message
			    String login = new String(message);
			    
			    String[] loginSplit = login.split("/");
				System.out.println("login : " + login);
				ServeurTest.gestionnaireJoueur.addJoueur(new Personnage(loginSplit[0], Double.parseDouble(loginSplit[1]),  Double.parseDouble(loginSplit[2])), out);
				//ServeurTest.addClient(out);
				
				String messageInitial = ServeurTest.gestionnaireJoueur.envoiePos(loginSplit[0]);
				messageInitial += ServeurTest.gestionnaireMerde.envoieAll();
				System.out.println("oui :  " + messageInitial);
				
				out.writeInt(messageInitial.length());
				out.write(messageInitial.getBytes());
				
				Thread threadReception = new Thread(new Reception(in,loginSplit[0], out));
				threadReception.start();
				
			}
			
			}
		} catch (IOException e) {	
			System.err.println("Erreur serveur");
		}
		
	}
}
