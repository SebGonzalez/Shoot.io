package Client.IHM.Component;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class OpenGlLabel implements Component {
	
	private int x;
	private int y;
	private String texte;
	
	TrueTypeFont font;
	
	public OpenGlLabel(String texte) {
		this.texte = texte;
		
		Font awtFont = new Font("Times New Roman", Font.ITALIC, 20); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); // base Font, anti-aliasing true/false
	}
	
	public OpenGlLabel(String texte, int size) {
		this.texte = texte;
		
		Font awtFont = new Font("Times New Roman", Font.ITALIC, size); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); // base Font, anti-aliasing true/false
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
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

}