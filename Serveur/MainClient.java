package Serveur;

import java.io.IOException;

public class MainClient {

	static EchoClient client;
	
	public static void main(String args[]) {
		client = EchoClient.start();

		String resp1 = client.sendMessage("hello");
		String resp2 = client.sendMessage("world");

		try {
			EchoClient.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
