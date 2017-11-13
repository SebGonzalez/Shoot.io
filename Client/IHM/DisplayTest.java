package Client.IHM;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
 
public class DisplayTest {
    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Episode 1 – Display Test");
            Display.create();
        } catch (LWJGLException e) {
            System.err.println("Display wasn't initialized correctly.");
            System.exit(1);
        }
 
        glMatrixMode(GL_PROJECTION); //The following code initialises a projection matrix where (0,0) is the upper-left corner of the drawing canvas and (640,480) the bottom-right corner of the drawing canvas:
    	glLoadIdentity(); // Resets any previous projection matrices
    	glOrtho(0, 640, 480, 0, 1, -1);
    	glMatrixMode(GL_MODELVIEW);
    	
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);  // Clear the 2D contents of the window.
        	glBegin(GL_TRIANGLES);
        	glColor3f(1.0f, 0.0f, 0.0f);      
        	glVertex2i(10, 10);
        	glColor3f(0.0f, 1.0f, 0.0f); 
        	glVertex2i(500, 10);
        	glColor3f(0.0f, 0.0f, 1.0f); 
        	glVertex2i(255, 400);
        	glEnd();
        	
        	glBegin(GL_QUADS);
			glColor3f(1.0F, 0.0F, 0.0F);
			glVertex2i(200, 0);
			glVertex2i(200+10, 0);
			glVertex2i(200, Display.getHeight());
			glVertex2i(200+10, Display.getHeight());
			glEnd();
        	
        	Display.update(); // Refresh the display and poll input.
        	Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
        }
 
        Display.destroy();
        System.exit(0);
    }
}
