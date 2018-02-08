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
				//System.out.println(ServeurTest.gestionnaireJoueur.listeJoueur.size());
			   Personnage cle = it.next(); // tu peux typer plus finement ici
			   //System.out.println(cle);
			   message = ServeurTest.gestionnaireJoueur.envoiePos(cle.getNom());
			   
			   Iterator<String> it2 = ServeurTest.gestionnaireJoueur.listeJoueurSuppr.iterator();
			   while(it2.hasNext()){
				   message += "S/"+it2.next()+";";
			   }
			   ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).println(message);
			   ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).flush();
			   compteur++;
			}
			ServeurTest.gestionnaireJoueur.listeJoueurSuppr.clear();
			if(compteur > 0)
				ServeurTest.compteurEmission++;
			compteur=0;
			//System.out.println("Emission : " + ServeurTest.compteurEmission + ", Reception : " + ServeurTest.compteurReception);
		}
	}
}
