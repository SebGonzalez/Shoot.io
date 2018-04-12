package Client.IHM.OpenGlGraphics.Components;

import Client.IHM.DisplayTaMere;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.Util.DrawTool;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class OpenGlAlertBox implements Component {

	private int x;
	private int y;
	
	int xClose;
	int yClose;
	int widthClose;
	int heightClose;
	

	TrueTypeFont font;
	
	boolean activateClose = false;
	boolean visible = true;
	
	private int width;
	private int height;
	private String texte = "";
	private boolean autoSup = false;
	
	public OpenGlAlertBox() {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 10); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); //base Font, anti-aliasing true/false
	}
	
	public OpenGlAlertBox(String texte) {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 10); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); //base Font, anti-aliasing true/false
		this.texte = texte;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		xClose = x+width-20;
		yClose = y+5;
		widthClose = 15;
		heightClose = 10;
	}

	public void setTailleFont(int taille) {
		Font awtFont = new Font("Times New Roman", Font.BOLD, taille); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false); //base Font, anti-aliasing true/false
	}
	
	public void setText(String texte) {
		this.texte = texte;
	}
	public String getTexte() {
		return texte;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBindTexture(GL_TEXTURE_2D, 0); //IMPORTANT

		DrawTool.drawFilledQuad(Color.lightGray, x, y, x+width, y, x+width, y+height, x, y+height);
		DrawTool.drawQuad(Color.gray, x, y, x+width, y, x+width, y+height, x, y+height);
		DrawTool.drawFilledQuad(Color.red, xClose, yClose, xClose+widthClose, yClose, xClose+widthClose, yClose+heightClose, xClose, yClose+heightClose);
		DrawTool.drawQuad(Color.black, xClose, yClose, xClose+widthClose, yClose, xClose+widthClose, yClose+heightClose, xClose, yClose+heightClose);
		DrawTool.drawLine(Color.black, xClose, yClose, xClose+widthClose, yClose+heightClose);
		DrawTool.drawLine(Color.black, xClose, yClose+heightClose, xClose+widthClose, yClose);
		
		
		int nbLignes = font.getWidth(texte)/(width-20)+1;
		
		for(int i = 0; i < nbLignes; i++) {
			font.drawString(x+20, y+20+i*font.getLineHeight(), texte.substring(i*(texte.length()/nbLignes), i*(texte.length()/nbLignes)+(texte.length()/nbLignes)));
		}	
		
	}

	@Override
	public boolean isMouseEntered() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isMouseEnteredClose() {
		if (Mouse.getX() >= xClose && Mouse.getX() <= xClose + widthClose && (Display.getHeight() - Mouse.getY()) > yClose && (Display.getHeight() - Mouse.getY()) < yClose+heightClose)
		{
			//DisplayTaMere.gestionnaireComposant.removeComponent(this);
			return true;
		}
		return false;
	}

	@Override
	public void doAction(ComponentListener listener) {

		if(Mouse.isButtonDown(0) && isMouseEnteredClose()) {
	        if (Mouse.getEventButton() == 0) {
	        	activateClose = true;
	        }
	    } else if(activateClose){
	        if (Mouse.getEventButton() == 0 && isMouseEnteredClose()) {
	        	//activateClose = false;
	        	autoSup = true;
	        }
		    else {
		    	activateClose = false;
		    }
	    }
	}

	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean autoSupression() {
		return autoSup ;
	}

	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
