package Client.Util;

import java.awt.Graphics;
import java.util.Vector;

public class Personnage {
	private static double VITESSE = 2;
	
	private String nom;
	private int x;
	private int y;
	
	private double xVector;
	private double yVector;
	
	public Personnage(String nom) {
		this.nom = nom;
		x = 2000;
		y=2000;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setVecteur(int xSouris, int ySouris, int largeurMap, int hauteurMap, int largeurFenetre, int hauteurFenetre) {
		//System.out.println("SOURIS  "+xSouris + " : " + ySouris);
		System.out.println("DARONNE "+x + " : " + y);
		
		xVector = xSouris - (largeurFenetre/2);
		yVector = ySouris - (hauteurFenetre/2);
		
		double longueur = Math.sqrt(xVector*xVector + yVector*yVector);
		
		xVector/=longueur;
		yVector/=longueur;
		
		System.out.println("VECTOR : "+ xVector +" : " + yVector);
	}
	
	public void move(int largeurMap, int hauteurMap) {
		
		double deplacementX = x +xVector*VITESSE;
		double deplacementY = y + yVector * VITESSE;
		if(deplacementX > 0 && deplacementX < largeurMap)
			x = (int) deplacementX;
		else if(deplacementX < 0)
			x=0;
		else
			x=largeurMap;
		
		if(deplacementY > 0 && deplacementY < hauteurMap)
			y = (int) deplacementY;
		else if(deplacementY < 0)
			y=0;
		else
			y=hauteurMap;
		
	}
	
	public void drawPersonnage(Graphics g, int largeurFenetre, int hauteurFenetre) {
		g.fillOval(largeurFenetre/2, hauteurFenetre/2, 15, 15);
		g.drawString("Le personnage", largeurFenetre/2-15, hauteurFenetre/2+15 );
	}
}
