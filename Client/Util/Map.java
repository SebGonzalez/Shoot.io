package Client.Util;

import java.awt.Color;
import java.awt.Graphics;

public class Map {

	private int largeur;
	private int longueur;
	
	public Map(int largeur, int longueur) {
		super();
		this.largeur = largeur;
		this.longueur = longueur;
	}
	
	
	public int getLargeur() {
		return largeur;
	}


	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}


	public int getLongueur() {
		return longueur;
	}


	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}


	public void drawMap(Graphics g, Personnage p, int largeurFenetre, int hauteurFenetre) {
		for(int i=0; i<largeurFenetre; i++) {
			if(((p.getX() - largeurFenetre/2) + i) == 0) {
				g.setColor(Color.RED);
				g.fillRect(i, 0, 10, hauteurFenetre);
			}
			if(((p.getX() - largeurFenetre/2) + i)%200 == 0) {
				g.setColor(Color.BLACK);
				g.drawString(""+((p.getX() - largeurFenetre/2) + i), i, 10);
				g.drawLine(i, 0, i, hauteurFenetre);
			}
		}
		for(int i=0; i<hauteurFenetre; i++) {
			if(((p.getY() - hauteurFenetre/2) + i) == 0) {
				g.setColor(Color.RED);
				g.drawRect(0, i, largeurFenetre, 20);
			}
			if(((p.getY() - hauteurFenetre/2) + i)%200 == 0) {
				g.setColor(Color.BLACK);
				g.drawString(""+((p.getY() - hauteurFenetre/2) + i), 10, i);
				g.drawLine(0, i, largeurFenetre, i);
			}
		}
	}
	
	
}
