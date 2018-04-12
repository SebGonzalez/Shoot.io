package Client.IHM;

import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import Client.Util.DrawTool;
import Client.Connect.DataBase;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Animator.ComponentAnimator;
import Client.IHM.OpenGlGraphics.Animator.TypeAnimation;
import Client.IHM.OpenGlGraphics.Components.OpenGlAlertBox;
import Client.IHM.OpenGlGraphics.Components.DrawableComponent;
import Client.IHM.OpenGlGraphics.Components.OpenGlButton;
import Client.IHM.OpenGlGraphics.Components.OpenGlFrame;
import Client.IHM.OpenGlGraphics.Components.OpenGlImage;
import Client.IHM.OpenGlGraphics.Components.OpenGlLabel;
import Client.IHM.OpenGlGraphics.Components.OpenGlPanel;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Client.RessourceFactory.RessourcesFactory;
import Client.RessourceFactory.Sprite;
import Client.RessourceFactory.TypeImage;
import Client.Util.Personnage;

/**
 * Ecran de mort d'un joueur constitue du nombre de tirs, de mort effectues durant la partie, et du niveau atteint.
 * 
 * @author Ludovic GIBAULT
 *
 */
public class DeathScreen implements ComponentListener, DrawableComponent, OpenGlPanel {

	private OpenGlFrame frame;

	/**
	 * GestionnaireComposant qui va, comme son nom l'indique, gerer les composants de l'ecran de mort.
	 */
	public static GestionnaireComposant gestionnaireComposant;

	/**
	 * Label affichant le niveau atteint durant la partie.
	 */
	private OpenGlLabel lvlLabel;

	/**
	 * Label affichant le nombre de tues effectues durant la partie.
	 */
	private OpenGlLabel nbKillsLabel;

	/**
	 * Label affichant le nombre de tirs effectues durant la partie.
	 */
	private OpenGlLabel nbThrowsLabel;

	/**
	 * Label affichant le nombre de touchers effectues durant la partie.
	 */
	private OpenGlLabel nbHitsLabel;

	/**
	 * Bouton continuer qui permet au joueur de relancer une partie.
	 */
	private OpenGlButton continueButton;

	private Client.RessourceFactory.TextureLoader loader;

	private Personnage personnage;

	/**
	 * Constructeur sans argument de l'ecran de mort.
	 */
	public DeathScreen(OpenGlFrame frame) {
		this.frame = frame;
		loader = DisplayTaMere.textureLoader;
		gestionnaireComposant = DisplayTaMere.gestionnaireComposant;
		/*try {
			Display.setDisplayMode(new DisplayMode(1200, 600));
			Display.setTitle("Ta Mere");
            Display.setInitialBackground(1.0F, 1.0F, 1.0F);
           // Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
            System.exit(1);
		}
		
		glMatrixMode(GL_PROJECTION); //The following code initialises a projection matrix where (0,0) is the upper-left corner of the drawing canvas and (640,480) the bottom-right corner of the drawing canvas:
    	glLoadIdentity(); // Resets any previous projection matrices
    	glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    	glMatrixMode(GL_MODELVIEW);
    	*/
		
  //  	personnage = new Personnage("Philippos", new Sprite(new Client.RessourceFactory.TextureLoader(), "Client/IHM/Images/Daronne/daronne_0_0.png"));
		    	
    	personnage = DisplayTaMere.personnage;

    	int posXLabels = Display.getWidth()/100*75;
    	
    	
		int largeurBackground = 0, hauteurBackground = 0;
    	
    	while (hauteurBackground < Display.getHeight()) {
			while (largeurBackground < Display.getWidth()) {
				OpenGlImage background = new OpenGlImage(loader, "Client/IHM/Images/ground.png");
				background.setBounds(largeurBackground, hauteurBackground, 512 / 8, 512 / 8);
				largeurBackground += 512 / 8;
				gestionnaireComposant.addComponent(background);
			}
			hauteurBackground += 512 / 8;
			largeurBackground = 0;
		}
    	
    	OpenGlImage planTravail = new OpenGlImage(loader, "Client/IHM/Images/Plan_de_travail.png");
    	planTravail.setBounds(Display.getWidth()/2-250/2-40, Display.getHeight()/2-711/2, 500/2, 1422/2);
    	gestionnaireComposant.addComponent(planTravail);
    	
    	/* **** lvl **** */
    	
    	lvlLabel = new OpenGlLabel("Niveau : " + personnage.getStats().lvl);
    	lvlLabel.setBounds(posXLabels, Display.getHeight()*25/100, 150, 50);
    	gestionnaireComposant.addComponent(lvlLabel);
    	
    	/* **** FIN : lvl **** */
    	    	
    	/* **** nbTues **** */
    	
    	nbKillsLabel = new OpenGlLabel("Tues : " + personnage.getStats().nbKills);
    	nbKillsLabel.setBounds(posXLabels, Display.getHeight()*35/100, 150, 50);
    	gestionnaireComposant.addComponent(nbKillsLabel);
    	
    	/* **** FIN : nbTues **** */
    	
    	/* **** nbLancers **** */
    	
    	nbThrowsLabel = new OpenGlLabel("Lancers : " + personnage.getStats().nbThrows);
    	nbThrowsLabel.setBounds(posXLabels, Display.getHeight()*45/100, 150, 50);
    	gestionnaireComposant.addComponent(nbThrowsLabel);
    	
    	/* **** FIN : nbLancers **** */

    	/* **** nbHits **** */
    	
    	nbHitsLabel = new OpenGlLabel("Touchers : " + personnage.getStats().nbHits);
    	nbHitsLabel.setBounds(posXLabels, Display.getHeight()*55/100, 150, 50);
    	gestionnaireComposant.addComponent(nbHitsLabel);
    	
    	/* **** FIN : nbHits **** */

  
    	OpenGlImage image = new OpenGlImage(loader, "Client/IHM/Images/Daronne/tamer_morte.png");
    	image.setBounds(Display.getWidth()/8, Display.getHeight()/10, 201, 500);
    	gestionnaireComposant.addComponent(image);
    
    	
    	/* bouton Continuer */
    	
    	continueButton = new OpenGlButton("Client/IHM/Images/Couteau-rejouer.png", "Client/IHM/Images/Couteau-rejouer-hover.png");
    	continueButton.setBounds(posXLabels, Display.getHeight()*68/100, 128, 21);
    	gestionnaireComposant.addComponent(continueButton);
    	    	
    	/* FIN : bouton Continuer */
    	
    	System.out.println(DisplayTaMere.dataBase.isConnected);
  	}

	@Override
	public void actionPerformed(Component c) {
		if(c == continueButton) {
			frame.setPanel(new AccueilPanel(frame));
		}
		
	}
	 
	@Override
	public void paintComponent() {	
		/*
		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBindTexture(GL_TEXTURE_2D, 0); //IMPORTANT
		
		DrawTool.drawLine(Color.orange, 0,0,Display.getWidth(), Display.getHeight());
		DrawTool.drawLine(Color.orange, Display.getWidth(), 0, 0, Display.getHeight());
		
		DrawTool.drawTriangle(Color.black, 0, 0, 100, 0, 300, 300);
		DrawTool.drawTriangle(Color.black, Display.getWidth(), 0, Display.getWidth()- 100, 0, Display.getWidth() -300, Display.getHeight() - 300);
		
		DrawTool.drawFilledTriangle(Color.red, 0, 0, 100, 0, 300, 300);
		DrawTool.drawFilledTriangle(Color.red, Display.getWidth(), 0, Display.getWidth()- 100, 0, Display.getWidth() -300, Display.getHeight() - 300);
		*/
		}


	@Override
	public void displayComponent() {
		gestionnaireComposant.render();
		gestionnaireComposant.update();
		gestionnaireComposant.doAction();
	}
}
