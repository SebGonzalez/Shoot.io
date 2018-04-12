package Client.IHM.OpenGlGraphics.Components;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import java.util.List;

import org.newdawn.slick.Color;

import Client.IHM.DisplayTaMere;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.RessourceFactory.Sprite;
import Client.Util.DrawTool;
import Client.Util.Personnage;

public class MiniMap implements Component {
	private int x, y, width, height;
	
	private Sprite texture;
	
	private List<Personnage> personnages = new ArrayList<>();
	
	public MiniMap() {
		personnages = DisplayTaMere.gestionnaireAdversaire.getListeAdversaire(); //ATTENTION C'EST LA MEME LISTE
		//personnages.add(new Personnage("Phillipe", 2500, 2500, false));
		texture = new Sprite(DisplayTaMere.textureLoader, "Client/IHM/Images/minimap.png");
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		System.out.println("x : " + x + " y  : " + y);
		
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

		texture.draw(x, y, width, height);
		
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBindTexture(GL_TEXTURE_2D, 0); //IMPORTANT
		
		//DrawTool.drawCircle(Color.black, x+width/2, y-height/2, 150);
		DrawTool.drawFilledCircle(Color.green, x+width/2, y+height/2, 6);
		DrawTool.drawCircle(Color.green, x+width/2, y+height/2, 8);

		for(Personnage p : personnages) {
			if(p.getX() > DisplayTaMere.personnage.getX()-width/2*20 || p.getX() < DisplayTaMere.personnage.getX()+width/2*20) {
				int xPersonnage = (int) (p.getX()-DisplayTaMere.personnage.getX());
				int yPersonnage = (int) (p.getY()-DisplayTaMere.personnage.getY());
				
				int hypotenuse = xPersonnage * xPersonnage + yPersonnage * yPersonnage;
				
				if(Math.sqrt(hypotenuse) <= width/2*20)  {
					DrawTool.drawFilledCircle(Color.red, x+width/2+xPersonnage/20, y+height/2+yPersonnage/20, 4);
					DrawTool.drawCircle(Color.red, x+width/2+xPersonnage/20, y+height/2+yPersonnage/20, 6);
				}
			}
		}
	}

	@Override
	public boolean isMouseEntered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doAction(ComponentListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean autoSupression() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setVisible(boolean visible) {
	}

	@Override
	public boolean getVisible() {
		return true;
	}

}
