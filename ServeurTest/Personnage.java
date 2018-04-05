package ServeurTest;

import org.newdawn.slick.TrueTypeFont;

import Client.Util.CaracteristiqueJoueur;

public class Personnage {
	private static final String cheminImageDaronne = "Client/IHM/Images/Daronne/daronne_";
	private static final String cheminImageVieVide = "Client/IHM/Images/vieVide.png";
	private static final String cheminImageViePlein = "Client/IHM/Images/viePlein.png";
	
	static final int NB_POSITION = 4;
	static final int NB_SPRITE = 3;
	private CaracteristiqueJoueur caracteristique;
	
	private static double VITESSE = 2;

	private TrueTypeFont font;
	private String nom;
	private double x;
	private double y;

	private double xVector;
	private double yVector;

	private int nbSprite = 0;
	private int position = 0; // 0 droite, 1 gauche, 2 haut, 3 bas
	private float cumulDelta = 0;
	private double angle = 0;

	private Arme arme;

	public Personnage(String nom) {
		this.nom = nom;
		x = 2000;
		y = 2000;
		setArme();
		caracteristique = new CaracteristiqueJoueur();
	}
	
	public Personnage(String nom, double x, double y) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		setArme();
	}
	public Personnage(String nom, double x, double y, TrueTypeFont font) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.font = font;
		setArme();
	}
	
	public String getNom() {
		return nom;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setArme() {
		arme = new Arme(x, y);
	}

	public double getxVector() {
		return xVector;
	}

	public void setxVector(double xVector) {
		this.xVector = xVector;
	}

	public double getyVector() {
		return yVector;
	}

	public void setyVector(double yVector) {
		this.yVector = yVector;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Arme getArme() {
		return arme;
	}

	public void setArme(Arme arme) {
		this.arme = arme;
	}
	
	public CaracteristiqueJoueur getCaracteristique() {
		return caracteristique;
	}

	// swing
	public void setVecteur(int xSouris, int ySouris, int largeurMap, int hauteurMap, int largeurFenetre,
			int hauteurFenetre) {

		xVector = xSouris - (largeurFenetre / 2);
		yVector = ySouris - (hauteurFenetre / 2);

		double longueur = Math.sqrt(xVector * xVector + yVector * yVector);

		xVector /= longueur;
		yVector /= longueur;

	}
}
