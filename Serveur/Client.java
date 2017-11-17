package Serveur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Client extends Thread{
	DatagramSocket serveur;
	InetAddress ip;
	int port;
	
	double posx;
	double posy;
	
	public Client(InetAddress ip, int port, int index, DatagramSocket serveur)
	{
		this.serveur = serveur;
		this.ip = ip;
		this.port = port;
		Random r = new Random();
		this.posx = r.nextDouble() * r.nextInt(500);
		this.posy = r.nextDouble() * r.nextInt(500);
	}
	
	public void run()
	{
		//TODO
        byte[] buffer = new byte[8192];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        String rep = "";
        try {
			serveur.receive(packet);
			rep = new String(packet.getData());
			update_pos(rep);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void update_pos(String rep) {
		// TODO Auto-generated method stub
		int compteur = 0;
		int i = 0;
		String pos = "";
		boolean isPosX = true;
		boolean isPosY = false;
		while(compteur < 2)
		{
			if(rep.charAt(i) == '/')
			{
				if(isPosX)
					posx = Double.parseDouble(pos);
				else if(isPosY)
					posy = Double.parseDouble(pos);
				compteur++;
				i++;
				isPosX = false;
				isPosY = true;
				pos = "";
				continue;
			}
			pos += rep.charAt(i);
			i++;
		}
	}
	

}
