package ServeurTest;

import java.util.ArrayList;
import java.util.Random;

import Client.Util.Personnage;

public class GestionnaireMerde {
	public static final int MAX_MERDE = 50;
	
	public ArrayList<Merde> listeMerde;
	public ArrayList<Integer> listeMerdeModifie;
	Random r;
	
	public GestionnaireMerde()  {
		listeMerde = new ArrayList<>();
		listeMerdeModifie = new ArrayList<>();
		r = new Random();
		for(int i=0; i<MAX_MERDE; i++) {
			Merde m = new Merde(r.nextInt(5000), r.nextInt(5000), 35);
			listeMerde.add(m);
		}
		//listeMerde.add(new Merde(2050, 2050, 35));
	}
	
	public void genererMerde(int id) {
		listeMerde.get(id).setX(r.nextInt(5000));
		listeMerde.get(id).setY(r.nextInt(5000));
		listeMerdeModifie.add(id);
	}
	
	public String envoieInfo() {
		String message = "";
		for(int m : listeMerdeModifie) {
			message += "M/"+ m + "/" + listeMerde.get(m).getX()+"/"+listeMerde.get(m).getY() + ";";
		}
		return message;
	}
	
	public String envoieAll() {
		String message = "MA/" + listeMerde.size() + "/";
		for(Merde m : listeMerde) {
			message += m.getX()+"/"+m.getY()+"/";
		}
		return message + ";";
	}
}
