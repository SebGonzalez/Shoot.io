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

import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import Client.Connect.Client;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Animator.PoolComponentMultiAnimator;
import Client.IHM.OpenGlGraphics.Animator.TypeAnimation;
import Client.IHM.OpenGlGraphics.Components.DrawableComponent;
import Client.IHM.OpenGlGraphics.Components.OpenGlButton;
import Client.IHM.OpenGlGraphics.Components.OpenGlImage;
import Client.IHM.OpenGlGraphics.Components.OpenGlLabel;
import Client.IHM.OpenGlGraphics.Components.OpenGlTextField;
import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TextureLoader;
import Client.RessourceFactory.TypeImage;
import Client.Util.GestionnaireAdversaire;
import Client.Util.GestionnaireMerde;
import Client.Util.Map;
import Client.Util.Personnage;
import Client.Util.State;

public class DisplayTaMere implements ComponentListener, DrawableComponent {

	public State state = State.MAIN_MENU;
	public static boolean changer = false;
	
	public static  GestionnaireAdversaire gestionnaireAdversaire = new GestionnaireAdversaire();
	public static GestionnaireMerde gestionnaireMerde = new GestionnaireMerde();
	public static Personnage personnage;
	public static Map map = new Map(5000, 5000);
	public static GestionnaireComposant gestionnaireComposant;
	private OpenGlButton start;
	private OpenGlTextField pseudo;
	
	static OpenGlImage imageStat1;
	static OpenGlImage imageStat2;
	static OpenGlImage imageStat3;

	static TrueTypeFont font;
	
	private static long lastFrame;
	
	static int tempPasse;
	static boolean bite = false;
	
	/** The width of the game display area */
	private final int width = 1200;
	/** The height of the game display area */
	private final int height = 600;
	private static final String IMAGE_HOME = "Client/IHM/Images/";
	TextureLoader textureLoader;
	
	public DisplayTaMere() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Test");
			Display.setInitialBackground(1.0F, 1.0F, 1.0F);
			//Display.setFullscreen(fullscreen);
			Display.create();

			initializeOpenGL();
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
            System.exit(1);
		}
		
    	
    	textureLoader = new TextureLoader();
    	RessourcesFactory.loadImage();
    	Font awtFont = new Font("Times New Roman", Font.ITALIC, 20);
    	font = new TrueTypeFont(awtFont, false);
    	gestionnaireComposant = new GestionnaireComposant(this, this);
    	
    	loadMenu();
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
	
	public void start() {
		while (!Display.isCloseRequested()) {
    		switch (state) {
    		case INTRO:
    			break;
    		case GAME:
    			clearScreen();
    			loopGame();
    			break;
    		case MAIN_MENU:
    			clearScreen();
    			loopMenu();
    			break;
    		}

        	Display.update(); // Refresh the display and poll input.
        	Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
        }
 
        Display.destroy();
        System.exit(0);
	}

	private void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
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
	
	public void loopGame() {
		double delta = getDelta();
		tempPasse += delta;
		
		if(tempPasse > 2000 && !bite) {
			//PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {imageStat1, imageStat2, imageStat3}, TypeAnimation.TRANSLATE, 600, 0, 500);
			//gestionnaireComposant.addComponent(animator);
			bite = true;
			PoolComponentMultiAnimator animator = new PoolComponentMultiAnimator(new Component[] {imageStat1, imageStat2, imageStat3}, new TypeAnimation[] {TypeAnimation.TRANSLATE, TypeAnimation.TRANSLATE}, new Integer[] {600, -600}, new Integer[] {0, 0}, new Integer[] {500, 500}, new Integer[] {500, 500});
			gestionnaireComposant.addComponent(animator);
			animator.setLoop(true);
		}
		/*if(tempPasse > 4000 && bite) {
			PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {imageStat1, imageStat2, imageStat3}, TypeAnimation.TRANSLATE, -600, 0, 500);
			gestionnaireComposant.addComponent(animator);
			bite = false;
			tempPasse = 0;
		}*/
		
		gestionnaireComposant.render();
		gestionnaireComposant.update();
		
		gestionnaireMerde.collision();
		gestionnaireMerde.drawMerde();

		map.drawMap(personnage);
		personnage.drawPersonnage();

		gestionnaireAdversaire.updateAdversaire(delta);
		gestionnaireAdversaire.draw(personnage);
    		
		personnage.updatePersonnage(Mouse.getX(), Mouse.getY(), map.getLargeur(), map.getLongueur(), delta);
		//System.out.println(gestionnaireComposant.getComponent().size() + " " + imageStat1.getX() + " " + imageStat1.getY());
		
		if(changer) {
			loadMenu();		
		}
	}
	
	public void loopMenu() {
		map.drawMap();
		gestionnaireComposant.render();
		gestionnaireComposant.update();
		gestionnaireComposant.doAction();
	}

	@Override
	public void actionPerformed(Component c) {
		if(c == start) {
			state = State.INTRO;
			
			Sprite s = getSprite("Daronne/daronne_0_0.png");
			personnage = new Personnage(pseudo.getText(), 2000,2000,font, s);
			Client client = new Client(personnage);
			personnage.setArme();

			gestionnaireComposant.clear();
			
			imageStat1 = new OpenGlImage(TypeImage.STAT);
			imageStat1.setBounds(20, 20, 200, 30);
			gestionnaireComposant.addComponent(imageStat1);
			imageStat2 = new OpenGlImage(TypeImage.STAT);
			imageStat2.setBounds(20, 50, 200, 30);
			gestionnaireComposant.addComponent(imageStat2);
			imageStat3 = new OpenGlImage(TypeImage.STAT);
			imageStat3.setBounds(20, 80, 200, 30);
			gestionnaireComposant.addComponent(imageStat3);
			
			state = State.GAME;
			lastFrame = getTime();
		}
		
	}
	
	public void loadMenu() {
		OpenGlLabel titre = new OpenGlLabel("Ta Mere.io", 50);
		titre.setBounds(Display.getWidth()/2-100, 80, 0, 0);
		gestionnaireComposant.addComponent(titre);
    	
		pseudo = new OpenGlTextField(font);
		pseudo.setBounds(Display.getWidth()/2 - 320, Display.getHeight()/2-22, 300, 45);
		gestionnaireComposant.addComponent(pseudo);
    	
		start = new OpenGlButton("/Client/IHM/Images/play.png", "/Client/IHM/Images/play_hover.png");
		start.setBounds(Display.getWidth()/2 + 20, Display.getHeight()/2-22, 240, 45);
		gestionnaireComposant.addComponent(start);
		
		state = State.MAIN_MENU;
		changer = false;
	}
	
	public static void main(String args[]) {
    	DisplayTaMere display = new DisplayTaMere();
    	display.start();
    	
	}

	@Override
	public void paintComponent() {		
	}
	
	public Sprite getSprite(String ref) {
		return new Sprite(textureLoader, IMAGE_HOME + ref);
	}
}