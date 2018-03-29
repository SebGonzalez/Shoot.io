package ServeurTest;

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
import Client.Util.EtatArme;

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

	public Arme(double x, double y) {
		this.x = x;
		this.y = y;

		etatArme = EtatArme.PORTER;
		decalage = -100;
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

	
}
