package Serveur;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Serveur {
	
	
	public static void main(String[] args){
		List<Integer> nb = new ArrayList<Integer>();
	      Thread t = new Thread(new Runnable(){
	         public void run(){
	            try {             
	               //Création de la connexion côté serveur, en spécifiant un port d'écoute
	            	
	               DatagramSocket server = new DatagramSocket(12346);
	               System.out.println("lancement serveur");
	               while(true){
	           		for(int i = 0; i < 10; i++)
	        		{
	        			Random r = new Random();
	        			nb.add(r.nextInt(1000));
	        		}
	            	   System.out.println("tour de boucle");
	                  //On s'occupe maintenant de l'objet paquet
	                  byte[] buffer = new byte[8192];
	                  DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	                                   
	                  //Cette méthode permet de récupérer le datagramme envoyé par le client
	                  //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
	                  server.receive(packet);
	                  System.out.println("reception socket");
	                  //nous récupérons le contenu de celui-ci et nous l'affichons
	                  String str = new String(packet.getData());
	                  print("Reçu de la part de " + packet.getAddress() 
	                                    + " sur le port " + packet.getPort() + " : ");
	                  println(str);
	                  
	                  //On réinitialise la taille du datagramme, pour les futures réceptions
	                  packet.setLength(buffer.length);
	                            
	                  //et nous allons répondre à notre client, donc même principe
	                  String string = "";
	                  for(int i = 0; i < nb.size(); i++)
	                	  string += nb.get(i).toString() + ":";
	                  byte[] buffer2 = string.getBytes();
	                  System.out.println(string);
	                  DatagramPacket packet2 = new DatagramPacket(
	                                       buffer2,             //Les données 
	                                       buffer2.length,      //La taille des données
	                                       packet.getAddress(), //L'adresse de l'émetteur
	                                       packet.getPort()     //Le port de l'émetteur
	                  );
	                  
	                  //Et on envoie vers l'émetteur du datagramme reçu précédemment
	                  server.send(packet2);
	                  packet2.setLength(buffer2.length);
	                  nb.clear();
	               }
	            } catch (SocketException e) {
	               e.printStackTrace();
	            } catch (IOException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	         }
	      });  
	      
	      //Lancement du serveur
	      t.start();
	      
	   }
	   
	   public static synchronized void print(String str){
	      System.out.print(str);
	   }
	   public static synchronized void println(String str){
	      System.err.println(str);
	   }
}
