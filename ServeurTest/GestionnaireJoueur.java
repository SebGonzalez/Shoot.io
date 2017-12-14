package ServeurTest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import Client.Util.Personnage;

public class GestionnaireJoueur {
	public HashMap<Personnage, PrintWriter> listeJoueur;
	public List<String> listeJoueurSuppr;

	public GestionnaireJoueur() {
		listeJoueur = new HashMap();
		listeJoueurSuppr = new ArrayList<>();
	}

	public void addJoueur(Personnage p, PrintWriter pw) {
		listeJoueur.put(p, pw);
	}

	public void remove(String nom) {
		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (cle.getNom().equals(nom)) {
			   listeJoueurSuppr.add(cle.getNom());
				it.remove();
			}
		}
	}

	public void setPosition(String nom, double x, double y) {
		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (cle.getNom().equals(nom)) {
			   cle.setX(x);
			   cle.setY(y);
			}
		}
	}

	public String envoiePos(String nom) {
		String s = "";

		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (!cle.getNom().equals(nom)) {
			   s += "U/" + cle.getNom() + "/" + cle.getX() + "/" + cle.getY() + ";";
			}
		}
		
		return s;
	}

}
