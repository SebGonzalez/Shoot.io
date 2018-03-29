package ServeurTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class Emission implements Runnable {

	int wait;
	int compteur = 0;

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
			
			String joueurTue = "";
			Iterator<String> it2 = ServeurTest.gestionnaireJoueur.listeJoueurSuppr.iterator();
			while (it2.hasNext()) {
				String nom = it2.next();
				joueurTue += "S/" + nom + ";";
			}
			joueurTue += ServeurTest.gestionnaireMerde.envoieInfo();

			String message;
			Iterator<Personnage> it = ServeurTest.gestionnaireJoueur.listeJoueur.keySet().iterator();
			while (it.hasNext()) {
				Personnage cle = it.next(); // tu peux typer plus finement ici
				message = ServeurTest.gestionnaireJoueur.envoiePos(cle.getNom());

				if (ServeurTest.gestionnaireJoueur.listeJoueur.get(cle) != null) {
					try {
						if(!joueurTue.equals("")) ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).writeBytes("I" + joueurTue + "\n");
						ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).writeBytes(message + "\n");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							ServeurTest.gestionnaireJoueur.listeJoueur.get(cle).close();
							it.remove();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			}
			ServeurTest.gestionnaireJoueur.listeJoueurSuppr.clear();
			ServeurTest.gestionnaireMerde.listeMerdeModifie.clear();

			// System.out.println("Emission : " + ServeurTest.compteurEmission + ",
			// Reception : " + ServeurTest.compteurReception);
		}
	}
}
