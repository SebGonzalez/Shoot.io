package Client.Connect;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import Client.Util.Personnage;

public class Chat_ClientServeur implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	Personnage p;

	public Chat_ClientServeur(Socket s, Personnage p, PrintWriter out, BufferedReader in) {
		this.p = p;
		this.out = out;
		this.in = in;
		socket = s;
	}

	public void run() {
		Thread t4 = new Thread(new Emission(out, p));
		t4.start();
		Thread t3 = new Thread(new Reception(in));
		t3.start();

	}

}
