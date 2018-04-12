package Client.Util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;

public class DrawTool {
	
	/**
	 * Dessine une ligne de la position {x1, y1} a la position {x2, y2} de la couleur donnee
	 * 
	 */
	public static void drawLine(Color color, int x1, int y1, int x2, int y2) {
		glBegin(GL_LINES);
		glColor3f(color.r, color.g, color.b);
		glVertex2i(x1, y1);
		glVertex2i(x2, y2);
		glEnd();
	}
	
	/**
	 * Dessine un triangle REMPLI de la couleur donnee ayant pour position d'angles {x1, y1}, {x2, y2} et {x3, y3} 
	 * 
	 * ex :
	 * 
	 * 	    x1,y1
	 *       / \
	 * 	    /   \
	 * 	   /     \
	 *    /       \
	 * x2,y2_____x3,y3
	 * 
	 */
	public static void drawFilledTriangle(Color color, int x1, int y1, int x2, int y2, int x3, int y3) {
		glBegin(GL_TRIANGLES);
		glColor3f(color.r, color.g, color.b);
		glVertex2i(x1, y1);
		glVertex2i(x2, y2);
		glVertex2i(x3, y3);
		glEnd();
	}
	
	
	/**
	 * Dessine un triangle VIDE de la couleur donnee ayant pour position d'angles {x1, y1}, {x2, y2} et {x3, y3} 
	 * 
	 * ex :
	 * 
	 * 	    x1,y1
	 *       / \
	 * 	    /   \
	 * 	   /     \
	 *    /       \
	 * x2,y2_____x3,y3
	 * 
	 */
	public static void drawTriangle(Color color, int x1, int y1, int x2, int y2, int x3, int y3) {
		glBegin(GL_LINES);
		glColor3f(color.r, color.g, color.b);
		
		glVertex2i(x1, y1);
		glVertex2i(x2, y2);

		glVertex2i(x2, y2);
		glVertex2i(x3, y3);

		glVertex2i(x3, y3);
		glVertex2i(x1, y1);
		
		glEnd();
	}
	
	/**
	 * 
	 * Dessine un quadrilatere REMPLI de la couleur donnee ayant pour position d'angles {x1, y1}, {x2, y2}, {x3, y3} et {x4, y4}
	 * 
	 * ex :
	 * 
	 * 	    x1,y1_____x4,y4
	 *       / 			|
	 * 	    /   		|
	 * 	   /   			|
	 *    /       		|
	 * x2,y2_________x3,y3
	 * 
	 * 
	 */
	public static void drawFilledQuad(Color color, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		glBegin(GL_QUADS);
		glColor3f(color.r, color.g, color.b);
		glVertex2i(x1, y1);
		glVertex2i(x2, y2);
		glVertex2i(x3, y3);
		glVertex2i(x4, y4);
		glEnd();
	}
	
	/**
	 * 
	 * Dessine un quadrilatere VIDE de la couleur donnee ayant pour position d'angles {x1, y1}, {x2, y2}, {x3, y3} et {x4, y4}
	 * 
	 * ex :
	 * 
	 * 	    x1,y1_____x4,y4
	 *       / 			|
	 * 	    /   		|
	 * 	   /   			|
	 *    /       		|
	 * x2,y2_________x3,y3
	 * 
	 * 
	 */
	public static void drawQuad(Color color, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		glBegin(GL_LINES);
		glColor3f(color.r, color.g, color.b);
		
		glVertex2i(x1, y1);
		glVertex2i(x2, y2);

		glVertex2i(x2, y2);
		glVertex2i(x3, y3);

		glVertex2i(x3, y3);
		glVertex2i(x4, y4);

		glVertex2i(x4, y4);
		glVertex2i(x1, y1);
		
		glEnd();
	}
	
	/**
	 * 
	 * Dessine un cercle REMPLI de la couleur donnee de centre {x1, y1} et de rayon radius
	 * 
	 */
	public static void drawFilledCircle(Color color, double x, double y, double radius){
		int i;
		int triangleAmount = 75;
		
		double twicePi = 2.0f * Math.PI;
		
		glBegin(GL_TRIANGLE_FAN);
		glColor3f(color.r, color.g, color.b);
			glVertex2d(x, y); 
			for(i = 0; i <= triangleAmount;i++) { 
				glVertex2d(
			            x + (radius * Math.cos(i *  twicePi / triangleAmount)), 
				    y + (radius * Math.sin(i * twicePi / triangleAmount))
				);
			}
		glEnd();
	}
	
	/**
	 * 
	 * Dessine un cercle VIDE de la couleur donnee de centre {x1, y1} et de rayon radius
	 */
	public static void drawCircle(Color color, double x, double y, double radius){
		int i;
		int lineAmount = 50; 
		
		double twicePi = 2.0f * Math.PI;
		
		glBegin(GL_LINE_LOOP);
		glColor3f(color.r, color.g, color.b);
			for(i = 0; i <= lineAmount;i++) { 
				glVertex2d(
				    x + (radius * Math.cos(i *  twicePi / lineAmount)), 
				    y + (radius* Math.sin(i * twicePi / lineAmount))
				);
			}
		glEnd();
	}
	
	/**
	 * Dessine un polygone REMPLI de la couleur donnee passant par tous les points donnes
	 */
	public static void drawFilledPolygone(Color color, Point... points) {
		glBegin(GL_POLYGON);
		glColor3f(color.r, color.g, color.b);
		for(Point p : points){
			glVertex2i(p.x, p.y);
		}
		glEnd();
	}
	
	/**
	 * Dessine un polygone VIDE de la couleur donnee passant par tous les points donnes
	 */
	public static void drawPolygone(Color color, Point... points) {
		glBegin(GL_LINES);
		glColor3f(color.r, color.g, color.b);
		Point lastP = points[0];
		
		for(int i = 1; i < points.length; i++) {
			glVertex2i(lastP.x, lastP.y);
			glVertex2i(points[i].x, points[i].y);
			lastP = points[i];
		}
		glVertex2i(lastP.x, lastP.y);
		glVertex2i(points[0].x, points[0].y);
		
		glEnd();
	}
	
}
