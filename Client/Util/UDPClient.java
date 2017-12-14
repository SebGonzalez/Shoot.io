package Client.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient implements Runnable{
    Personnage p;
    long sleepTime = 1000;
    
    public final static int port = 12346;
    
    public static void main(String args[]) {
    		/*UDPClient cli = new UDPClient("je suis beau", 1000);
    		Thread t = new Thread(cli);
    		t.start();*/
    }
    
    public UDPClient(Personnage p){
      this.p = p;
    }
    
    public void run(){
       int nbre = 0;
       while(true){
          String envoi = p.getX() + "/" + p.getY() + "/" + (++nbre);
          byte[] buffer = envoi.getBytes();
          
          try {
             //On initialise la connexion côté client
             DatagramSocket client = new DatagramSocket();
             
             //On crée notre datagramme
             InetAddress adresse = InetAddress.getByName("92.222.22.48");
             System.out.println("connexion");
             DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);
             System.out.println("creation packet");
             
             //On lui affecte les données à envoyer
             packet.setData(buffer);
             System.out.println("affecte donné");
             
             //On envoie au serveur
             client.send(packet);
             System.out.println("envoie packet");
             
             //Et on récupère la réponse du serveur
             byte[] buffer2 = new byte[8196];
             DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adresse, port);
             client.receive(packet2);
             System.out.println("packet reçu");
             print(envoi + " a reçu une réponse du serveur : ");
             println(new String(packet2.getData()));
             
             try {
                Thread.sleep(sleepTime);
             } catch (InterruptedException e) {
                e.printStackTrace();
             }
             
          } catch (SocketException e) {
             e.printStackTrace();
          } catch (UnknownHostException e) {
             e.printStackTrace();
          } catch (IOException e) {
             e.printStackTrace();
          }
       }
    }   
    
    public static synchronized void print(String str){
        System.out.print(str);
     }
     public static synchronized void println(String str){
        System.err.println(str);
     }
 }   
