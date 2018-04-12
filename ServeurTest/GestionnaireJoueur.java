package ServeurTest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GestionnaireJoueur {
	public ConcurrentHashMap<Personnage, DataOutputStream> listeJoueur;
	public ArrayList<String> listeJoueurSuppr;
	public CopyOnWriteArrayList<Personnage> listeJoueurUpdate;
	public ArrayList<Personnage> listeJoueurAdd;

	public GestionnaireJoueur() {
		listeJoueur = new ConcurrentHashMap<Personnage, DataOutputStream>();
		listeJoueurSuppr = new ArrayList();
		listeJoueurUpdate = new CopyOnWriteArrayList<>();
		listeJoueurAdd = new ArrayList<>();
	}

	public void addJoueur(Personnage p, DataOutputStream pw) {
		listeJoueur.put(p, pw);
		listeJoueurAdd.add(p);
	}

	public void remove(String nom) {
		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()) {
			Personnage cle = it.next();
			if (cle.getNom().equals(nom)) {
				listeJoueurSuppr.add(cle.getNom());
				DataOutputStream pw = listeJoueur.get(cle);
				it.remove();
				try {
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void updateJoueur(String message) {
		//System.out.println(message);
		String messageSplit[] = message.split(";");

		for (int i = 0; i < messageSplit.length; i++) {
			String messageSplit2[] = messageSplit[i].split("/");

			if (messageSplit2[0].equals("U") || messageSplit2[0].equals("S")) {
				Iterator<Personnage> it = listeJoueur.keySet().iterator();
				while (it.hasNext()) {
					Personnage cle = it.next();
					if (cle.getNom().equals(messageSplit2[1])) {
						if(messageSplit2[0].equals("S")) {
							cle.getCaracteristique().setSante(cle.getCaracteristique().getSante() - Integer.parseInt(messageSplit2[2]));
							cle.getCaracteristique().santeDifferenceAdversaire = Integer.parseInt(messageSplit2[2]);
							System.out.println(cle.getCaracteristique().santeDifferenceAdversaire);
							if(cle.getCaracteristique().getSante() <= 0)
								remove(messageSplit2[1]);
							else
								listeJoueurUpdate.add(cle);
							break;
						}
						cle.setX(Double.parseDouble(messageSplit2[2]));
						cle.setY(Double.parseDouble(messageSplit2[3]));
						cle.setxVector(Double.parseDouble(messageSplit2[4]));
						cle.setyVector(Double.parseDouble(messageSplit2[5]));
						cle.setAngle(Double.parseDouble(messageSplit2[6]));
						cle.setPosition(Integer.parseInt(messageSplit2[7]));
						cle.getArme().setX(Double.parseDouble(messageSplit2[8]));
						cle.getArme().setY(Double.parseDouble(messageSplit2[9]));
						cle.getArme().setDecalageX(Integer.parseInt(messageSplit2[10]));
						cle.getCaracteristique().setSante(cle.getCaracteristique().getSante() + Float.parseFloat(messageSplit2[11]));
						cle.getCaracteristique().santeDifferenceClient += Float.parseFloat(messageSplit2[11]);
						if(Float.parseFloat(messageSplit2[11]) > 0)
							System.out.println("Reçu : " + cle.getCaracteristique().santeDifferenceClient);
						if(Float.parseFloat(messageSplit2[11]) > 0) listeJoueurUpdate.add(cle);
						break;
					}
				}
			} else if (messageSplit2[0].equals("K")) {
				remove(messageSplit2[1]);
			}
			else if(messageSplit2[0].equals("M")) {
				ServeurTest.gestionnaireMerde.genererMerde(Integer.parseInt(messageSplit2[1]));
			}
		}
	}

	public String envoiePos(String nom) {
		String s = "";

		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()) {
			Personnage cle = it.next();
			if (!cle.getNom().equals(nom)) {
				s += "U/" + cle.getNom() + "/" + cle.getX() + "/" + cle.getY() + "/" + cle.getxVector() + "/" + cle.getyVector() + "/" + cle.getAngle() + "/" + cle.getPosition() + "/" + cle.getArme().getX() + "/" + cle.getArme().getY() + "/" + cle.getArme().getDecalage()  + ";";
			}
		}

		return s;
	}

	public String envoiePosInitiale() {
		String s = "";

		Iterator<Personnage> it = listeJoueur.keySet().iterator();
		while (it.hasNext()) {
			Personnage cle = it.next();
			s += "A/" + cle.getNom() + "/" + cle.getX() + "/" + cle.getY() + "/" + cle.getIdSkin() + "/" + cle.getIdWeapon() + ";";

		}

		return s;
	}

}