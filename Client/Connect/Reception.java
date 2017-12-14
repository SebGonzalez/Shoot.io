package Client.Connect;

import java.io.BufferedReader;
import java.io.IOException;

import Client.IHM.DisplayTaMere;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null;
	
	public Reception(BufferedReader in){
		
		this.in = in;
	}
	
	public void run() {
		
		while(true){
	        try {
			message = in.readLine();
			String[] messageSplit = message.split(";");
			for(String s : messageSplit) {
				String messageSplit2[] = s.split("/");
				if(messageSplit2[0].equals("S")) { //suppresion
					System.out.println("Le serveur vous dit :" +message);
					DisplayTaMere.gestionnaireAdversaire.remove(messageSplit2[1]);
					
				}
				if(messageSplit2[0].equals("U")) //update
					DisplayTaMere.gestionnaireAdversaire.setPosition(messageSplit2[1], Double.parseDouble(messageSplit2[2]),  Double.parseDouble(messageSplit2[3]));
			}
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			//	break;
			}
		}
	}

}
