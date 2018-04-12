package Client.IHM;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
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
import Client.Connect.DataBase;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Animator.PoolComponentMultiAnimator;
import Client.IHM.OpenGlGraphics.Animator.TypeAnimation;
import Client.IHM.OpenGlGraphics.Components.DrawableComponent;
import Client.IHM.OpenGlGraphics.Components.OpenGlButton;
import Client.IHM.OpenGlGraphics.Components.OpenGlFrame;
import Client.IHM.OpenGlGraphics.Components.OpenGlImage;
import Client.IHM.OpenGlGraphics.Components.OpenGlLabel;
import Client.IHM.OpenGlGraphics.Components.OpenGlPanel;
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

public class DisplayTaMere extends OpenGlFrame implements ComponentListener, DrawableComponent {

	public State state = State.MAIN_MENU;
	public static boolean changer = false;

	public static TextureLoader textureLoader = new TextureLoader();

	public static DataBase dataBase = new DataBase();
	public static GestionnaireAdversaire gestionnaireAdversaire;
	public static GestionnaireMerde gestionnaireMerde;
	public static Personnage personnage;
	public static Map map = new Map(5000, 5000);
	public static GestionnaireComposant gestionnaireComposant;

	static TrueTypeFont font;

	private static final String IMAGE_HOME = "Client/IHM/Images/";

	public DisplayTaMere() {
		super();
		RessourcesFactory.loadImage();
		Font awtFont = new Font("Times New Roman", Font.ITALIC, 20);
		font = new TrueTypeFont(awtFont, false);
		gestionnaireComposant = new GestionnaireComposant(this, this);

		//personnage = new Personnage("yolo"); // A ENLEVER
		setPanel(new AccueilPanel(this));
		// loadMenu();
	}

	@Override
	public void actionPerformed(Component c) {
		getPanel().actionPerformed(c);
	}

	@Override
	public void paintComponent() {
		//Ajouter les trucs
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBindTexture(GL_TEXTURE_2D, 0); //IMPORTANT
		getPanel().paintComponent();
		
		if(changer) {
			setPanel(new DeathScreen(this));
			changer = false;
		}
	}
	
	public static void main(String args[]) {
		DisplayTaMere display = new DisplayTaMere();
		display.start();
	}
}