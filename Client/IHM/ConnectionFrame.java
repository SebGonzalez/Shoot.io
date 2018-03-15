package Client.IHM;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Components.*;
import Client.RessourceFactory.*;

import static org.lwjgl.opengl.GL11.*;


public class ConnectionFrame implements ComponentListener, DrawableComponent{

	public static GestionnaireComposant gestionnaireComposant;
	
	public OpenGlImage logoImage; 
	public OpenGlLabel usernameLabel;
	public OpenGlTextField usernameTextField;
	public OpenGlLabel pwdLabel;
	public OpenGlTextField pwdTextField;
	public OpenGlButton connectionButton;
	
	public ConnectionFrame() {
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
    	
    	
    	/* username */
    	
    	usernameLabel = new OpenGlLabel("nom d'utilisateur : ");
    	usernameLabel.setBounds(Display.getWidth()/3, Display.getHeight()/5*2+12, 150, 50);
    	gestionnaireComposant.addComponent(usernameLabel);
    	

    	usernameTextField = new OpenGlTextField();
    	usernameTextField.setBounds(Display.getWidth()/5*3 -75, Display.getHeight()/5*2, 150, 50);
    	gestionnaireComposant.addComponent(usernameTextField);
    	
    	/* Fin : username */
    	
    	
    	/* password */
    	
    	pwdLabel = new OpenGlLabel("Mot de passe : ");
    	pwdLabel.setBounds(Display.getWidth()/3, Display.getHeight()/5*3+12, 150, 50);
    	gestionnaireComposant.addComponent(pwdLabel);
    	pwdTextField = new OpenGlTextField();
    	pwdTextField.setBounds(Display.getWidth()/5*3 -75, Display.getHeight()/5*3, 150, 50);
    	gestionnaireComposant.addComponent(pwdTextField);
    	
    	/* Fin : password */
    	
    	
    	/* connexion */
    	
    	connectionButton = new OpenGlButton("/Client/IHM/Images/play.png");
    	connectionButton.setBounds(Display.getWidth()/2-450/4, Display.getHeight()/4*3, 450, 150);
    	gestionnaireComposant.addComponent(connectionButton);
   
    	/* Fin : connexion */
    	
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

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

   
	public static void main(String args[]) {
    	ConnectionFrame display = new ConnectionFrame();
    	display.start();
	}

	@Override
	public void actionPerformed(Component c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintComponent() {
		// TODO Auto-generated method stub
		
	}
}