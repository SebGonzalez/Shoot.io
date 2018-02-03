package Client.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

public class GestionnaireAdversaire {
	private List<Personnage> listeAdversaire;
	private List<Personnage> listeAdversaireDessine;
	private String reception;
	
	public GestionnaireAdversaire() {
		listeAdversaire = new ArrayList<>();
		listeAdversaireDessine = new ArrayList<>();
		
		Random r = new Random();
		
		//listeAdversaire.add(new Personnage("test", 10, 10));
		/*for(int i=0; i<100000; i++) {
			listeAdversaire.add(new Personnage("test", r.nextInt(100000), r.nextInt(100000)));
		}*/
	}
	
	public void addAversaire(Personnage p) {
		listeAdversaire.add(p);
	}
	
	public void setReception(String message) {
		this.reception = message;
	}
	
	public void updateAdversaire(double delta) {
		if(reception != null)
			updateAdversaireServeur(delta);
		else
			updateAdversaireSimule(delta);
		reception = null;
	}
	
	
	public void updateAdversaireServeur(double delta) {
		String messageSplit[] = reception.split(";");
		for(String s : messageSplit) {
			String messageSplit2[] = s.split("/");
			if(messageSplit2[0].equals("S")) { //suppresion
				remove(messageSplit2[1]);
				
			}
			if(messageSplit2[0].equals("U")) //update
				setInfoAdversaire(messageSplit2[1], Double.parseDouble(messageSplit2[2]),  Double.parseDouble(messageSplit2[3]), Double.parseDouble(messageSplit2[4]), Double.parseDouble(messageSplit2[5]),
						Double.parseDouble(messageSplit2[6]), Integer.parseInt(messageSplit2[7]), Double.parseDouble(messageSplit2[8]), Double.parseDouble(messageSplit2[9]), Integer.parseInt(messageSplit2[10]), delta);
		}
	}
	
	public void updateAdversaireSimule(double delta) {
		for(Personnage p : listeAdversaire) {
			p.updatePersonnageX(0, 0, 0, 0, 0, 0, 0, 0, 0, false, delta);
		}
	}
	
	public void remove(String nom) {
		for(Iterator<Personnage> it = listeAdversaire.iterator(); it.hasNext();) {
			Personnage p = it.next();
			if(p.getNom().equals(nom)) {
				it.remove();
			}
		}
	}
	
	public void setInfoAdversaire(String nom, double x, double y, double xVector, double yVector, double angle, int position, double xArme, double yArme, int decalageArme, double delta) {
		boolean trouve = false;
		for(Personnage p : listeAdversaire) {
			if(p.getNom().equals(nom)) {
				p.updatePersonnageX(x,y,xVector, yVector, angle, position, xArme, yArme, decalageArme, true, delta);
				trouve = true;
				break;
			}
		}
		if(!trouve)
			addAversaire(new Personnage(nom, x, y));
	}
	
	public List<Personnage> getListeAdversaire() {
		return listeAdversaire;
	}

	public void draw(Personnage joueur) {
		for(Personnage p : listeAdversaire) {
			//System.out.println("Adversaire : " + p.getX() + " " + p.getY());
			if(p.getX() >= joueur.getX() - Display.getWidth()/2-200 && p.getX() <= joueur.getX() + Display.getWidth()/2+200 && p.getY() >= joueur.getY() - Display.getHeight()/2-200 && p.getY() <= joueur.getY() + Display.getHeight()/2+200) {
				p.drawPersonnageX(joueur);
				listeAdversaireDessine.add(p);
			}
		}
	}
}