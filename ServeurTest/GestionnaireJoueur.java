package ServeurTest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import Client.Util.Personnage;

public class GestionnaireJoueur {
	public ConcurrentHashMap<Personnage, PrintWriter> listeJoueur;
	public List<String> listeJoueurSuppr;

	public GestionnaireJoueur() {
		listeJoueur = new ConcurrentHashMap<Personnage, PrintWriter>();
		listeJoueurSuppr = new ArrayList<>();
	}

	public void addJoueur(Personnage p, PrintWriter pw) {
		listeJoueur.put(p, pw);
		System.out.println("nous");
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

	public void updateJoueur(String message) {
		String messageSplit[] = message.split("/");
		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (cle.getNom().equals(messageSplit[0])) {
			   cle.setX(Double.parseDouble(messageSplit[1]));
			   cle.setY(Double.parseDouble(messageSplit[2]));
			   cle.setxVector(Double.parseDouble(messageSplit[3]));
			   cle.setyVector(Double.parseDouble(messageSplit[4]));
			   cle.setAngle(Double.parseDouble(messageSplit[5]));
			   cle.setPosition(Integer.parseInt(messageSplit[6]));
			   cle.getArme().setX(Double.parseDouble(messageSplit[7]));
			   cle.getArme().setY(Double.parseDouble(messageSplit[8]));
			   cle.getArme().setDecalageX(Integer.parseInt(messageSplit[9]));
			}
		}
	}

	public String envoiePos(String nom) {
		String s = "";

		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (!cle.getNom().equals(nom)) {
			   s += "U/" + cle.getNom() + "/" + cle.getX() + "/" + cle.getY() + "/" + cle.getxVector() + "/" + cle.getyVector() + "/" + cle.getAngle() + "/" + cle.getPosition()
			   		+ "/" + cle.getArme().getX() + "/" + cle.getArme().getY() + "/" + cle.getArme().getDecalage() + ";";
			}
		}
		
		return s;
	}

}
