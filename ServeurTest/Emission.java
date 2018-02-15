package ServeurTest;

import java.io.PrintWriter;
import java.util.Iterator;

import Client.Util.Personnage;

public class Emission implements Runnable {

	int wait;
	int compteur = 0;
	public Emission(int wait) {
		this.wait = 0;
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
			   
			   Iterator<String> it2 = ServeurTest.gestionnaireJoueur.listeJoueurSuppr.iterator();
			   while(it2.hasNext()){
				   String nom = it2.next();
				   message += "S/"+nom +";";
			   }
			   if(ServeurTest.gestionnaireJoueur.listeJoueur.get(cle) != null) {
			   ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).println(message);
			   ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).flush();
			   }
			   compteur++;
			   System.out.println(cle.getNom() + " : " + message);
			}
			ServeurTest.gestionnaireJoueur.listeJoueurSuppr.clear();
			if(compteur > 0)
				ServeurTest.compteurEmission++;
			compteur=0;
			//System.out.println("Emission : " + ServeurTest.compteurEmission + ", Reception : " + ServeurTest.compteurReception);
		}
	}
}
