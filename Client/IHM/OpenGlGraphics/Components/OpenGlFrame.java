package Client.IHM.OpenGlGraphics.Components;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class OpenGlFrame {
	/** The width of the game display area */
	private final int width = 1200;
	/** The height of the game display area */
	private final int height = 600;

	private OpenGlPanel panel;
	
	public static long lastFrame;

	public OpenGlFrame() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Test");
			Display.setInitialBackground(1.0F, 1.0F, 1.0F);
			// Display.setFullscreen(fullscreen);
			Display.create();

			initializeOpenGL();
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
			System.exit(1);
		}
	}

	private void initializeOpenGL() {
		// enable textures since we're going to use these for our sprites
		glEnable(GL_TEXTURE_2D);

		// disable the OpenGL depth test since we're rendering 2D graphics
		glDisable(GL_DEPTH_TEST);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, width, height);
	}

	public void start() {
		while (!Display.isCloseRequested()) {
			clearScreen();
			displayComponent();

			Display.update(); // Refresh the display and poll input.
			Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
		}

		Display.destroy();
		System.exit(0);
	}
	
	public void setPanel(OpenGlPanel panel) {
		this.panel = panel;
	}

	public OpenGlPanel getPanel() {
		return panel;
	}

	public void displayComponent() {
		if (panel != null)
			panel.displayComponent();
	}
	
	protected void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static double getDelta() {
		long currentTime = getTime();
		double delta = (double) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
}
