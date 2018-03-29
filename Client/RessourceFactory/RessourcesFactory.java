package Client.RessourceFactory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static org.lwjgl.opengl.GL11.*;

public class RessourcesFactory {
	static int NB_POSITION = 4;
	static int NB_SPRITE = 3;
	
	private static String cheminImagePersonnage = "/Client/IHM/Images/personnage_tmp.png";
	private static String cheminImageDaronne = "/Client/IHM/Images/Daronne/daronne_";
	private static String cheminImageArme = "/Client/IHM/Images/arme.jpg";
	private static String cheminImageStat = "/Client/IHM/Images/stat.png";
	private static String cheminImageJouer = "/Client/IHM/Images/play.png";
	private static String cheminImageMerde = "/Client/IHM/Images/merde.png";
	
	private static int intTexturePersonnage;
	private static int intTextureArme;
	private static int intTextureStat;
	private static int intTextureJouer;
	private static Texture textureMerde;
	
	/*
	private static Texture texturePersonnage;
	private static Texture textureArme; */
	private static int[][] textureDaronne = new int[NB_POSITION][NB_SPRITE];
	private static Texture textureStat;
	

	public static void loadImage() {
		
		try {
			
			for(int i=0; i<NB_POSITION; i++) {
				for(int y=0; y<NB_SPRITE; y++) {
					String chemin = cheminImageDaronne + i + "_" + y + ".png";
					System.out.println(i + " " + y + " " + chemin);
					textureDaronne[i][y] = loadTexture(chemin);
				}
			}
			
			textureMerde = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(cheminImageMerde));
			
			//texturePersonnage = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(cheminImagePersonnage));
			//textureArme = TextureLoader.getTexture("JPG", Class.class.getResourceAsStream(cheminImageArme));
			textureStat = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(cheminImageStat));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		intTextureArme = loadTexture(cheminImageArme);
		//intTexturePersonnage = loadTexture(cheminImagePersonnage);
		intTextureStat = loadTexture(cheminImageStat);
		intTextureJouer = loadTexture(cheminImageJouer);

	}
	
	public static int loadTexture(String str) {
		
		int tex;
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(RessourcesFactory.class.getResource(str));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
		
		ByteBuffer b = BufferUtils.createByteBuffer(width*height*3);
		tex = glGenTextures();
		
		for(int i =0; i < pixels.length; i++) {
			byte rr = (byte)((pixels[i] >> 16) & 0xff);
			byte gg = (byte)((pixels[i] >> 8) & 0xff);
			byte bb = (byte)((pixels[i]) & 0xff);

			b.put(rr);
			b.put(gg);
			b.put(bb);
		}
		
		b.flip();
		glBindTexture(GL_TEXTURE_2D, tex);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, b);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
	}
	
	public static Texture getImage(TypeImage type) {
		
		switch(type) {
		/*case PERSONNAGE :
			return texturePersonnage;
		case ARME :
			return textureArme;*/
		case STAT:
			return textureStat;
		case MERDE:
			return textureMerde;
		default:
			break;
		}
		return null;
	}
	
	public static int getIntImage(TypeImage type) {

		switch(type) {
		case PERSONNAGE :
			return intTexturePersonnage;
		case ARME :
			return intTextureArme;
		case STAT:
			return intTextureStat;
		case JOUER:
			return intTextureJouer;
		default:
			break;
		}
		return -1;
	}

	public static int getImageDaronne(int nbSprite, int position) {
		return textureDaronne[position][nbSprite];
	}
}
