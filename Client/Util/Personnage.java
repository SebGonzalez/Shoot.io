package Client.Util;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Font;
import java.awt.Graphics;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.github.davidmoten.lwjgl.Entity;

import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TypeImage;

public class Personnage {
	private static double VITESSE = 2;

	private TrueTypeFont font;
	private String nom;
	private double x;
	private double y;

	private double xVector;
	private double yVector;

	private int nbSprite = 0;
	private int position = 0; // 0 droite, 1 gauche, 2 haut, 3 bas
	private float cumulDelta = 0;
	private double angle = 0;

	private Arme arme;

	private Stats stats = new Stats();
	Sprite s;

	public Personnage(String nom, Sprite s) {
		this.nom = nom;
		x = 2000;
		y = 2000;
		setArme();
		this.s = s;
	}
	
	public Personnage(String nom, double x, double y) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		setArme();
	}
	public Personnage(String nom, double x, double y, TrueTypeFont font, Sprite s) {
		this.s = s;
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.font = font;
		setArme();
	}
	
	public String getNom() {
		return nom;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setArme() {
		arme = new Arme(x, y);
	}

	public double getxVector() {
		return xVector;
	}

	public void setxVector(double xVector) {
		this.xVector = xVector;
	}

	public double getyVector() {
		return yVector;
	}

	public void setyVector(double yVector) {
		this.yVector = yVector;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Arme getArme() {
		return arme;
	}

	public void setArme(Arme arme) {
		this.arme = arme;
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
	public void setVecteur(double xSouris, double ySouris) {
		
		//System.out.println(xSouris + " : " + ySouris);
		
		xVector = xSouris - (Display.getWidth()/2);
		yVector = ySouris - (Display.getHeight()/2);
		angle = Math.toDegrees(Math.atan2(ySouris- (Display.getHeight()/2), xSouris));
		if(xSouris>Display.getWidth()/2) angle=-angle;
		//System.out.println(angle);
		
		Vec2 vector = new Vec2((float)xVector, (float)yVector);

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
		
	//	System.out.println("xVector : " + xVector + " yVector : " + yVector + " position : " + positioni);

	}

	public void move(int largeurMap, int hauteurMap, double delta) {

		double deplacementX = xVector * delta * VITESSE;
		double deplacementY = -(yVector * delta * VITESSE);
	//	System.out.println("Personnage : " + deplacementX + " " + deplacementY + " " + delta);

		//System.out.println(nom + " : " + deplacementX);
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
		setVecteur(Mouse.getX(), Mouse.getY());
		if (!estDansPersonnage(xSouris, ySouris)) {
			move(largeurMap, hauteurMap, delta * 0.1);

			cumulDelta += delta;
			if (cumulDelta > 100) {
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
		} else {
			nbSprite = 0;
			xVector = 0;
			yVector = 0;
		}
	}
	
	public void updatePersonnageX(double x, double y, double xVector, double yVector, double angle, int position, double xArme, double yArme, int decalageArme, boolean updateServeur, double delta) {
		//System.out.println(x + " " + this.x);
		if(updateServeur) {
			setX(x);
			setY(y);
			setxVector(xVector);
			setyVector(yVector);
			setAngle(angle);
			setPosition(position);
			arme.setX(xArme);
			arme.setY(yArme);
			arme.setDecalageX(decalageArme);
		}
		else {
			if(!Double.isNaN(this.xVector) && !Double.isNaN(this.yVector)) {
				this.x += this.xVector * (delta*0.1) * VITESSE;
				this.y += -(this.yVector * (delta*0.1) * VITESSE);
			}
		}
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
		glColor3f(1f, 1f, 1f);
		s.draw(Display.getWidth()/2-63, Display.getHeight()/2-99, 126, 198);
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
		
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		
		glBindTexture(GL_TEXTURE_2D, RessourcesFactory.getImageDaronne(joueur.nbSprite, joueur.position));

		glPushMatrix();

		glTranslated(xEcran, yEcran, 0.0d);

		glRotatef((float) angle, 0, 0, 1); // now rotate

		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2i(-63, -99);
		glTexCoord2f(1, 0);
		glVertex2i(+63, -99);
		glTexCoord2f(1, 1);
		glVertex2i(+63, +99);
		glTexCoord2f(0, 1);
		glVertex2i(-63, +99);
		glEnd();

		glPopMatrix(); // pop off the rotation and transformation
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);

		arme.drawX(joueur);
	}
	
	public Stats getStats() {
		return stats;
	}
}
