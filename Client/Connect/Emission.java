package Client.Connect;

import java.io.DataOutputStream;
import java.io.IOException;

import Client.IHM.DisplayTaMere;
import Client.Util.Merde;
import Client.Util.Personnage;

public class Emission implements Runnable {

	private DataOutputStream out;
	private String message = null;
	private Personnage p;

	public Emission(DataOutputStream out, Personnage p) {
		this.out = out;
		this.p = p;
	}

	public void run() {

		while (true) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = "U/" + p.getNom() + "/" + p.getX() + "/" + p.getY() + "/" + p.getxVector() + "/" + p.getyVector()
					+ "/" + p.getAngle() + "/" + p.getPosition() + "/" + p.getArme().getX() + "/" + p.getArme().getY()
					+ "/" + p.getArme().getDecalage() + ";";
			for (Personnage pTue : DisplayTaMere.gestionnaireAdversaire.getListeAdversaireTue()) {
				System.out.println("KO");
				message += "K/" + pTue.getNom() + ";";
			}
			for(Merde m : DisplayTaMere.gestionnaireMerde.getListeMerdeGraille()) {
				message += "M/" + m.getId() + ";"; 
			}
			
			DisplayTaMere.gestionnaireAdversaire.getListeAdversaireTue().clear();
			DisplayTaMere.gestionnaireMerde.getListeMerdeGraille().clear();

			try {
				out.writeBytes(message + "\n");
				//out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				deconnexion();
			}
		}
	}
	
	public void deconnexion() {
		System.out.println("Connexion terminé avec le serveur");
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
}
