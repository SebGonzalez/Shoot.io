package ServeurTest;

import java.io.PrintWriter;
import java.util.Iterator;

import Client.Util.Personnage;

public class Emission implements Runnable {

	int wait;
	
	public Emission(int wait) {
		this.wait = wait;
	}
	
	public void run() {

		while (true) {
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String message;
			Iterator<Personnage> it = ServeurTest.gestionnaireJoueur.listeJoueur.keySet().iterator();
			while (it.hasNext()){
			   Personnage cle = it.next(); // tu peux typer plus finement ici
			   message = ServeurTest.gestionnaireJoueur.envoiePos(cle.getNom());
			   for(String nomJoueurDeco : ServeurTest.gestionnaireJoueur.listeJoueurSuppr) {
				   message += "S/"+nomJoueurDeco+";";
			   }
			   ServeurTest.gestionnaireJoueur.listeJoueurSuppr.clear();
			   ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).println(message);
			   ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).flush();
			}
		}
	}
	
	/*public static void suppressionJoueur(String nom) {
		gestionnaireJoueur.remove(nom);
		for(PrintWriter pw : listeClient) {
			pw.println("S/"+nom+";");
			System.out.println("suppression envoye");
		}
	}*/
}
