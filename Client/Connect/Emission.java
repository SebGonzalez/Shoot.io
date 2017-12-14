package Client.Connect;

import java.io.PrintWriter;
import java.util.Scanner;

import Client.Util.Personnage;


public class Emission implements Runnable {

	private PrintWriter out;
	private String login = null, message = null;
	private Personnage p;
	
	public Emission(PrintWriter out, Personnage p) {
		this.out = out;
		this.p = p;
	}

	
	public void run() {
		
		  
		  while(true){
			  try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				message = p.getNom() + "/" + p.getX() + "/" + p.getY();
				out.println(message);
			    out.flush();
			  }
	}
}

