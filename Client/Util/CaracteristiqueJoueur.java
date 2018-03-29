package Client.Util;

import org.lwjgl.opengl.Display;

import Client.IHM.DisplayTaMere;
import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.Animator.PoolComponentAnimator;
import Client.IHM.OpenGlGraphics.Animator.TypeAnimation;
import Client.IHM.OpenGlGraphics.Components.OpenGlButton;
import Client.RessourceFactory.Sprite;

public class CaracteristiqueJoueur {
	private static final String cheminImageExpVide = "Client/IHM/Images/expVide.png";
	private static final String cheminImageExpPlein = "Client/IHM/Images/expPlein.png";
	private static final String cheminImageStatDegat = "Client/IHM/Images/stat/degat.png";
	private static final String cheminImageStatRecup = "Client/IHM/Images/stat/recup.png";
	private static final String cheminImageStatRegen = "Client/IHM/Images/stat/regen.png";
	private static final String cheminImageStatSante = "Client/IHM/Images/stat/sante.png";
	private static final String cheminImageStatSpeed = "Client/IHM/Images/stat/speed.png";
	private static final String cheminImageStatVitesseTir = "Client/IHM/Images/stat/vitesseTir.png";
	
	private Sprite expVide;
	private Sprite expPlein;
	private OpenGlButton statDegat;
	private OpenGlButton statRecup;
	private OpenGlButton statRegen;
	private OpenGlButton statSante;
	private OpenGlButton statSpeed;
	private OpenGlButton statVitesseTir;

	public int degat;
	public int recup;
	public int regen;
	public int sante;
	public int speed;
	public int vitesseTir;
	
	public int nbMerde;
	public int niveau;
	public boolean caractAffiche;
	
	public CaracteristiqueJoueur() {
		sante = 100;
		degat = 100;
		recup = 1;
		regen = 1;
		speed = 1;
		vitesseTir = 1;
		
		caractAffiche = false;
		niveau = 0;
		nbMerde = 0;
		
		expVide = new Sprite(DisplayTaMere.textureLoader, cheminImageExpVide); 
		expPlein = new Sprite(DisplayTaMere.textureLoader, cheminImageExpPlein); 
		statDegat = new OpenGlButton(cheminImageStatDegat);
		statDegat.setBounds(-300, Display.getHeight()-190, 290, 20);
		DisplayTaMere.gestionnaireComposant.addComponent(statDegat);
		
		statRecup = new OpenGlButton(cheminImageStatRecup);
		statRecup.setBounds(-300, Display.getHeight()-160, 290, 20);
		DisplayTaMere.gestionnaireComposant.addComponent(statRecup);
		
		statRegen = new OpenGlButton(cheminImageStatRegen);
		statRegen.setBounds(-300, Display.getHeight()-130, 290, 20);
		DisplayTaMere.gestionnaireComposant.addComponent(statRegen);
		
		statSante = new OpenGlButton(cheminImageStatSante);
		statSante.setBounds(-300, Display.getHeight()-100, 290, 20);
		DisplayTaMere.gestionnaireComposant.addComponent(statSante);
		
		statSpeed = new OpenGlButton(cheminImageStatSpeed);
		statSpeed.setBounds(-300, Display.getHeight()-70, 290, 20);
		DisplayTaMere.gestionnaireComposant.addComponent(statSpeed);
		
		statVitesseTir = new OpenGlButton(cheminImageStatVitesseTir);
		statVitesseTir.setBounds(-300, Display.getHeight()-40, 290, 20);
		DisplayTaMere.gestionnaireComposant.addComponent(statVitesseTir);
	}

	public int getNiveau() {
		return niveau;
	}
	
	public int getDegat() {
		return degat;
	}

	public void setDegat() {
		this.degat += 10;
		this.niveau--;
	}

	public int getRecup() {
		return recup;
	}

	public void setRecup() {
		this.recup++;
		this.niveau--;
	}

	public int getRegen() {
		return regen;
	}

	public void setRegen() {
		this.regen++;
		this.niveau--;
	}

	public int getSante() {
		return sante;
	}

	public void setSante() {
		this.sante += 10;
		this.niveau--;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed() {
		this.speed += 1;
		this.niveau--;
	}

	public int getVitesseTir() {
		return vitesseTir;
	}

	public void setVitesseTir() {
		this.vitesseTir += 1;
		this.niveau--;
	}
	
	public void addMerdeRamasse() {
		nbMerde++;
		if(nbMerde >= 2) {
			nbMerde = 0;
			niveau++;
		}
	}
	
	public OpenGlButton getStatDegat() {
		return statDegat;
	}

	public OpenGlButton getStatRecup() {
		return statRecup;
	}

	public OpenGlButton getStatRegen() {
		return statRegen;
	}

	public OpenGlButton getStatSante() {
		return statSante;
	}

	public OpenGlButton getStatSpeed() {
		return statSpeed;
	}

	public OpenGlButton getStatVitesseTir() {
		return statVitesseTir;
	}

	public void draw() {
		expVide.draw(15, Display.getHeight()-5-12, Display.getWidth() - 30, 5);
		int widthExp = nbMerde*(Display.getWidth() - 30)/10;
		expPlein.draw(15, Display.getHeight()-5-12, widthExp, 5);
		
		if(niveau >= 1 && caractAffiche == false) {
			PoolComponentAnimator animator = new PoolComponentAnimator(new Component[] {statDegat, statRecup, statRegen, statSante, statSpeed, statVitesseTir}, TypeAnimation.TRANSLATE, 310, 0, 1000);
			DisplayTaMere.gestionnaireComposant.addComponent(animator);
			nbMerde = 0;
			caractAffiche = true;
		}
	}
}
