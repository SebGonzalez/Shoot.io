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
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;
import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TypeImage;

public class Arme {

	private static double VITESSE = 4;

	private static int width = 500/8;
	private static int height = 765/8;
	
	
	private double x;
	private double y;
	private int decalageX;
	private int decalageY;

	private boolean jeter = false;
	private double xVector;
	private double yVector;
	private double xVectorJeter;
	private double yVectorJeter;

	private long cumulDelta = 0;
	private EtatArme etatArme;
	private Sprite textureArme;
	
	private ArrayList<String> joueurTouche;

	public Arme(double x, double y) {
		this.x = x;
		this.y = y;

		etatArme = EtatArme.PORTER;
		decalageX = -100;
		decalageY = -140;
		textureArme = new Sprite(DisplayTaMere.textureLoader, "Client/IHM/Images/Armes/poele.png");
		
		joueurTouche = new ArrayList<>();
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
	

	public boolean isJeter() {
		return jeter;
	}

	public void setJeter(boolean jeter) {
		this.jeter = jeter;
	}
	
	public EtatArme getEtatArme() {
		return etatArme;
	}
		
	public void updateArme(double delta, double xVector, double yVector,int direction, boolean jeter, boolean recuperer, double xPerso, double yPerso, int degat) {
		if(jeter && etatArme != EtatArme.JETER) {
			xVectorJeter = xVector;
			yVectorJeter = yVector;
			if(etatArme == EtatArme.PORTER) etatArme = EtatArme.JETER;
		}
		else if(recuperer) {
			if(etatArme == EtatArme.LACHER) etatArme = EtatArme.RECUPERER;
		}
		else {
			if(etatArme == EtatArme.RECUPERER) etatArme = EtatArme.LACHER;
		}
		
		this.xVector = xVector;
		this.yVector = yVector;

		if (etatArme == EtatArme.PORTER) {
			setDecalage(direction);
			movePorter(delta, xPerso, yPerso);
		} else if (etatArme == EtatArme.JETER) {
			moveJeter(delta);
		}
		else if(etatArme == EtatArme.RECUPERER) {
			setDecalage(direction);
			moveRecuperer(delta, xPerso, yPerso);
		}
		//System.out.println(etatArme);
		collision(degat);
	}
	
	
	public int getDecalage() {
		return decalageX;
	}

	public void setDecalage(int position) { // 0 droite, 1 gauche, 2 haut, 3 bas
		if(position == 0) {
			decalageX = -40;
		}
		else if(position == 1) {
			decalageX = 70;
		}
		else if(position == 2) {
			decalageX = 70;
		}
		else if(position == 3) {
			decalageX = -40;
		}
	}
	
	public void setDecalageX(int value) {
		this.decalageX = value;
	}
	
	public void setDecalageY(int value) {
		this.decalageY = value;
	}
	

	public void movePorter(double delta, double xPerso, double yPerso) {
		//double deplacementX = xVector * delta * 2;
		//double deplacementY = -(yVector * delta * 2);
		//System.out.println("Arme : " + deplacementX + " " + deplacementY + " " + delta);
		x = xPerso;
		y = yPerso;
	}

	public void moveJeter(double delta) {
		x = x + xVectorJeter * delta * VITESSE*2;
		y = y - yVectorJeter * delta * VITESSE*2;
		cumulDelta += delta;
		if (cumulDelta > 50) {
			cumulDelta = 0;
			DisplayTaMere.personnage.getStats().nbThrows++;
			etatArme = EtatArme.LACHER;
			joueurTouche.clear();
		}
	}
	
	public void moveRecuperer(double delta, double xPerso, double yPerso) {
		float xVectorRecuperer = (float) (xPerso - x);
		float yVectorRecuperer = (float) (yPerso - y);
		Vec2 vector = new Vec2(xVectorRecuperer, yVectorRecuperer);

		xVectorRecuperer = vector.x / vector.length();
		yVectorRecuperer = vector.y / vector.length();
		
		x = x + xVectorRecuperer * delta * VITESSE*2;
		y = y + yVectorRecuperer * delta * VITESSE*2;
		
		//System.out.println("x : " + Math.abs(x-xPerso)  + " : " + Math.abs(y-yPerso));
		if(Math.abs(x-xPerso) < 2 && Math.abs(y-yPerso) < 2) {
			x = xPerso;
			y = yPerso;
			etatArme = EtatArme.PORTER;
		}
		//cumulDelta += delta;
	}
	
	public void collision(int degat) {
		Rectangle armeJoueur = new Rectangle((int)this.getX()+decalageX-width/2, (int)y+decalageY-height/2, width, height);
		//System.out.println(this.getX() + " " + this.getY());
		for(Iterator<Personnage> it = DisplayTaMere.gestionnaireAdversaire.getListeAdversaire().iterator(); it.hasNext();) {
			Personnage p = it.next();
			Rectangle adversaire = new Rectangle((int)p.getX()-63, (int)p.getY()-99, 63*2, 99*2);
			//System.out.println(p.getX() + " " + p.getY());
			if(adversaire.intersects(armeJoueur)) {
				if(!joueurTouche.contains(p.getNom())) {
					DisplayTaMere.personnage.getStats().nbHits++;
					if(p.getCaracteristique().getSante() - degat <= 0) {
						//System.out.println("iiiiii " + p.getCaracteristique().getSante() + " " + degat + " " + (p.getCaracteristique().getSante() - degat));
						DisplayTaMere.gestionnaireAdversaire.addAversaireTue(p);
						it.remove();
						DisplayTaMere.personnage.getStats().nbKills++;
					}
					else {
						//System.out.println("eeeee " + p.getCaracteristique().getSante() + " " + degat + " " + p.getNom());
						p.getCaracteristique().santeDifferenceAdversaire = degat;
						DisplayTaMere.gestionnaireAdversaire.addAversaireUpdate(p);
					}
					joueurTouche.add(p.getNom());
				}
				else System.out.println("DEJA TOUCHEEEEEEEEEEEEEEE");
			}
		}
	}


	public void draw(Personnage p) {
		//System.out.println(p.getX() + " : " + this.x);
		
		int xEcran;
		int yEcran;
		if(p.getX() < x)
			xEcran = (int) ((x - p.getX())) + decalageX - 50;
		else
			xEcran = (int) (-(p.getX()-x)) + decalageX - 50;
		if(p.getY() < y)
			yEcran = (int) ((y-p.getY())) + decalageY - 25;
		else
			yEcran = (int) (-(p.getY()-y)) + decalageY - 25;

		textureArme.draw(xEcran, yEcran, width, height);
	}
	
	public void drawX(Personnage p, double xJoueur, double yJoueur) {
		//System.out.println(p.getX() + " : " + this.x);
		
		int xEcran = 0;
		int yEcran = 0;
		
		//System.out.println(x + " " + p.getX());
		if(x == xJoueur) {
			xEcran = - 50 + decalageX + width;
			yEcran = - 25 + decalageY + height;
		}
		else {
			xEcran = (int) (x - xJoueur);
			yEcran = (int) (y - yJoueur);
		}

		textureArme.draw(xEcran, yEcran, width, height);
	}
}