package Client.IHM.OpenGlGraphics.Components;

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

import java.io.IOException;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.tests.TestUtils;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.TypeImage;

public class OpenGlButton implements Component {

	private int texture;
	private int textureHover;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public OpenGlButton(String cheminTexture) {
		this(cheminTexture, cheminTexture);
	}
	
	public OpenGlButton(String cheminTexture, String cheminTextureHover) {
		this.texture = RessourcesFactory.loadTexture(cheminTexture);
		this.textureHover = RessourcesFactory.loadTexture(cheminTextureHover);
	}
	
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
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		if(!isMouseEntered()) {
			//RessourcesFactory.getImage(TypeImage.STAT).bind();
			glBindTexture(GL_TEXTURE_2D, texture);
		}
		else {
			//RessourcesFactory.getImage(TypeImage.STAT).bind();
			glBindTexture(GL_TEXTURE_2D, textureHover);
		}
		glBegin(GL_QUADS);
		glTexCoord2f(0.0F, 0.0F);
		glVertex2i(x, y);
		glTexCoord2f(1.0F,0.0F);
		glVertex2i(x+width, y);
		glTexCoord2f(1.0F, 1.0F);
		glVertex2i(x+width, y+height);
		glTexCoord2f(0.0F, 1.0F);
		glVertex2i(x, y+height);
		glEnd();
		
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public boolean isMouseEntered() {
		if (Mouse.getX() >= x && Mouse.getX() <= x + width && (Display.getHeight() - Mouse.getY()) > y && (Display.getHeight() - Mouse.getY()) < y+height)
			return true;
		return false;
	}

	@Override
	public void doAction(ComponentListener listener) {
		while (Mouse.next()){
		    if (Mouse.isButtonDown(0) && isMouseEntered()) {
		        if (Mouse.getEventButton() == 0) {
		        		//cancel
		        }
		    }else {
		        if (Mouse.getEventButton() == 0 && isMouseEntered()) {
		            listener.actionPerformed(this);
		        }
		    }
		}
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
