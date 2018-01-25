package Client.IHM.Component;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Font;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class OpenGlTextField implements Component {

	private String texte = "";
	private boolean cursor = true;
	private boolean focus = false;

	private int x;
	private int y;
	private int width;
	private int height;

	private long lastFrame;
	private long cumulDelta;

	TrueTypeFont font;
	Thread t;

	public OpenGlTextField() {
		lastFrame = getTime();

		// t = new Thread(new Runnable() {
			
		//	@Override
		//	public void run() {
				Font awtFont = new Font("Times New Roman", Font.ITALIC, 20); //name, style (PLAIN, BOLD, or ITALIC), size
				font = new TrueTypeFont(awtFont, false); // base Font, anti-aliasing true/false
		//	}
		//});
		
	}
	
	public OpenGlTextField(TrueTypeFont font) {
		lastFrame = getTime();

		this.font = font; // base Font, anti-aliasing true/false
	}

	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String getText() {
		return texte;
	}

	@Override
	public void update() {
		//if(!charge) t.start();
		if (Mouse.isButtonDown(0) && isMouseEntered()) {
			focus = true;
		} else if (Mouse.isButtonDown(0) && !isMouseEntered())
			focus = false;

		cumulDelta += getDelta();

		if (cumulDelta > 700) {
			if (cursor)
				cursor = false;
			else
				cursor = true;
			cumulDelta = 0;
		}
	}

	@Override
	public void render() {
		glBegin(GL_QUADS);
		glColor3f(0.0F, 0.0F, 0.0F); // noir
		glVertex2i(x, y);
		glVertex2i(x + width, y);
		glVertex2i(x + width, y + height);
		glVertex2i(x, y + height);
		glEnd();

		glBegin(GL_QUADS);
		glColor3f(1.0F, 1.0F, 1.0F);
		glVertex2i(x + 1, y + 1);
		glVertex2i(x + 1 + width - 1, y + 1);
		glVertex2i(x + 1 + width - 2, y + 1 + height - 2);
		glVertex2i(x + 1, y + 1 + height - 2);
		glEnd();

		if (cursor) {
			glBegin(GL_LINES);
			glColor3f(0.0F, 0.0F, 0.0F); // noir
			
			glVertex2i(x + 3 + font.getWidth(texte), y + 4);
			glVertex2i(x + 3 + font.getWidth(texte), y + 4 + height - 8);
			glEnd();
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		font.drawString(x, y, texte, Color.black); // x, y, string to draw, color
		glDisable(GL_BLEND);
	}

	@Override
	public boolean isMouseEntered() {
		if (Mouse.getX() >= x && Mouse.getX() <= x + width && Mouse.getY() > y && Mouse.getY() < y + height)
			return true;
		return false;
	}

	@Override
	public void doAction(ComponentListener listener) {
		if (focus) {

			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (texte.length() > 0 && Keyboard.getEventKey() == Keyboard.KEY_BACK) { // Backspace
						texte = texte.substring(0, texte.length() - 1);

					} else {
						texte += Keyboard.getEventCharacter();
					}
				}
			}
		}
		
		/*Keyboard.next();
		if (Keyboard.getEventKeyState()) {
			if (texte.length() > 0 && Keyboard.getEventKey() == Keyboard.KEY_BACK) { // Backspace
				texte = texte.substring(0, texte.length() - 1);

			} else {
				texte += Keyboard.getEventCharacter();
			}
		}*/

	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private double getDelta() {
		long currentTime = getTime();
		double delta = (double) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}

	@Override
	public boolean isFocused() {
		return focus;
	}

}
