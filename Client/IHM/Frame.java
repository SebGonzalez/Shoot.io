package Client.IHM;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Client.Util.Map;
import Client.Util.Personnage;

public class Frame extends JFrame {

	public static Personnage personnage = new Personnage("Test");
	public static Map map = new Map(5000, 5000);
	
	public Frame() {
		//this.setSize(1000, 500);
		this.add(new Panel());
		this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	public static void main(String args[]) {
		Frame f = new Frame();
	}
	
}
