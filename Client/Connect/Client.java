package Client.Connect;

import java.io.*;
import java.net.*;

import Client.Util.Personnage;

public class Client {

	public static Socket socket = null;
	public static Thread t1;
	
	public Client(Personnage p) {
	
		
	try {
		
		System.out.println("Demande de connexion");
		//socket = new Socket("92.222.22.48",18000);
		socket = new Socket("localhost",18000);
		socket.setTcpNoDelay(true);
		System.out.println("Connexion �tablie avec le serveur, authentification :"); // Si le message s'affiche c'est que je suis connect�
		
		t1 = new Thread(new Connexion(socket, p));
		t1.start();
		
		
		
	} catch (UnknownHostException e) {
	  System.err.println("Impossible de se connecter � l'adresse "+socket.getLocalAddress());
	} catch (IOException e) {
	  System.err.println("Aucun serveur � l'�coute du port "+socket.getLocalPort());
	}
	
	

	}

}

