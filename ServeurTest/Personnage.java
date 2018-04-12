package ServeurTest;

import org.newdawn.slick.TrueTypeFont;

import Client.Util.CaracteristiqueJoueur;

public class Personnage {

	private CaracteristiqueJoueur caracteristique;
	
	private String nom;
	private double x;
	private double y;

	private double xVector;
	private double yVector;

	private int nbSprite = 0;
	private int position = 0; // 0 droite, 1 gauche, 2 haut, 3 bas
	private double angle = 0;
	
	private int idSkin;
	private int idWeapon;

	private Arme arme;

	public Personnage(String nom) {
		this.nom = nom;
		x = 2000;
		y = 2000;
		setArme();
		caracteristique = new CaracteristiqueJoueur(false);
	}
	
	public Personnage(String nom, double x, double y, int idSkin, int idWeapon) {
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.idSkin = idSkin;
		this.idWeapon = idWeapon;
		setArme();
		caracteristique = new CaracteristiqueJoueur(false);
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
	
	public int getIdSkin() {
		return idSkin;
	}

	public void setIdSkin(int idSkin) {
		this.idSkin = idSkin;
	}

	public int getIdWeapon() {
		return idWeapon;
	}

	public void setIdWeapon(int idWeapon) {
		this.idWeapon = idWeapon;
	}

	public CaracteristiqueJoueur getCaracteristique() {
		return caracteristique;
	}
}