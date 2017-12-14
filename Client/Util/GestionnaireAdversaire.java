package Client.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

public class GestionnaireAdversaire {
	private List<Personnage> listeAdversaire;
	private List<Personnage> listeAdversaireDessine;
	
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
	
	public void remove(String nom) {
		for(Iterator<Personnage> it = listeAdversaire.iterator(); it.hasNext();) {
			Personnage p = it.next();
			if(p.getNom().equals(nom)) {
				it.remove();
			}
		}
	}
	
	public void setPosition(String nom, double x, double y) {
		boolean trouve = false;
		for(Personnage p : listeAdversaire) {
			//System.out.println(p.getNom() + " : " + nom);
			if(p.getNom().equals(nom)) {
				System.out.println("oui" + p.getNom() + " : " + Math.abs(p.getX() - x));
				p.setX(x);
				p.setY(y);
				trouve=true;
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
