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
			DisplayTaMere.gestionnaireAdversaire.setReception(message);
		//	System.out.println(message);
			
		    } catch (IOException e) {
				
				e.printStackTrace();
			//	break;
			}
		}
	}

}
