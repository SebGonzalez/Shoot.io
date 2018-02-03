package Client.IHM.Component;

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
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Image;

import org.lwjgl.opengl.Display;

import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.TypeImage;

public class OpenGlImage implements Component {

	private int x;
	private int y;
	private int width;
	private int height;
	private TypeImage image;
	
	public OpenGlImage(TypeImage image) {
		this.image = image;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getX() {return x;}
	@Override
	public int getY() {return y;}
	@Override
	public int getWidth() {return width;}
	@Override
	public int getHeight() {return height;}

	@Override
	public void update() {
	}

	@Override
	public void render() {
		glColor3f(1f, 1f, 1f); // reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		RessourcesFactory.getImage(image).bind();

		glPushMatrix();

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2i(x, y);
		glTexCoord2f(1, 0);
		glVertex2i(x+width, y);
		glTexCoord2f(1, 1);
		glVertex2i(x+width, y+height);
		glTexCoord2f(0, 1);
		glVertex2i(x, y+height);
		glEnd();

		glPopMatrix(); // pop off the rotation and transformation
		glDisable(GL_BLEND);
	}

	@Override
	public boolean isMouseEntered() {
		return false;
	}

	@Override
	public void doAction(ComponentListener listener) {
	}

	@Override
	public boolean isFocused() {
		return false;
	}

	@Override
	public boolean autoSupression() {
		return false;
	}

}
