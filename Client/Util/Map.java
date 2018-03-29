package Client.Util;

import java.awt.Color;
import java.awt.Graphics;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

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

	// lwjgl
	public void drawMap(Personnage p) {
		//glColor3f(1f, 1f, 1f);
		for (int i = 0; i < Display.getWidth(); i++) {
			if ((((int) p.getX() - Display.getWidth() / 2) + i) == 0) {
				glBegin(GL_QUADS);
				glColor3f(1.0F, 0.0F, 0.0F);
				glVertex2i(i, 0);
				glVertex2i(i + 10, 0);
				glVertex2i(i + 10, Display.getHeight());
				glVertex2i(i, Display.getHeight());
				glEnd();
			} else if ((((int) p.getX() - Display.getWidth() / 2) + i) == largeur) {
				glBegin(GL_QUADS);
				glColor3f(1.0F, 0.0F, 0.0F);
				glVertex2i(i, 0);
				glVertex2i(i + 10, 0);
				glVertex2i(i + 10, Display.getHeight());
				glVertex2i(i, Display.getHeight());
				glEnd();
			}
			if ((((int) p.getX() - Display.getWidth() / 2) + i) % 200 == 0) {
				glBegin(GL_LINES);
				glColor3f(0.0F, 0.0F, 0.0F);
				glVertex2i(i, 0);
				glVertex2i(i, Display.getHeight());
				glEnd();
				// g.drawString(""+((p.getX() - Display.getWidth()/2) + i), i, 10);
			}
		}
		for (int i = 0; i < Display.getHeight(); i++) {
			if ((((int) p.getY() - Display.getHeight() / 2) + i) == 0) {
				glBegin(GL_QUADS);
				glColor3f(1.0F, 0.0F, 0.0F);
				glVertex2i(0, i);
				glVertex2i(0, i + 10);
				glVertex2i(Display.getWidth(), i + 10);
				glVertex2i(Display.getWidth(), i);
				glEnd();
			} else if ((((int) p.getY() - Display.getHeight() / 2) + i) == longueur) {
				glBegin(GL_QUADS);
				glColor3f(1.0F, 0.0F, 0.0F);
				glVertex2i(0, i);
				glVertex2i(0, i + 10);
				glVertex2i(Display.getWidth(), i + 10);
				glVertex2i(Display.getWidth(), i);
				glEnd();
			}
			if ((((int) p.getY() - Display.getHeight() / 2) + i) % 200 == 0) {
				glBegin(GL_LINES);
				glColor3f(0.0F, 0.0F, 0.0F);
				glVertex2i(0, i);
				glVertex2i(Display.getWidth(), i);
				glEnd();

				// g.drawString(""+((p.getY() - Display.getHeight()/2) + i), 10, i);
			}
		}
	}

	public void drawMap() {
		for (int i = 0; i < Display.getWidth(); i++) {
			if (i % 200 == 0) {
				glBegin(GL_LINES);
				glColor3f(0.0F, 0.0F, 0.0F);
				glVertex2i(i, 0);
				glVertex2i(i, Display.getHeight());
				glEnd();
				// g.drawString(""+((p.getX() - Display.getWidth()/2) + i), i, 10);
			}
		}
		for (int i = 0; i < Display.getHeight(); i++) {

			if (i % 200 == 0) {
				glBegin(GL_LINES);
				glColor3f(0.0F, 0.0F, 0.0F);
				glVertex2i(0, i);
				glVertex2i(Display.getWidth(), i);
				glEnd();

				// g.drawString(""+((p.getY() - Display.getHeight()/2) + i), 10, i);
			}
		}
	}

}
