package Serveur;

import java.io.IOException;

public class EchoTest {
	 
    static Process server;
    static EchoClient client;
    
    public static void main(String args[]) {
    	try {
			server = EchoServer.start();
			System.out.println("serveur ouvert");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        client = EchoClient.start();
        
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        
        server.destroy();
        try {
			EchoClient.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
