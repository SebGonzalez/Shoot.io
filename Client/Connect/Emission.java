package Client.Connect;

import java.io.PrintWriter;
import java.util.Scanner;

import Client.IHM.DisplayTaMere;
import Client.Util.Personnage;


public class Emission implements Runnable {

	private PrintWriter out;
	private String message = null;
	private Personnage p;
	
	public Emission(PrintWriter out, Personnage p) {
		this.out = out;
		this.p = p;
	}

	
	public void run() {
		
		  
		  while(true){
			  try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				message = "U/" + p.getNom() + "/" + p.getX() + "/" + p.getY() + "/" + p.getxVector() + "/" + p.getyVector() + "/" + p.getAngle() + "/" + p.getPosition() 
							+ "/" + p.getArme().getX() + "/" + p.getArme().getY() + "/" + p.getArme().getDecalage() + ";";
				for(Personnage pTue : DisplayTaMere.gestionnaireAdversaire.getListeAdversaireTue()) {
					message += "K/" + pTue.getNom() + ";";
				}
				out.println(message);
			    out.flush();
			  }
	}
}

