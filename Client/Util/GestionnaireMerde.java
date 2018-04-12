package Client.Util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;

public class GestionnaireMerde {
	public ArrayList<Merde> listeMerde;
	public ArrayList<Merde> listeMerdeTmp;
	public ArrayList<Merde> listeMerdeGraille;
	
	public GestionnaireMerde()  {
		listeMerde = new ArrayList<>();
		listeMerdeTmp = new ArrayList<>();
		listeMerdeGraille = new ArrayList<>();
	}
	
	public ArrayList<Merde> getListeMerde() {
		return listeMerde;
	}

	public ArrayList<Merde> getListeMerdeGraille() {
		return listeMerdeGraille;
	}

	public void addMerde(int x, int y, int width) {
		System.out.println("Ajout merde : " + (listeMerde.size() + listeMerdeTmp.size()));
		listeMerdeTmp.add(new Merde(listeMerde.size() + listeMerdeTmp.size(), x, y, width));
	}
	
	public void updateMerde(int id, int x, int y) {
		listeMerde.get(id).setX(x);
		listeMerde.get(id).setY(y);
	}
	
	public void drawMerde() {
		Iterator<Merde> i = listeMerde.iterator();
		while(i.hasNext()) {
			Merde m = i.next();
			//System.out.println(DisplayTaMere.personnage.getX() + " " + m.getX() + (DisplayTaMere.personnage.getX() - m.getX()));
			if(Math.abs(DisplayTaMere.personnage.getX() - m.getX()) < (Display.getWidth()/2 + 250) && Math.abs(DisplayTaMere.personnage.getY() - m.getY()) < (Display.getHeight()/2 + 250)) {
				//System.out.println("Merde "  + " , x : " + m.getX() + " , y : " + m.getY());
				m.draw();
			}
		}
		
		listeMerde.addAll(listeMerdeTmp);
		listeMerdeTmp.clear();
	}
	
	public void collision() {
		Personnage p = DisplayTaMere.personnage;
		Rectangle rectJoueur = new Rectangle((int)p.getX()-63, (int)p.getY()-99, 126, 198);

		Iterator<Merde> i = listeMerde.iterator();
		while(i.hasNext()) {
			Merde m = i.next();
			Rectangle rectMerde = new Rectangle((int)m.getX(), (int)m.getY(), m.getWidth(), m.getWidth());

			if(rectJoueur.intersects(rectMerde)) {
				listeMerdeGraille.add(m);
				DisplayTaMere.personnage.getCaracteristique().addMerdeRamasse();
			}
		}
	}
}