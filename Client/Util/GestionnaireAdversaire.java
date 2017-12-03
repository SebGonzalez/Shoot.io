package Client.Util;

import java.util.ArrayList;
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
		for(int i=0; i<100000; i++) {
			listeAdversaire.add(new Personnage("test", r.nextInt(100000), r.nextInt(100000)));
		}
	}
	
	public List<Personnage> getListeAdversaire() {
		return listeAdversaire;
	}

	public void draw(Personnage joueur) {
		for(Personnage p : listeAdversaire) {
			if(p.getX() >= joueur.getX() - Display.getWidth()/2-200 && p.getX() <= joueur.getX() + Display.getWidth()/2+200 && p.getY() >= joueur.getY() - Display.getHeight()/2-200 && p.getY() <= joueur.getY() + Display.getHeight()/2+200) {
				p.drawPersonnageX(joueur);
				listeAdversaireDessine.add(p);
			}
		}
	}
}
