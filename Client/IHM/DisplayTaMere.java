package Client.IHM;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Client.Util.Map;
import Client.Util.Personnage;
import Client.RessourceFactory.*;

import static org.lwjgl.opengl.GL11.*;

public class DisplayTaMere {

	public static Personnage personnage = new Personnage("Test");
	public static Map map = new Map(5000, 5000);
	
	private static long lastFrame;

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static double getDelta() {
        long currentTime = getTime();
        double delta = (double) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }
	
	public static void main(String args[]) {
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
		
      //  glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		glMatrixMode(GL_PROJECTION); //The following code initialises a projection matrix where (0,0) is the upper-left corner of the drawing canvas and (640,480) the bottom-right corner of the drawing canvas:
    	glLoadIdentity(); // Resets any previous projection matrices
    	glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    	glMatrixMode(GL_MODELVIEW);
    	glEnable(GL_TEXTURE_2D);
    	
    	RessourcesFactory.loadImage();
    	lastFrame = getTime();
    	
    	while (!Display.isCloseRequested()) {
    		glClear(GL_COLOR_BUFFER_BIT);  // Clear the 2D contents of the window.
    		map.drawMap(personnage);
    		personnage.drawPersonnage();
        		
    		personnage.updatePersonnage(Mouse.getX(), Mouse.getY(), map.getLargeur(), map.getLongueur(), getDelta());
        	Display.update(); // Refresh the display and poll input.
        	Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
        }
 
        Display.destroy();
        System.exit(0);
	}
}
