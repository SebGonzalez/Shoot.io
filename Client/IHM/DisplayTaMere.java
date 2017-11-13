package Client.IHM;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Client.Util.Map;
import Client.Util.Personnage;

import static org.lwjgl.opengl.GL11.*;

public class DisplayTaMere {

	public static Personnage personnage = new Personnage("Test");
	public static Map map = new Map(5000, 5000);
	
	public static void main(String args[]) {
		try {
			//Display.setDisplayMode(new DisplayMode(1200, 1000));
			Display.setTitle("Ta Mere");
            Display.setInitialBackground(1.0F, 1.0F, 1.0F);
            Display.setFullscreen(true);
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
    	
    	while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);  // Clear the 2D contents of the window.
            map.drawMap(personnage);
            personnage.drawPersonnage();
        	
            personnage.setVecteur(Mouse.getX(), Mouse.getY(), map.getLargeur(), map.getLongueur());
        	Display.update(); // Refresh the display and poll input.
        	Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
        }
 
        Display.destroy();
        System.exit(0);
	}
}
