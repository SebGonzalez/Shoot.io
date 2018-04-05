package Client.IHM;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Desktop;
import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import Client.Connect.Client;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Animator.PoolComponentAnimator;
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
import Client.Util.DrawTool;
import Client.Util.Personnage;
import Client.Util.State;

public class AccueilPanel implements ComponentListener, OpenGlPanel {

	/**
	 * GestionnaireComposant qui va, comme son nom l'indique, gerer les composants de l'ecran de mort.
	 */
	public static GestionnaireComposant gestionnaireComposant;

	private OpenGlFrame frame;
	/**
	 * Bouton continuer qui permet au joueur de relancer une partie.
	 */
	private OpenGlButton playButton;

	private TextureLoader loader;

	/* *** Connexion *** */

	public OpenGlLabel usernameLabel;
	public OpenGlTextField usernameTextField;
	public OpenGlLabel pwdLabel;
	public OpenGlTextField pwdTextField;
	public OpenGlButton connectionButton;
	/* *** FIN : Connexion *** */

	/* *** Jouer sans connexion *** */
	private OpenGlButton start;
	private OpenGlLabel pseudoLabel;
	private OpenGlTextField pseudoTextField;
	/* *** FIN : Jouer sans connexion *** */

	private OpenGlButton inscription;
	private OpenGlButton playAsGuest;
	

	public AccueilPanel(OpenGlFrame frame) {
		this.frame = frame;
		loader = DisplayTaMere.textureLoader;
		gestionnaireComposant = DisplayTaMere.gestionnaireComposant;
		initAccueil();
	}

	@Override
	public void paintComponent() {
	}

	@Override
	public void actionPerformed(Component c) {
		System.out.println("lo");
		if (c == playButton) {

			Component comp[] = { playAsGuest, inscription, usernameLabel, usernameTextField, pwdLabel, pwdTextField, connectionButton, playButton };
			PoolComponentAnimator animation = new PoolComponentAnimator(comp, TypeAnimation.TRANSLATE, Display.getWidth(), 0, 750);
			gestionnaireComposant.addComponent(animation);

		}
		else if(c == inscription) {
			try {
				  Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL = new URI("https://www.youtube.com/watch?v=Kg-HHXuOBlw");
				  desktop.browse(oURL);
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		else if(c == playAsGuest) {
			Component comp[] = { playAsGuest, inscription, start, pseudoLabel, pseudoTextField, playButton};
			PoolComponentAnimator animation2 = new PoolComponentAnimator(comp, TypeAnimation.TRANSLATE, -Display.getWidth(), 0, 750);
			gestionnaireComposant.addComponent(animation2);
		}
		else if (c == start) {
			gestionnaireComposant.clear();
			DisplayTaMere.personnage = new Personnage(pseudoTextField.getText(), 2000, 2000);
			Client client = new Client(DisplayTaMere.personnage);
			DisplayTaMere.personnage.setArme();
			frame.setPanel(new GamePanel(frame));
		}
	}

	public void initAccueil() {
		
		
		int largeurBackground = 0, hauteurBackground = Display.getHeight() / 3;

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

		largeurBackground = 0;
		hauteurBackground = 0;

		while (hauteurBackground < Display.getHeight() / 3) {
			while (largeurBackground < Display.getWidth()) {
				OpenGlImage background = new OpenGlImage(loader, "Client/IHM/Images/wood1.jpg");
				background.setBounds(largeurBackground, hauteurBackground, 512 / 2, 512 / 2);
				largeurBackground += 512 / 2;
				gestionnaireComposant.addComponent(background);
			}
			hauteurBackground += 512 / 2;
			largeurBackground = 0;
		}

		OpenGlImage background = new OpenGlImage(loader, "Client/IHM/Images/joint.jpg");
		background.setBounds(largeurBackground, hauteurBackground, Display.getWidth(), 10);
		gestionnaireComposant.addComponent(background);
		
		

		OpenGlImage imageGrosseGauche = new OpenGlImage(loader, "Client/IHM/Images/Daronne/daronne_0_0.png");
		imageGrosseGauche.setBounds(20, Display.getHeight() / 4, 63 * 2, 99 * 2);
		gestionnaireComposant.addComponent(imageGrosseGauche);

		OpenGlImage imageGrosseDroite = new OpenGlImage(loader, "Client/IHM/Images/Daronne/daronne_1_0.png");
		imageGrosseDroite.setBounds(Display.getWidth() - 63 * 2 - 20, Display.getHeight() / 4, 63 * 2, 99 * 2);
		gestionnaireComposant.addComponent(imageGrosseDroite);

		OpenGlImage image = new OpenGlImage(loader, "Client/IHM/Images/fenetre.png");
		image.setBounds(Display.getWidth() / 4 - 32, Display.getHeight() / 5, 32 * 2, 32 * 2);
		gestionnaireComposant.addComponent(image);

		OpenGlImage image3 = new OpenGlImage(loader, "Client/IHM/Images/fenetre.png");
		image3.setBounds(Display.getWidth() / 4 * 3 - 32, Display.getHeight() / 5, 32 * 2, 32 * 2);
		gestionnaireComposant.addComponent(image3);

		OpenGlImage image2 = new OpenGlImage(loader, "Client/IHM/Images/tamer.png");
		image2.setBounds(Display.getWidth() / 2 - 750 / 8, Display.getHeight() / 100 * 35 - 750 / 4, 750 / 4, 868 / 4);
		gestionnaireComposant.addComponent(image2);

		OpenGlImage image4 = new OpenGlImage(loader, "Client/IHM/Images/Aspirateur.png");
		image4.setBounds(Display.getWidth() - 500 / 4, Display.getHeight() - 438 / 3, 500 / 4, 438 / 4);
		gestionnaireComposant.addComponent(image4);


		usernameLabel = new OpenGlLabel("nom d'utilisateur : ");
		usernameLabel.setBounds((Display.getWidth() / 3) - Display.getWidth(), Display.getHeight() / 10 * 5 + 12, 150, 50);
		gestionnaireComposant.addComponent(usernameLabel);

		usernameTextField = new OpenGlTextField();
		usernameTextField.setBounds((Display.getWidth() / 5 * 3 - 75) - Display.getWidth(), Display.getHeight() / 10 * 5, 150, 50);
		gestionnaireComposant.addComponent(usernameTextField);

		pwdLabel = new OpenGlLabel("Mot de passe : ");
		pwdLabel.setBounds((Display.getWidth() / 3) - Display.getWidth(), Display.getHeight() / 10 * 7 + 12, 150, 50);
		gestionnaireComposant.addComponent(pwdLabel);

		pseudoTextField = new OpenGlTextField();
		pseudoTextField.setBounds((Display.getWidth() / 100 * 50) + Display.getWidth(), Display.getHeight() / 10 * 7, 150, 50);
		gestionnaireComposant.addComponent(pseudoTextField);

		pwdTextField = new OpenGlTextField();
		pwdTextField.setBounds((Display.getWidth() / 5 * 3 - 75) - Display.getWidth(), Display.getHeight() / 10 * 7, 150, 50);
		gestionnaireComposant.addComponent(pwdTextField);

		connectionButton = new OpenGlButton("Client/IHM/Images/rouleau.png", "Client/IHM/Images/rouleau-jouer-hover.png");
		connectionButton.setBounds(Display.getWidth() / 2 - Display.getWidth() - 500 / 8, Display.getHeight() / 100 * 85, 500 / 4, 141 / 4);
		gestionnaireComposant.addComponent(connectionButton);

		pseudoLabel = new OpenGlLabel("Pseudo : ");
		pseudoLabel.setBounds((Display.getWidth() / 100 * 42) + Display.getWidth(), Display.getHeight() / 10 * 7 + 12, 150, 50);
		gestionnaireComposant.addComponent(pseudoLabel);

		start = new OpenGlButton("Client/IHM/Images/rouleau.png", "Client/IHM/Images/rouleau-jouer-hover.png");
		start.setBounds(Display.getWidth() / 2 - 500 / 8 + Display.getWidth(), Display.getHeight() / 100 * 85, 500 / 4, 141 / 4);
		gestionnaireComposant.addComponent(start);

		playButton = new OpenGlButton("Client/IHM/Images/planche.png", "Client/IHM/Images/planche_hover.png");
		playButton.setBounds(Display.getWidth() / 3 - 500 / 4 - 10, Display.getHeight() / 100 * 50, 500 / 2 + 20, 297 / 2);
		gestionnaireComposant.addComponent(playButton);	
		
		inscription = new OpenGlButton("Client/IHM/Images/Hachette.png", "Client/IHM/Images/Hachette_hover.png");
		inscription.setBounds(Display.getWidth() / 2 - 500/4, Display.getHeight()/10 * 8, 500/2, 151/2);
		gestionnaireComposant.addComponent(inscription);	
		
		playAsGuest = new OpenGlButton("Client/IHM/Images/Grille_pain.png", "Client/IHM/Images/Grille_pain_hover.png");
		playAsGuest.setBounds(Display.getWidth()*2/ 3 - 500 / 4 - 10, Display.getHeight() / 100 * 48, 500 / 2 + 20, 330 / 2);
		gestionnaireComposant.addComponent(playAsGuest);	
		
	}
	
	@Override
	public void displayComponent() {
		gestionnaireComposant.render();
		gestionnaireComposant.update();
		gestionnaireComposant.doAction();
	}

}
