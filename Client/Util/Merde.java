package Client.Util;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;
import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.TypeImage;

public class Merde {
	private int id;
	private int x;
	private int y;
	private int width;
	
	public Merde(int id, int x, int y, int width) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void draw() {
		int xEcran;
		int yEcran;
		
		Personnage p = DisplayTaMere.personnage;
		if(this.getX() > p.getX())
			xEcran = (int) (Display.getWidth() / 2 + (this.getX() - p.getX())) - width/2;
		else
			xEcran = (int) (Display.getWidth() / 2 - (p.getX()-this.getX()))  - width/2;
		if(this.getY() > p.getY())
			yEcran = (int) (Display.getHeight() / 2 + (this.getY() - p.getY())) - width/2;
		else
			yEcran = (int) (Display.getHeight() / 2 - (p.getY()-this.getY())) - width/2;

		glColor3f(1f, 1f, 1f); // reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		RessourcesFactory.getImage(TypeImage.MERDE).bind();

		glBegin(GL_QUADS);
		glTexCoord2f(0.0F, 0.0F);
		glVertex2i(xEcran, yEcran);
		glTexCoord2f(1F, 0);
		glVertex2i(xEcran + width, yEcran);
		glTexCoord2f(1, 1);
		glVertex2i(xEcran + width, yEcran + width);
		glTexCoord2f(0, 1);
		glVertex2i(xEcran, yEcran + width);
		glEnd();

		glDisable(GL_BLEND);
	}
}