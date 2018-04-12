package Client.IHM;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;
import Client.IHM.OpenGlGraphics.Animator.PoolComponentAnimator;
import Client.IHM.OpenGlGraphics.Animator.PoolComponentMultiAnimator;
import Client.IHM.OpenGlGraphics.Animator.TypeAnimation;
import Client.IHM.OpenGlGraphics.Components.MiniMap;
import Client.IHM.OpenGlGraphics.Components.OpenGlFrame;
import Client.IHM.OpenGlGraphics.Components.OpenGlImage;
import Client.IHM.OpenGlGraphics.Components.OpenGlPanel;
import Client.RessourceFactory.TextureLoader;
import Client.Util.CaracteristiqueJoueur;
import Client.Util.GestionnaireAdversaire;
import Client.Util.GestionnaireMerde;
import Client.Util.Map;
import Client.Util.Personnage;

public class GamePanel implements ComponentListener, OpenGlPanel {
	
	private OpenGlFrame frame;
	public static GestionnaireComposant gestionnaireComposant;
	private TextureLoader loader;
	private GestionnaireMerde gestionnaireMerde;
	private GestionnaireAdversaire gestionnaireAdversaire;
	private Map map;
	private Personnage personnage;
	
	private static long lastFrame;

	int tempPasse;
	boolean bite = false;
	boolean changer = false;
	
	OpenGlImage imageStat1;
	OpenGlImage imageStat2;
	OpenGlImage imageStat3;
	
	public GamePanel(OpenGlFrame frame) {
		
		this.frame = frame;
		loader = DisplayTaMere.textureLoader;
		gestionnaireComposant = DisplayTaMere.gestionnaireComposant;
		gestionnaireMerde = DisplayTaMere.gestionnaireMerde;
		map = DisplayTaMere.map;
		personnage = DisplayTaMere.personnage;
		gestionnaireAdversaire = DisplayTaMere.gestionnaireAdversaire;
		OpenGlFrame.lastFrame = OpenGlFrame.getTime();
		
		MiniMap miniMap = new MiniMap();
		miniMap.setBounds(Display.getWidth()-300-Display.getWidth()/100*2, Display.getWidth()/100*2, 300, 300);
		gestionnaireComposant.addComponent(miniMap);
	}
	
	@Override
	public void paintComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayComponent() {
			double delta = OpenGlFrame.getDelta();
			tempPasse += delta;

			if (tempPasse > 2000 && !bite) {
				// PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {imageStat1, imageStat2, imageStat3}, TypeAnimation.TRANSLATE, 600, 0, 500);
				// gestionnaireComposant.addComponent(animator);
				bite = true;
				//PoolComponentMultiAnimator animator = new PoolComponentMultiAnimator(new Component[] { imageStat1, imageStat2, imageStat3 }, new TypeAnimation[] { TypeAnimation.TRANSLATE, TypeAnimation.TRANSLATE }, new Integer[] { 600, -600 }, new Integer[] { 0, 0 }, new Integer[] { 500, 500 }, new Integer[] { 500, 500 });
				//gestionnaireComposant.addComponent(animator);
				//animator.setLoop(true);
			}
			/*
			 * if(tempPasse > 4000 && bite) { PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {imageStat1, imageStat2, imageStat3}, TypeAnimation.TRANSLATE, -600, 0, 500); gestionnaireComposant.addComponent(animator); bite = false; tempPasse = 0; }
			 */

			map.drawMap(personnage);
			
			gestionnaireComposant.render();
			gestionnaireComposant.doAction();
			gestionnaireComposant.update();

			gestionnaireMerde.collision();
			gestionnaireMerde.drawMerde();
			
			gestionnaireAdversaire.updateAdversaire(delta);
			gestionnaireAdversaire.draw(personnage);

			personnage.drawPersonnage();

			personnage.updatePersonnage(Mouse.getX(), Mouse.getY(), map.getLargeur(), map.getLongueur(), delta);
			// System.out.println(gestionnaireComposant.getComponent().size() + " " + imageStat1.getX() + " " + imageStat1.getY());

			if (changer) {
				frame.setPanel(new AccueilPanel(frame));
			}
	}

	@Override
	public void actionPerformed(Component c) {
		CaracteristiqueJoueur ca = DisplayTaMere.personnage.getCaracteristique();
		if(c == ca.getStatDegat()) {
			ca.setDegat();
			if(ca.getNiveau() < 1) {
				PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {ca.getStatDegat(), ca.getStatRecup(), ca.getStatRegen(), ca.getStatSante(), ca.getStatSpeed(), ca.getStatVitesseTir()}, TypeAnimation.TRANSLATE, -310, 0, 1000);
				DisplayTaMere.gestionnaireComposant.addComponent(animator);
				ca.caractAffiche = false;
			}
		}
		else if(c == ca.getStatRecup()) {
			ca.setRecup();
			if(ca.getNiveau() < 1) {
				PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {ca.getStatDegat(), ca.getStatRecup(), ca.getStatRegen(), ca.getStatSante(), ca.getStatSpeed(), ca.getStatVitesseTir()}, TypeAnimation.TRANSLATE, -310, 0, 1000);
				DisplayTaMere.gestionnaireComposant.addComponent(animator);
				ca.caractAffiche = false;
			}
		}
		else if(c == ca.getStatRegen()) {
			ca.setRegen();
			if(ca.getNiveau() < 1) {
				PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {ca.getStatDegat(), ca.getStatRecup(), ca.getStatRegen(), ca.getStatSante(), ca.getStatSpeed(), ca.getStatVitesseTir()}, TypeAnimation.TRANSLATE, -310, 0, 1000);
				DisplayTaMere.gestionnaireComposant.addComponent(animator);
				ca.caractAffiche = false;
			}
		}
		else if(c == ca.getStatSante()) {
			ca.setSanteMax();
			if(ca.getNiveau() < 1) {
				PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {ca.getStatDegat(), ca.getStatRecup(), ca.getStatRegen(), ca.getStatSante(), ca.getStatSpeed(), ca.getStatVitesseTir()}, TypeAnimation.TRANSLATE, -310, 0, 1000);
				DisplayTaMere.gestionnaireComposant.addComponent(animator);
				ca.caractAffiche = false;
			}
		}
		else if(c == ca.getStatSpeed()) {
			ca.setSpeed();
			if(ca.getNiveau() < 1) {
				PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {ca.getStatDegat(), ca.getStatRecup(), ca.getStatRegen(), ca.getStatSante(), ca.getStatSpeed(), ca.getStatVitesseTir()}, TypeAnimation.TRANSLATE, -310, 0, 1000);
				DisplayTaMere.gestionnaireComposant.addComponent(animator);
				ca.caractAffiche = false;
			}
		}
		else if(c == ca.getStatVitesseTir()) {
			ca.setVitesseTir();
			if(ca.getNiveau() < 1) {
				PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {ca.getStatDegat(), ca.getStatRecup(), ca.getStatRegen(), ca.getStatSante(), ca.getStatSpeed(), ca.getStatVitesseTir()}, TypeAnimation.TRANSLATE, -310, 0, 1000);
				DisplayTaMere.gestionnaireComposant.addComponent(animator);
				ca.caractAffiche = false;
			}
		}
		
	}

}
