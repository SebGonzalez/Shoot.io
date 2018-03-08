package Client.RessourceFactory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class RessourcesFactory {
	static int NB_POSITION = 4;
	static int NB_SPRITE = 3;
	
	private static String cheminImagePersonnage = "/Client/IHM/Images/personnage.png";
	private static String cheminImageDaronne = "/Client/IHM/Images/Daronne/daronne_";
	private static String cheminImageArme = "/Client/IHM/Images/arme.jpg";
	private static String cheminImageStat = "/Client/IHM/Images/stat.png";
	private static String cheminImageMerde = "/Client/IHM/Images/merde.png";
	
	private static Texture texturePersonnage;
	private static Texture[][] textureDaronne = new Texture[NB_POSITION][NB_SPRITE];
	private static Texture textureArme;
	private static Texture textureStat;
	private static Texture textureMerde;

	public static void loadImage() {
		try {
			
			for(int i=0; i<NB_POSITION; i++) {
				for(int y=0; y<NB_SPRITE; y++) {
					String chemin = cheminImageDaronne + i + "_" + y + ".png";
					System.out.println(i + " " + y + " " + chemin);
					textureDaronne[i][y] = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(chemin));
				}
			}
			texturePersonnage = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(cheminImagePersonnage));
			textureArme = TextureLoader.getTexture("JPG", Class.class.getResourceAsStream(cheminImageArme));
			textureStat = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(cheminImageStat));
			textureMerde = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(cheminImageMerde));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Texture getImage(TypeImage type) {
		switch(type) {
		case PERSONNAGE :
			return texturePersonnage;
		case ARME :
			return textureArme;
		case STAT:
			return textureStat;
		case MERDE:
			return textureMerde;
		default:
			break;
		}
		return null;
	}
	
	public static Texture getImageDaronne(int nbSprite, int position) {
		return textureDaronne[position][nbSprite];
	}
	
}
