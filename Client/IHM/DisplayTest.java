package Client.IHM;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.TypeImage;

public class DisplayTest {
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Episode 1 � Display Test");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
			System.exit(1);
		}

		glMatrixMode(GL_PROJECTION); // The following code initialises a projection matrix where (0,0) is the
										// upper-left corner of the drawing canvas and (640,480) the bottom-right corner
										// of the drawing canvas:
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);

		RessourcesFactory.loadImage();

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT); // Clear the 2D contents of the window.
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
			glVertex2i(200, 10);
			glVertex2i(200 + 10, 10);
			glVertex2i(200 + 10, 100);
			glVertex2i(200, 100);
			glEnd();

			glColor3f(1f, 1f, 1f);
			RessourcesFactory.getImage(TypeImage.PERSONNAGE).bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2i(Display.getWidth() / 2 - 25, Display.getHeight() / 2 - 25);
			glTexCoord2f(1, 0);
			glVertex2i(Display.getWidth() / 2 + RessourcesFactory.getImage(TypeImage.PERSONNAGE).getTextureWidth(),
					Display.getHeight() / 2 - 25);
			glTexCoord2f(1, 1);
			glVertex2i(Display.getWidth() / 2 + RessourcesFactory.getImage(TypeImage.PERSONNAGE).getTextureWidth(),
					Display.getHeight() / 2 + RessourcesFactory.getImage(TypeImage.PERSONNAGE).getTextureHeight());
			glTexCoord2f(0, 1);
			glVertex2i(Display.getWidth() / 2 - 25,
					Display.getHeight() / 2 + RessourcesFactory.getImage(TypeImage.PERSONNAGE).getTextureHeight());
			glEnd();

			Display.update(); // Refresh the display and poll input.
			Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
		}

		Display.destroy();
		System.exit(0);
	}
}
