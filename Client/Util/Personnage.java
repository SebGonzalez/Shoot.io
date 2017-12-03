package Client.Util;

import static org.lwjgl.opengl.GL11.*;

import Client.RessourceFactory.*;

import java.awt.Graphics;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Personnage {
	private static double VITESSE = 2;

	private String nom;
	private double x;
	private double y;

	private float xVector;
	private float yVector;

	private float nbSprite = 0;
	private int position = 0; // 0 droite, 1 gauche, 2 haut, 3 bas
	private float cumulDelta = 0;
	private double angle = 0;

	private Arme arme;

	public Personnage(String nom) {
		this.nom = nom;
		x = 2000;
		y = 2000;
	}
	
	public Personnage(String nom, int x, int y) {
		this.nom = nom;
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setArme() {
		arme = new Arme(x, y);
	}

	// swing
	public void setVecteur(int xSouris, int ySouris, int largeurMap, int hauteurMap, int largeurFenetre,
			int hauteurFenetre) {

		xVector = xSouris - (largeurFenetre / 2);
		yVector = ySouris - (hauteurFenetre / 2);

		double longueur = Math.sqrt(xVector * xVector + yVector * yVector);

		xVector /= longueur;
		yVector /= longueur;

	}

	// lwjgl
	public void setVecteur(int xSouris, int ySouris, int largeurMap, int hauteurMap) {
		
		//System.out.println(xSouris + " : " + ySouris);
		
		xVector = xSouris - (Display.getWidth()/2);
		yVector = ySouris - (Display.getHeight()/2);
		angle = Math.toDegrees(Math.atan2(ySouris- (Display.getHeight()/2), xSouris));
		if(xSouris>Display.getWidth()/2) angle=-angle;
		//System.out.println(angle);
		
		Vec2 vector = new Vec2(xVector, yVector);

		xVector = vector.x / vector.length();
		yVector = vector.y / vector.length();

		if (xSouris > Display.getWidth() / 2 - 50 && xSouris < Display.getWidth() / 2 + 50) {
			if (ySouris < Display.getHeight() / 2)
				position = 3;
			else
				position = 2;
			angle = 0;
		} else if (xSouris < Display.getWidth() / 2)
			position = 1;
		else
			position = 0;

	}

	public void move(int largeurMap, int hauteurMap, double delta) {

		double deplacementX = xVector * delta * VITESSE;
		double deplacementY = -(yVector * delta * VITESSE);
	//	System.out.println("Personnage : " + deplacementX + " " + deplacementY + " " + delta);

		if (x + deplacementX > 0 && x + deplacementX < largeurMap) {
			x += deplacementX;
		} else if (x + deplacementX < 0) {
			x = 0;
		} else {
			x = largeurMap;
		}
		if (y + deplacementY > 0 && y + deplacementY < hauteurMap) {
			y += deplacementY;
		} else if (y + deplacementY < 0) {
			y = 0;
		} else {
			y = hauteurMap;
		}

	}

	public void updatePersonnage(int xSouris, int ySouris, int largeurMap, int hauteurMap, double delta) {
		setVecteur(Mouse.getX(), Mouse.getY(), largeurMap, hauteurMap);
		if (!estDansPersonnage(xSouris, ySouris)) {
			move(largeurMap, hauteurMap, delta * 0.1);

			cumulDelta += delta;
			if (cumulDelta > 60) {
				nbSprite++;
				cumulDelta = 0;
			}
			if (nbSprite > 2)
				nbSprite = 0;
			
			if (Mouse.isButtonDown(0)) {
				arme.updateArme(delta * 0.1, xVector, yVector, position, true,false, x, y); // il y a un clique gauche
			} else if(Mouse.isButtonDown(1)) {
				arme.updateArme(delta * 0.1, xVector, yVector, position, false,true, x, y); // il y a un clique droit
			}
			else {
				arme.updateArme(delta * 0.1, xVector, yVector, position, false, false, x, y); // pas de clique
			}
		} else
			nbSprite = 0;

	}

	public boolean estDansPersonnage(int xSouris, int ySouris) {
		if (xSouris >= Display.getWidth() / 2 - 50 && xSouris <= Display.getWidth() / 2 + 50
				&& ySouris >= Display.getHeight() / 2 - 50 && ySouris <= Display.getHeight() / 2 + 50)
			return true;
		return false;
	}

	// swing
	public void drawPersonnage(Graphics g, int largeurFenetre, int hauteurFenetre) {
		g.fillOval(largeurFenetre / 2, hauteurFenetre / 2, 15, 15);
		g.drawString("Le personnage", largeurFenetre / 2 - 15, hauteurFenetre / 2 + 15);
	}

	// lwjgl
	public void drawPersonnage() {
		glColor3f(1f, 1f, 1f); // reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		RessourcesFactory.getImage(TypeImage.PERSONNAGE).bind();

		glPushMatrix();

		glTranslated(Display.getWidth() / 2, Display.getHeight() / 2, 0.0d);

		glRotatef((float) angle, 0, 0, 1); // now rotate

		glBegin(GL_QUADS);
		glTexCoord2f(0.20F * nbSprite, 0.20F * position);
		glVertex2i(-50, -50);
		glTexCoord2f(0.20F * (nbSprite + 1), 0.20F * position);
		glVertex2i(+50, -50);
		glTexCoord2f(0.20F * (nbSprite + 1), 0.20F * (position + 1));
		glVertex2i(+50, +50);
		glTexCoord2f(0.20F * nbSprite, 0.20F * (position + 1));
		glVertex2i(-50, +50);
		glEnd();

		glPopMatrix(); // pop off the rotation and transformation
		glDisable(GL_BLEND);

		arme.draw(this);
	}
	
	public void drawPersonnageX(Personnage joueur) {
		int xEcran;
		int yEcran;
		if(this.getX() > joueur.x)
			xEcran = (int) (Display.getWidth() / 2 + (this.getX() - joueur.x));
		else
			xEcran = (int) (Display.getWidth() / 2 - (joueur.x-this.getX()));
		if(this.getY() > y)
			yEcran = (int) (Display.getHeight() / 2 + (this.getY() - joueur.getY()));
		else
			yEcran = (int) (Display.getHeight() / 2 - (joueur.getY()-this.getY()));
		
		glColor3f(1f, 1f, 1f); // reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		RessourcesFactory.getImage(TypeImage.PERSONNAGE).bind();

		glPushMatrix();

		glTranslated(xEcran, yEcran, 0.0d);

		glRotatef((float) angle, 0, 0, 1); // now rotate

		glBegin(GL_QUADS);
		glTexCoord2f(0.20F * nbSprite, 0.20F * position);
		glVertex2i(-50, -50);
		glTexCoord2f(0.20F * (nbSprite + 1), 0.20F * position);
		glVertex2i(+50, -50);
		glTexCoord2f(0.20F * (nbSprite + 1), 0.20F * (position + 1));
		glVertex2i(+50, +50);
		glTexCoord2f(0.20F * nbSprite, 0.20F * (position + 1));
		glVertex2i(-50, +50);
		glEnd();

		glPopMatrix(); // pop off the rotation and transformation
		glDisable(GL_BLEND);

		//arme.draw(this);
	}
}
