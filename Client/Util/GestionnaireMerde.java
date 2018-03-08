package Client.Util;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;
import Client.Util.Personnage;

public class GestionnaireMerde {
	public ArrayList<Merde> listeMerde;
	
	public GestionnaireMerde()  {
		listeMerde = new ArrayList<>();
	}
	
	public void addMerde(int x, int y, int width) {
		listeMerde.add(new Merde(x, y, width));
	}
	
	public void drawMerde() {
		for(Merde m : listeMerde) {
			//System.out.println(DisplayTaMere.personnage.getX() + " " + m.getX() + (DisplayTaMere.personnage.getX() - m.getX()));
			if(Math.abs(DisplayTaMere.personnage.getX() - m.getX()) < (Display.getWidth()/2 + 250) && Math.abs(DisplayTaMere.personnage.getY() - m.getY()) < (Display.getHeight()/2 + 250)) {
				//System.out.println("Merde "  + " , x : " + m.getX() + " , y : " + m.getY());
				m.draw();
			}
		}
	}
}
