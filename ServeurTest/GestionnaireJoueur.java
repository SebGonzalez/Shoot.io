package ServeurTest;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import Client.Util.Personnage;

public class GestionnaireJoueur {
	public ConcurrentHashMap<Personnage, PrintWriter> listeJoueur;
	public CopyOnWriteArrayList<String> listeJoueurSuppr;

	public GestionnaireJoueur() {
		listeJoueur = new ConcurrentHashMap<Personnage, PrintWriter>();
		listeJoueurSuppr = new CopyOnWriteArrayList();
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
		String messageSplit[] = message.split(";"); 
		String messageSplit2[] = messageSplit[0].split("/");
		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (cle.getNom().equals(messageSplit2[0])) {
			   cle.setX(Double.parseDouble(messageSplit2[1]));
			   cle.setY(Double.parseDouble(messageSplit2[2]));
			   cle.setxVector(Double.parseDouble(messageSplit2[3]));
			   cle.setyVector(Double.parseDouble(messageSplit2[4]));
			   cle.setAngle(Double.parseDouble(messageSplit2[5]));
			   cle.setPosition(Integer.parseInt(messageSplit2[6]));
			   cle.getArme().setX(Double.parseDouble(messageSplit2[7]));
			   cle.getArme().setY(Double.parseDouble(messageSplit2[8]));
			   cle.getArme().setDecalageX(Integer.parseInt(messageSplit2[9]));
			}
		}
		
		if(messageSplit.length > 1) {
			for(int i=1; i<messageSplit.length; i++) {
				messageSplit2 = messageSplit[i].split("/");
				if(messageSplit2[0].equals("K")) {
					listeJoueurSuppr.add(messageSplit2[1]);
				}
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
