package Client.IHM.OpenGlGraphics.Components;

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

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TextureLoader;
import Client.RessourceFactory.TypeImage;

public class OpenGlImage implements Component {

	private int x;
	private int y;
	private int width;
	private int height;
	private Sprite image;
	
	public OpenGlImage(TextureLoader loader, String cheminImage) {
		image = new Sprite(loader, cheminImage);
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
		glColor3f(1f, 1f, 1f);
		image.draw(x, y, width, height);
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
