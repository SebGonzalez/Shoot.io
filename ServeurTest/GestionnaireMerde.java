package ServeurTest;

import java.util.ArrayList;
import java.util.Random;

import Client.Util.Personnage;

public class GestionnaireMerde {
	public ArrayList<Merde> listeMerde;
	
	public ArrayList<Merde> listeMerdeSuppr;
	public ArrayList<Merde> listeMerdeAjoute;
	Random r;
	
	public GestionnaireMerde()  {
		listeMerde = new ArrayList<>();
		listeMerdeSuppr = new ArrayList<>();
		listeMerdeAjoute = new ArrayList<>();
		r = new Random();
		for(int i=0; i<100; i++) {
			Merde m = new Merde(r.nextInt(5000), r.nextInt(5000), 35);
			listeMerde.add(m);
		}
	}
	
	public void genererMerde() {
		if(listeMerde.size() < 100) {
			Merde m = new Merde(r.nextInt(5000), r.nextInt(500), 35);
			listeMerde.add(m);
			listeMerdeAjoute.add(m);
		}
	}
	
	public String envoieInfo() {
		String message = "";
		for(Merde m : listeMerdeAjoute) {
			message += "MA/"+m.getX()+"/"+m.getY()+"/"+m.getWidth()+";";
		}
		for(Merde m : listeMerdeSuppr) {
			message += "MS/"+m.getX()+"/"+m.getY()+"/"+m.getWidth()+";";
		}
		return message;
	}
	
	public String envoieAll() {
		String message = "MA/" + listeMerde.size() + "/";
		for(int i=0; i<100; i++) {
			message += listeMerde.get(i).getX()+"/"+listeMerde.get(i).getY()+"/";
		}
		return message + ";";
	}
}
