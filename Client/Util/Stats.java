package Client.Util;

import java.lang.reflect.Constructor;

/**
 * 
 * Recueille les differentes stats possible chacune etant d'accesibilite publique
 * 
 * @author Ludovic GIBAULT
 *
 */
public class Stats {
	/**
	 * Nombre de tues
	 */
	public int nbKills;
	
	/**
	 * Nombre de lvl
	 */
	public int lvl;
	
	/**
	 * Nombre de lancers
	 */
	public int nbThrows;
	
	/**
	 * Nombre de touchers
	 */
	public int nbHits;
	
	/**
	 * Initialise toutes les stats a 0
	 */
	public Stats() {
		nbKills = 0;
		lvl = 0;
		nbThrows = 0;
		nbHits = 0;
	}
}
