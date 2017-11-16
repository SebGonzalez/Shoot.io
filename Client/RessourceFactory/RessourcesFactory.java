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
	
	private static String imagePersonnage = "/Client/IHM/Images/personnage.png";
	
	private static Texture imagePersonnageB;
	private static BufferedImage imagePersonnageDecoupeB[][] = new BufferedImage[NB_POSITION][NB_SPRITE];
	
	public static void loadImage() {
		try {
			imagePersonnageB = TextureLoader.getTexture("PNG", Class.class.getResourceAsStream(imagePersonnage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//decoupePersonnage();
	}
	
	/*public static void decoupePersonnage() {
		int width = imagePersonnageB.getWidth();
		int height = imagePersonnageB.getHeight();
		
		for(int i=0; i<imagePersonnageDecoupeB.length; i++) {
			for(int y=0; y<imagePersonnageDecoupeB[0].length; y++) {
				imagePersonnageDecoupeB[i][y] = imagePersonnageB.getSubimage(i*(width/NB_SPRITE), y*(height/NB_POSITION), (width/NB_SPRITE), (height/NB_POSITION));
			}
		}
	}*/
	
	public static Image getImagePersonnage(int nbSprite, int position) {
		return imagePersonnageDecoupeB[position][nbSprite];
	}
	
	public static Texture getImage(TypeImage type) {
		switch(type) {
		case PERSONNAGE :
			return imagePersonnageB;
		}
		return null;
	}
	
}
