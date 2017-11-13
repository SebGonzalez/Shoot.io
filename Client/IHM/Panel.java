package Client.IHM;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class Panel extends JPanel implements MouseListener, MouseMotionListener{
	
	public Panel() {
		this.setLayout(null);
		
		final Runnable task = new Runnable() {
            
	        @Override
	        public void run() {
	            Frame.personnage.move(Frame.map.getLargeur(), Frame.map.getLongueur());
	            repaint();
	        }
	    };
	         
	    final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	    executor.scheduleAtFixedRate(task, 3, 10, TimeUnit.MILLISECONDS);
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Frame.map.drawMap(g, Frame.personnage, this.getWidth(), this.getHeight());
		Frame.personnage.drawPersonnage(g, this.getWidth(), this.getHeight());
	}

	@Override
	public void mouseDragged(MouseEvent e) {	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Frame.personnage.setVecteur(e.getX(), e.getY(), Frame.map.getLargeur(), Frame.map.getLongueur(), this.getWidth(), this.getHeight());
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
