package Client.IHM.OpenGlGraphics.Components;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;

public class OpenGlLabel implements Component {
	
	private int x;
	private int y;
	private String texte;
	boolean visible = true;
	
	TrueTypeFont font;
	
	public OpenGlLabel(String texte) {
		this.texte = texte;
		
		Font awtFont = new Font("Times New Roman", Font.ITALIC, 20); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); // base Font, anti-aliasing true/false
	}
	
	public OpenGlLabel(String texte, int sizeFont) {
		this.texte = texte;
		
		Font awtFont = new Font("Times New Roman", Font.ITALIC, sizeFont); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); // base Font, anti-aliasing true/false
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX() {return x;}
	@Override
	public int getY() {return y;}
	@Override
	public int getWidth() {return 0;}
	@Override
	public int getHeight() {return 0;}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		font.drawString(x, y, texte, Color.black); // x, y, string to draw, color
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
