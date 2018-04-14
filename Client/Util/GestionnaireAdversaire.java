package Client.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;

public class GestionnaireAdversaire {
	private List<Personnage> listeAdversaire;
	//private List<Personnage> listeAdversaireDessine;
	private ArrayList<Personnage> listeAdversaireTue;
	private ArrayList<Personnage> listeAdversaireUpdate;
	private String reception;
	
	public GestionnaireAdversaire() {
		listeAdversaire = new ArrayList<>();
		listeAdversaireTue = new ArrayList<>();
		listeAdversaireUpdate = new ArrayList<>();
		//listeAdversaireDessine = new ArrayList<>();
		
		Random r = new Random();
		
		//listeAdversaire.add(new Personnage("test", 10, 10));
		/*for(int i=0; i<100000; i++) {
			listeAdversaire.add(new Personnage("test", r.nextInt(100000), r.nextInt(100000)));
		}*/
	}
	
	public void addAversaire(Personnage p) {
		listeAdversaire.add(p);
	}
	
	public void addAversaireTue(Personnage p) {
		listeAdversaireTue.add(p);
	}
	
	public void addAversaireUpdate(Personnage p) {
		listeAdversaireUpdate.add(p);
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
	
	public void updateDonneCritique(String reception) {
		//System.out.println("Et c'est le critique : " + reception.length() + " " + reception);
		String messageSplit[] = reception.split(";");
		for(String s : messageSplit) {
			String messageSplit2[] = s.split("/");
			if(messageSplit2[0].equals("S")) { //suppresion
				remove(messageSplit2[1]);
			}
			else if(messageSplit2[0].equals("V")) { //suppresion
				if(messageSplit2[1].equals(DisplayTaMere.personnage.getNom())) {
					DisplayTaMere.personnage.getCaracteristique().setSante(DisplayTaMere.personnage.getCaracteristique().getSante() - Integer.parseInt(messageSplit2[2]));
				}
				else {
					updateSante(messageSplit2[1], Integer.parseInt(messageSplit2[2]), Float.parseFloat(messageSplit2[3]));
				}
			}
			else if (messageSplit2[0].equals("A")) {
				if(!messageSplit2[1].equals(DisplayTaMere.personnage.getNom())) 
					DisplayTaMere.gestionnaireAdversaire.addAversaire(new Personnage(messageSplit2[1], Double.parseDouble(messageSplit2[2]), Double.parseDouble(messageSplit2[3]), Integer.parseInt(messageSplit2[4]), Integer.parseInt(messageSplit2[5]), false));
			} else if (messageSplit2[0].equals("MA")) {
				for(int i=2; i<Integer.parseInt(messageSplit2[1])*2 + 2; i+=2)
					DisplayTaMere.gestionnaireMerde.addMerde(Integer.parseInt(messageSplit2[i]), Integer.parseInt(messageSplit2[i+1]), 35);
				System.out.println("SIIIIZE : " + DisplayTaMere.gestionnaireMerde.listeMerde.size());
			}
			else if(messageSplit2[0].equals("M")) {
				DisplayTaMere.gestionnaireMerde.updateMerde(Integer.parseInt(messageSplit2[1]), Integer.parseInt(messageSplit2[2]), Integer.parseInt(messageSplit2[3]));
			}
			else if(messageSplit2[0].equals("D")) {
				System.out.println("AHHHHHHHHHH");
				System.exit(0);
			}
		}
	}
	
	public void updateAdversaireServeur(double delta) {
		String messageSplit[] = reception.split(";");
		//System.out.println(reception);
		for(String s : messageSplit) {
			String messageSplit2[] = s.split("/");
			if(messageSplit2[0].equals("S")) { //suppresion
				System.out.println("Suppression de : " + messageSplit2[1]);
				remove(messageSplit2[1]);
			}
			else if(messageSplit2[0].equals("U")) //update
				setInfoAdversaire(messageSplit2[1], Double.parseDouble(messageSplit2[2]),  Double.parseDouble(messageSplit2[3]), Double.parseDouble(messageSplit2[4]), Double.parseDouble(messageSplit2[5]),
						Double.parseDouble(messageSplit2[6]), Integer.parseInt(messageSplit2[7]), Double.parseDouble(messageSplit2[8]), Double.parseDouble(messageSplit2[9]), Integer.parseInt(messageSplit2[10]),delta);
			else if (messageSplit2[0].equals("A")) {
				DisplayTaMere.gestionnaireAdversaire.addAversaire(new Personnage(messageSplit2[1], Double.parseDouble(messageSplit2[2]), Double.parseDouble(messageSplit2[3]), Integer.parseInt(messageSplit2[4]), Integer.parseInt(messageSplit2[5]), false));
			} else if (messageSplit2[0].equals("MA")) {
				DisplayTaMere.gestionnaireMerde.addMerde(Integer.parseInt(messageSplit2[1]), Integer.parseInt(messageSplit2[1]), Integer.parseInt(messageSplit2[3]));
			}
			else if(messageSplit2[0].equals("D")) {
				System.out.println("AHHHHHHHHHH");
				System.exit(0);
			}
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
	
	public void updateSante(String nom, int vie, float vie2) {
		for(Iterator<Personnage> it = listeAdversaire.iterator(); it.hasNext();) {
			Personnage p = it.next();
			if(p.getNom().equals(nom)) {
				p.getCaracteristique().setSante(p.getCaracteristique().getSante() - vie + vie2);
				System.out.println("Vie : " + p.getCaracteristique().getSante() );
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
			addAversaire(new Personnage(nom, x, y, 9, 10, false));
	}
	
	public List<Personnage> getListeAdversaire() {
		return listeAdversaire;
	}
	public List<Personnage> getListeAdversaireTue() {
		return listeAdversaireTue;
	}
	public List<Personnage> getListeAdversaireUpdate() {
		return listeAdversaireUpdate;
	}

	public void draw(Personnage joueur) {
		for(Personnage p : listeAdversaire) {
			//System.out.println("Adversaire : " + p.getX() + " " + p.getY());
			if(p.getX() >= joueur.getX() - Display.getWidth()/2-200 && p.getX() <= joueur.getX() + Display.getWidth()/2+200 && p.getY() >= joueur.getY() - Display.getHeight()/2-200 && p.getY() <= joueur.getY() + Display.getHeight()/2+200) {
				p.drawPersonnageX(joueur);
				//listeAdversaireDessine.add(p);
			}
		}
	}
}