package Client.IHM;

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
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TextureLoader;
import Client.Util.Personnage;

public class Test {
	
	/** The width of the game display area */
	private final int width = 800;
	/** The height of the game display area */
	private final int height = 600;
	private final TextureLoader textureLoader;
	
	Personnage p;
	private static final String IMAGE_HOME = "Client/IHM/Images/";
	
	public Test() {
		try {
			setDisplayMode();
			Display.setTitle("Test");
			//Display.setFullscreen(fullscreen);
			Display.create();

			initializeOpenGL();

			textureLoader = new TextureLoader();
			Sprite s = getSprite("Daronne/daronne_0_0.png");
			
			p = new Personnage("test", s);

		} catch (LWJGLException e) {
			System.out.println("Game exiting - exception in initialization:");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private boolean setDisplayMode() {
		try {
			// get modes
			DisplayMode[] dm = org.lwjgl.util.Display.getAvailableDisplayModes(
					width, height, -1, -1, -1, -1, 60, 60);

			org.lwjgl.util.Display.setDisplayMode(dm, new String[] {
					"width=" + width,
					"height=" + height,
					"freq=" + 60,
					"bpp="
							+ org.lwjgl.opengl.Display.getDisplayMode()
									.getBitsPerPixel() });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Unable to enter fullscreen, continuing in windowed mode");
		}

		return false;
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
	
	public void gameLoop() {
		while (!Display.isCloseRequested()) {

			// clear screen
			clearScreen();

			// let subsystem paint
			p.drawPersonnage();

			// update window contents
			Display.update();
		}

		// clean up
		Display.destroy();
	}
	
	public Sprite getSprite(String ref) {
		return new Sprite(textureLoader, IMAGE_HOME + ref);
	}
	
	private void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static void main(String args[]) {
		Test test = new Test();
		test.gameLoop();
	}

}
