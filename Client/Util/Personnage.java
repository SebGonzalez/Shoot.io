package Client.Util;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Graphics;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

import Client.IHM.DisplayTaMere;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TextureLoader;

public class Personnage {
	private static final String cheminImageDaronne = "Client/IHM/Images/Daronne/daronne_";
	private static final String cheminImageVieVide = "Client/IHM/Images/vieVide.png";
	private static final String cheminImageViePlein = "Client/IHM/Images/viePlein.png";
	
	static final int NB_POSITION = 4;
	static final int NB_SPRITE = 4; //DE 3 A 4
	private Sprite[][] textureDaronne = new Sprite[NB_POSITION][NB_SPRITE];
	private Sprite vieVide;
	private Sprite viePlein;
	private CaracteristiqueJoueur caracteristique;

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
	private int idSkin;
	private int idWeapon;

	private Arme arme;
	private boolean spriteLoaded = false;

	private Stats stats = new Stats();

	public Personnage(String nom) {
		this.nom = nom;
		x = 2000;
		y = 2000;
		setArme();
		vieVide = new Sprite(DisplayTaMere.textureLoader, cheminImageVieVide);
		viePlein = new Sprite(DisplayTaMere.textureLoader, cheminImageViePlein);
		caracteristique = new CaracteristiqueJoueur(false);
	}
	
	public Personnage(String nom, double x, double y, int idSkin, int idWeapon, boolean joueurCourant) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.idSkin = idSkin;
		this.idWeapon = idWeapon;
		setArme();
		vieVide = new Sprite(DisplayTaMere.textureLoader, cheminImageVieVide);
		viePlein = new Sprite(DisplayTaMere.textureLoader, cheminImageViePlein);
		caracteristique = new CaracteristiqueJoueur(joueurCourant);
	}
	public Personnage(String nom, double x, double y, int idSkin, int idWeapon, TrueTypeFont font, boolean joueurCourant) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.idSkin = idSkin;
		this.idWeapon = idWeapon;
		this.font = font;
		setArme();
		vieVide = new Sprite(DisplayTaMere.textureLoader, cheminImageVieVide);
		viePlein = new Sprite(DisplayTaMere.textureLoader, cheminImageViePlein);
		caracteristique = new CaracteristiqueJoueur(joueurCourant);
	}
	
	public void loadSprite(TextureLoader textureLoader) {
		System.out.println("Id skin : " + idSkin);
		for(int i=0; i<NB_POSITION; i++) {
			for(int y=0; y<NB_SPRITE; y++) {
				String chemin = cheminImageDaronne + i + "_" + y + "_" + idSkin + ".png";
				System.out.println(i + " " + y + " " + chemin);
				textureDaronne[i][y] = new Sprite(textureLoader, chemin);
			}
		}
		spriteLoaded = true;
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

	public int getIdSkin() {
		return idSkin;
	}

	public int getIdWeapon() {
		return idWeapon;
	}

	public CaracteristiqueJoueur getCaracteristique() {
		return caracteristique;
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

		double deplacementX = xVector * delta * caracteristique.getSpeed();
		double deplacementY = -(yVector * delta * caracteristique.getSpeed());
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
			
		} else {
			nbSprite = 0;
			xVector = 0;
			yVector = 0;
			
		}
		
		if (Mouse.isButtonDown(0) && !DisplayTaMere.personnage.getCaracteristique().isOnOneOfTheStats()) {
			arme.updateArme(delta * 0.1, xVector, yVector, position, true,false, x, y, caracteristique.getDegat()); // il y a un clique gauche
			nbSprite = 3;
			
		} else if(Mouse.isButtonDown(1)) {
			arme.updateArme(delta * 0.1, xVector, yVector, position, false,true, x, y, caracteristique.getDegat()); // il y a un clique droit
		}
		else {
			arme.updateArme(delta * 0.1, xVector, yVector, position, false, false, x, y, caracteristique.getDegat()); // pas de clique
		}
		caracteristique.update(delta*0.01);
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
			//caracteristique.setSante(caracteristique.getSante() + sante);
			//System.out.println("SANTE = " + sante);
		}
		else {
			if(!Double.isNaN(this.xVector) && !Double.isNaN(this.yVector)) {
				this.x += this.xVector * (delta*0.1) * caracteristique.getSpeed();
				this.y += -(this.yVector * (delta*0.1) * caracteristique.getSpeed());
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
		if(!spriteLoaded) loadSprite(DisplayTaMere.textureLoader);
		glColor3f(1f, 1f, 1f);
		glPushMatrix();
		glTranslated(Display.getWidth() / 2, Display.getHeight() / 2, 0.0d);
		glRotatef( (float)angle, 0, 0, 1 ); // now rotate
		//arme.draw(this);
		textureDaronne[position][nbSprite].draw(-63, -99, 126, 198);
		
		glPopMatrix(); // pop off the rotation and transformation
		vieVide.draw(Display.getWidth()/2-75, Display.getHeight()/2+99 + 10, 150, 90);
		int widthVie = (int)caracteristique.getSante()*150/caracteristique.getSanteMax();
		//System.out.println(caracteristique.getSante() + " " + caracteristique.getSanteMax());
		viePlein.draw(Display.getWidth()/2-75, Display.getHeight()/2+99 + 10, widthVie,90);
		
		glPushMatrix();
		glTranslated(Display.getWidth() / 2, Display.getHeight() / 2, 0.0d);
		if(arme.getEtatArme() == EtatArme.PORTER) glRotatef( (float)angle, 0, 0, 1 ); // now rotate
		if(arme.getEtatArme() == EtatArme.JETER) {
			//rotationner arme
		}
		arme.draw(this);
		
		glPopMatrix(); // pop off the rotation and transformation
		
		caracteristique.draw();
	}
	
	public void drawPersonnageX(Personnage joueur) {
		if(!spriteLoaded) loadSprite(DisplayTaMere.textureLoader);
		int xEcran;
		int yEcran;
		if(this.getX() > joueur.x)
			xEcran = (int) (Display.getWidth() / 2 + (this.getX() - joueur.x)) -63;
		else
			xEcran = (int) (Display.getWidth() / 2 - (joueur.x-this.getX())) -63;
		if(this.getY() > y)
			yEcran = (int) (Display.getHeight() / 2 + (this.getY() - joueur.getY()))-99;
		else
			yEcran = (int) (Display.getHeight() / 2 - (joueur.getY()-this.getY()))-99;
		
		
		glColor3f(1f, 1f, 1f);
		glPushMatrix();
		glTranslated(xEcran, yEcran, 0.0d);
		glRotatef( (float)angle, 0, 0, 1 ); // now rotate
		textureDaronne[position][nbSprite].draw(0, 0, 126, 198);
		arme.drawX(joueur, x, y);
		vieVide.draw(-75 + 63, +198 + 10, 150, 90);
		int widthVie = (int)caracteristique.getSante()*150/caracteristique.getSanteMax();
		viePlein.draw(-75 + 63, +198 + 10, widthVie,90);
		glPopMatrix(); // pop off the rotation and transformation
	}
	
	public Stats getStats() {
		return stats;
	}
}