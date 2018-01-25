package Client.Connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Client.IHM.DisplayTaMere;
import Client.Util.Personnage;

public class Connexion implements Runnable {

	private Socket socket = null;
	public static Thread t2;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private Scanner sc = null;
	private Personnage p;

	public Connexion(Socket s, Personnage p) {
		this.p = p;
		socket = s;
	}

	public void run() {

		try {

			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println(p.getNom() + "/" + p.getX() + "/" + p.getY());
			out.flush();

			String listeAdversaire = in.readLine();
			System.out.println("CONNNEEXION : " + listeAdversaire);
			String[] messageSplit = listeAdversaire.split(";");
			for (String s : messageSplit) {
				String messageSplit2[] = s.split("/");
				if (messageSplit2.length == 3) {
					System.out.println("AJOUTTTTTTTTTTTTTTT");
					DisplayTaMere.gestionnaireAdversaire.addAversaire(new Personnage(messageSplit2[0],Double.parseDouble(messageSplit2[1]), Double.parseDouble(messageSplit2[2])));
				}
			}

			t2 = new Thread(new Chat_ClientServeur(socket, p));
			t2.start();

		} catch (IOException e) {

			System.err.println("Le serveur ne répond plus ");
		}
	}

}
