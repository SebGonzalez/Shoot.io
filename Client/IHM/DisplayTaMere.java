package Client.IHM;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Client.Util.Map;
import Client.Util.Personnage;
import Client.Util.State;
import Client.IHM.Component.Component;
import Client.IHM.Component.ComponentListener;
import Client.IHM.Component.GestionnaireComposant;
import Client.IHM.Component.OpenGlButton;
import Client.IHM.Component.OpenGlTextField;
import Client.RessourceFactory.*;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

public class DisplayTaMere implements ComponentListener {

	private static State state = State.MAIN_MENU;
	
	public static Personnage personnage = new Personnage("Test");
	public static Map map = new Map(5000, 5000);
	public static GestionnaireComposant gestionnaireComposant;
	private OpenGlButton start;
	private OpenGlTextField pseudo;
	
	private static long lastFrame;
	
	public DisplayTaMere() {
		try {
			Display.setDisplayMode(new DisplayMode(1200, 600));
			Display.setTitle("Ta Mere");
            Display.setInitialBackground(1.0F, 1.0F, 1.0F);
            Display.setFullscreen(false);
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
            System.exit(1);
		}
		
	glMatrixMode(GL_PROJECTION); //The following code initialises a projection matrix where (0,0) is the upper-left corner of the drawing canvas and (640,480) the bottom-right corner of the drawing canvas:
    	glLoadIdentity(); // Resets any previous projection matrices
    	glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    	glMatrixMode(GL_MODELVIEW);
    	
    	RessourcesFactory.loadImage();
    	gestionnaireComposant = new GestionnaireComposant(this);
    	
    	pseudo = new OpenGlTextField();
    	pseudo.setBounds(Display.getWidth()/2-120-320, Display.getHeight()/2-22, 300, 45);
    	gestionnaireComposant.addComponent(pseudo);
    	
    	start = new OpenGlButton("/Client/IHM/Images/play.png", "/Client/IHM/Images/play_hover.png");
    	start.setBounds(Display.getWidth()/2-120, Display.getHeight()/2-22, 240, 45);
    	gestionnaireComposant.addComponent(start);
    	
	}
	
	public void start() {
		while (!Display.isCloseRequested()) {
    		glClear(GL_COLOR_BUFFER_BIT);  // Clear the 2D contents of the window.
    		
    		switch (state) {
    		case INTRO:
    			break;
    		case GAME:
    			loopGame();
    			break;
    		case MAIN_MENU:
    			loopMenu();
    			break;
    		}

        	Display.update(); // Refresh the display and poll input.
        	Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
        }
 
        Display.destroy();
        System.exit(0);
	}

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static double getDelta() {
        long currentTime = getTime();
        double delta = (double) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }
	
	public static void loopGame() {
		map.drawMap(personnage);
		personnage.drawPersonnage();
    		
		personnage.updatePersonnage(Mouse.getX(), Mouse.getY(), map.getLargeur(), map.getLongueur(), getDelta());
	}
	
	public static void loopMenu() {
		map.drawMap(personnage);
		gestionnaireComposant.render();
		gestionnaireComposant.update();
		gestionnaireComposant.doAction();
		
	}

	@Override
	public void actionPerformed(Component c) {
		if(c == start) {
			personnage.setArme();
			state = State.GAME;
			lastFrame = getTime();
		}
		
	}
	
	public static void main(String args[]) {
    	DisplayTaMere display = new DisplayTaMere();
    	display.start();
    	
	}
}
