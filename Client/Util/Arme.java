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
import java.util.Iterator;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;
import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TypeImage;

public class Arme {

	private static double VITESSE = 4;

	private double x;
	private double y;
	private int decalage;

	private boolean jeter = false;
	private double xVector;
	private double yVector;
	private double xVectorJeter;
	private double yVectorJeter;

	private long cumulDelta = 0;
	private EtatArme etatArme;
	private Sprite textureArme;

	public Arme(double x, double y) {
		this.x = x;
		this.y = y;

		etatArme = EtatArme.PORTER;
		decalage = -100;
		textureArme = new Sprite(DisplayTaMere.textureLoader, "Client/IHM/Images/arme.jpg");
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
			moveRecuperer(delta, xPerso, yPerso);
		}
		//System.out.println(etatArme);
		collision(degat);
	}
	
	
	public int getDecalage() {
		return decalage;
	}

	public void setDecalage(int position) { // 0 droite, 1 gauche, 2 haut, 3 bas
		if(position == 0)
			decalage = 100;
		else if(position == 1)
			decalage = -100;
	}
	public void setDecalageX(int value) {
		this.decalage = value;
	}

	public void movePorter(double delta, double xPerso, double yPerso) {
		double deplacementX = xVector * delta * 2;
		double deplacementY = -(yVector * delta * 2);
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
			etatArme = EtatArme.LACHER;
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
		
		//System.out.println(x + " : " + xPerso + " " + y + " : " + yPerso);
		if(Math.abs(x-xPerso)< 1 && Math.abs(y-yPerso) < 1) {
			etatArme = EtatArme.PORTER;

		}
		//cumulDelta += delta;
	}
	
	public void collision(int degat) {
		Rectangle armeJoueur = new Rectangle((int)this.getX()+decalage-50, (int)y-25, 100, 50);
		//System.out.println(this.getX() + " " + this.getY());
		for(Iterator<Personnage> it = DisplayTaMere.gestionnaireAdversaire.getListeAdversaire().iterator(); it.hasNext();) {
			Personnage p = it.next();
			Rectangle adversaire = new Rectangle((int)p.getX()-63, (int)p.getY()-99, 63*2, 99*2);
			//System.out.println(p.getX() + " " + p.getY());
			if(adversaire.intersects(armeJoueur)) {
				if(p.getCaracteristique().getSante() - degat < 0) {
					DisplayTaMere.gestionnaireAdversaire.addAversaireTue(p);
					it.remove();
				}
				else {
					p.getCaracteristique().setSante(p.getCaracteristique().getSante()-degat);
					DisplayTaMere.gestionnaireAdversaire.addAversaireUpdate(p);
				}
			}
		}
	}

	public void draw(Personnage p) {
		//System.out.println(p.getX() + " : " + this.x);
		
		int xEcran;
		int yEcran;
		if(p.getX() < x)
			xEcran = (int) (Display.getWidth() / 2 + (x - p.getX())) + decalage - 50;
		else
			xEcran = (int) (Display.getWidth() / 2 - (p.getX()-x)) + decalage - 50;
		if(p.getY() < y)
			yEcran = (int) (Display.getHeight() / 2 + (y - p.getY())) - 25;
		else
			yEcran = (int) (Display.getHeight() / 2 - (p.getY()-y)) - 25;

		textureArme.draw(xEcran, yEcran, 100, 50);
	}
	
	public void drawX(Personnage p) {
		//System.out.println(p.getX() + " : " + this.x);
		
		int xEcran;
		int yEcran;
		
		if(this.getX() > p.getX())
			xEcran = (int) (Display.getWidth() / 2 + (this.getX() - p.getX())) + decalage - 50;
		else
			xEcran = (int) (Display.getWidth() / 2 - (p.getX()-this.getX())) + decalage - 50;
		if(this.getY() > y)
			yEcran = (int) (Display.getHeight() / 2 + (this.getY() - p.getY())) - 25;
		else
			yEcran = (int) (Display.getHeight() / 2 - (p.getY()-this.getY())) - 25;

		textureArme.draw(xEcran, yEcran, 100, 50);
	}
}