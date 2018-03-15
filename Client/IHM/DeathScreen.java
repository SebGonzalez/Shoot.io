package Client.IHM;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
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

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Animator.ComponentAnimator;
import Client.IHM.OpenGlGraphics.Animator.TypeAnimation;
import Client.IHM.OpenGlGraphics.Components.DrawableComponent;
import Client.IHM.OpenGlGraphics.Components.OpenGlButton;
import Client.IHM.OpenGlGraphics.Components.OpenGlImage;
import Client.IHM.OpenGlGraphics.Components.OpenGlLabel;

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
import Client.RessourceFactory.TypeImage;
import Client.Util.Personnage;

/**
 * Ecran de mort d'un joueur constitue du nombre de tirs, de mort effectues durant la partie, et du niveau atteint.
 * 
 * @author Ludovic GIBAULT
 *
 */
public class DeathScreen implements ComponentListener, DrawableComponent {
	
	
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
	
	static OpenGlImage imageStat1;
	
	
	/**
	 * Constructeur sans argument de l'ecran de mort.
	 */
	public DeathScreen(Personnage personnage) {
		try {
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
    	
    	RessourcesFactory.loadImage();
    	gestionnaireComposant = new GestionnaireComposant(this, this);
    	
    	imageStat1 = new OpenGlImage(TypeImage.STAT);
		imageStat1.setBounds(20, 20, 200, 30);
		gestionnaireComposant.addComponent(imageStat1);
		ComponentAnimator c = new ComponentAnimator(imageStat1, TypeAnimation.SCALE, 10, 10, 10);
		gestionnaireComposant.addComponent(c);
    	

    	/* **** lvl **** */
    	
    	lvlLabel = new OpenGlLabel("Niveau : " + personnage.getStats().lvl);
    	lvlLabel.setBounds(Display.getWidth()/2-75, Display.getHeight()/5*0+50, 150, 50);
    	gestionnaireComposant.addComponent(lvlLabel);
    	
    	/* **** FIN : lvl **** */
    	    	
    	/* **** nbTues **** */
    	
    	nbKillsLabel = new OpenGlLabel("Tues : " + personnage.getStats().nbKills);
    	nbKillsLabel.setBounds(Display.getWidth()/2-75, Display.getHeight()/5*1+50, 150, 50);
    	gestionnaireComposant.addComponent(nbKillsLabel);
    	
    	/* **** FIN : nbTues **** */
    	
    	/* **** nbLancers **** */
    	
    	nbThrowsLabel = new OpenGlLabel("Lancers : " + personnage.getStats().nbThrows);
    	nbThrowsLabel.setBounds(Display.getWidth()/2-75, Display.getHeight()/5*2+50, 150, 50);
    	gestionnaireComposant.addComponent(nbThrowsLabel);
    	
    	/* **** FIN : nbLancers **** */

    	/* **** nbHits **** */
    	
    	nbHitsLabel = new OpenGlLabel("Touchers : " + personnage.getStats().nbHits);
    	nbHitsLabel.setBounds(Display.getWidth()/2-75, Display.getHeight()/5*3+50, 150, 50);
    	gestionnaireComposant.addComponent(nbHitsLabel);
    	
    	/* **** FIN : nbHits **** */
    	
    	
    	/* bouton Continuer */
    	
    	continueButton = new OpenGlButton("/Client/IHM/Images/play.png", "/Client/IHM/Images/play_hover.png");
    	continueButton.setBounds(Display.getWidth()/2-75, Display.getHeight()/5*4+50, 150, 50);
    	gestionnaireComposant.addComponent(continueButton);
    	    	
    	/* FIN : bouton Continuer */

    	
    	
	}
	

	public void start() {
		while (!Display.isCloseRequested()) {
    		glClear(GL_COLOR_BUFFER_BIT);  // Clear the 2D contents of the window.
    		
    		
    		gestionnaireComposant.render();
    		gestionnaireComposant.update();
    		gestionnaireComposant.doAction();
        	Display.update(); // Refresh the display and poll input.
        	Display.sync(60); // Wait until 16.67 milliseconds have passed. (Maintain 60 frames-per-second)
        }

		
        Display.destroy();
        System.exit(0);
	}
	
	
	public static void main(String[] args) {
		Personnage perso = new Personnage("Philippos");
		DeathScreen display = new DeathScreen(perso);
		display.start();
	}

	@Override
	public void actionPerformed(Component c) {
		// TODO Auto-generated method stub
		
	}
	 
	@Override
	public void paintComponent() {	
		//glClear(GL_COLOR_BUFFER_BIT);

		glEnable(GL_TEXTURE_2D);
		glColor3f(1f, 1f, 1f); //reset color
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		//glPushMatrix();
		
		RessourcesFactory.getImage(TypeImage.STAT).bind(); //VCOMME CA CA MARCHE MAIS C'EST CHELOU
		glBindTexture(GL_TEXTURE_2D, RessourcesFactory.getIntImage(TypeImage.JOUER));
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2i(10, 10);
		glTexCoord2f(1, 0);
		glVertex2i(250, 10);
		glTexCoord2f(1, 1);
		glVertex2i(250, 55);
		glTexCoord2f(0, 1);
		glVertex2i(10, 55);
		glEnd();
		
		//glBindTexture(GL_TEXTURE_2D, 0);
		//glPopMatrix();

		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		
		
		/*try {
			InputStream in = new FileInputStream("src/Client/IHM/Images/play.png");
		   PNGDecoder decoder = new PNGDecoder(in);
		 
		   System.out.println("width="+decoder.getWidth());
		   System.out.println("height="+decoder.getHeight());
		 
		   ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
		   decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
		   buf.flip();
		   GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

		  GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D,
				     0,
				     0,
				     0,
				     240,
				     45,
				     GL12.GL_BGRA,
				     GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
				     buf);
				   GL11.glPixelTransferf(GL11.GL_ALPHA_BIAS, 0);
				
		   
				   glBegin(GL_QUADS);
					glTexCoord2f(0, 0);
					glVertex2i(300, 300);
					glTexCoord2f(1, 0);
					glVertex2i(350, 300);
					glTexCoord2f(1, 1);
					glVertex2i(350, 350);
					glTexCoord2f(0, 1);
					glVertex2i(300, 350);
					glEnd();
			
			
		   
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		DrawTool.drawLine(Color.orange, 0,0,Display.getWidth(), Display.getHeight());
		DrawTool.drawLine(Color.orange, Display.getWidth(), 0, 0, Display.getHeight());
		
		DrawTool.drawTriangle(Color.black, 0, 0, 100, 0, 300, 300);
		DrawTool.drawTriangle(Color.black, Display.getWidth(), 0, Display.getWidth()- 100, 0, Display.getWidth() -300, Display.getHeight() - 300);
		
		DrawTool.drawFilledTriangle(Color.red, 0, 0, 100, 0, 300, 300);
		DrawTool.drawFilledTriangle(Color.red, Display.getWidth(), 0, Display.getWidth()- 100, 0, Display.getWidth() -300, Display.getHeight() - 300);
		
		}
}
