package Client.Connect;

import java.io.*;
import java.net.*;

import Client.IHM.DisplayTaMere;
import Client.Util.Personnage;

public class Client {

	public static Socket socket = null;

	public Client(Personnage p) {

		try {

			System.out.println("Demande de connexion");
			 //socket = new Socket("172.20.10.4",18000);
			socket = new Socket("localhost", 18000);
			//socket.setTcpNoDelay(true);
			System.out.println("Connexion �tablie avec le serveur, authentification :"); // Si le message s'affiche c'est que je suis connect�

			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());

			String message = p.getNom() + "/" + p.getX() + "/" + p.getY();

			out.writeBytes(message + "\n");
			//out.flush();

			Thread t4 = new Thread(new Emission(out, p));
			t4.start();
			Thread t3 = new Thread(new Reception(in));
			t3.start();

		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter � l'adresse " + socket.getLocalAddress());
		} catch (IOException e) {
			System.err.println("Aucun serveur � l'�coute du port " + socket.getLocalPort());
		}

	}

}
