package Client.IHM;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame() {
		this.setSize(1000, 500);
		this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String args[]) {
		Frame f = new Frame();
	}
	
}
