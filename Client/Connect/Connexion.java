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
				System.out.println("AAAAAAAAAAA : " + s);
				if (messageSplit2[0].equals("U")) {
					System.out.println("AJOUTTTTTTTTTTTTTTT : " + messageSplit2[0]);
					DisplayTaMere.gestionnaireAdversaire.addAversaire(new Personnage(messageSplit2[1],Double.parseDouble(messageSplit2[2]), Double.parseDouble(messageSplit2[3])));
				}
				else if(messageSplit2[0].equals("MA")) {
					System.out.println("Merde");
					DisplayTaMere.gestionnaireMerde.addMerde(Integer.parseInt(messageSplit2[1]), Integer.parseInt(messageSplit2[1]), Integer.parseInt(messageSplit2[3]));
				}
			}

			t2 = new Thread(new Chat_ClientServeur(socket, p, out, in));
			t2.start();

		} catch (IOException e) {

			System.err.println("Le serveur ne r�pond plus ");
		}
	}

}
