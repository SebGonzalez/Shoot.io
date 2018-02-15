package ServeurTest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import Client.Util.Personnage;

public class GestionnaireJoueur {
	public ConcurrentHashMap<Personnage, PrintWriter> listeJoueur;
	public ArrayList<String> listeJoueurSuppr;

	public GestionnaireJoueur() {
		listeJoueur = new ConcurrentHashMap<Personnage, PrintWriter>();
		listeJoueurSuppr = new ArrayList();
	}

	public void addJoueur(Personnage p, PrintWriter pw) {
		listeJoueur.put(p, pw);
	}
	
	public void remove(String nom) {
		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()){
		   Personnage cle = it.next();
		   if (cle.getNom().equals(nom)) {
			   System.out.println("WOOOOOOOOW");
			   listeJoueurSuppr.add(cle.getNom());
			   	PrintWriter pw = listeJoueur.get(cle);
				it.remove();
				pw.close();
			}
		}
	}

	public void updateJoueur(String message) {
		String messageSplit[] = message.split(";"); 
		
		for(int i=0; i<messageSplit.length; i++) {
			String messageSplit2[] = messageSplit[i].split("/");

			if(messageSplit2[0].equals("U")) {
				Iterator<Personnage> it = listeJoueur.keySet().iterator();
				while (it.hasNext()){
					Personnage cle = it.next();
					if (cle.getNom().equals(messageSplit2[1])) {
						cle.setX(Double.parseDouble(messageSplit2[2]));
						cle.setY(Double.parseDouble(messageSplit2[3]));
						cle.setxVector(Double.parseDouble(messageSplit2[4]));
						cle.setyVector(Double.parseDouble(messageSplit2[5]));
						cle.setAngle(Double.parseDouble(messageSplit2[6]));
						cle.setPosition(Integer.parseInt(messageSplit2[7]));
						cle.getArme().setX(Double.parseDouble(messageSplit2[8]));
						cle.getArme().setY(Double.parseDouble(messageSplit2[9]));
						cle.getArme().setDecalageX(Integer.parseInt(messageSplit2[10]));
					}
				}
			}
			else if(messageSplit2[0].equals("K")) {
				remove(messageSplit2[1]);
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
