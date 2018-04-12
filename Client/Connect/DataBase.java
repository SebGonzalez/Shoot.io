package Client.Connect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Client.IHM.DisplayTaMere;
import Client.Util.Stats;

public class DataBase {

	Connection con;
	Statement stmt = null;

	private static String URL = "jdbc:mysql://mysql-sebenforce.alwaysdata.net:3306/sebenforce_tamereio";
	private static String LOGIN = "81312";
	private static String PASSWORD = "sebastien2";

	private final static String CONNECT = "SELECT * FROM User where userLogin='";
	private final static String SKIN = "SELECT * FROM Personalization where objectCategoryId=9 and userId=";
	private final static String WEAPON = "SELECT * FROM Personalization where objectCategoryId=10 and userId=";
	
	private final static String IDSTAT = "SELECT * FROM Stat where userID=";
	private final static String INSERT_STAT = "INSERT INTO Stat VALUES (";
	private final static String UPDATE_NBKILL = "UPDATE Stat SET StatNbKill=";
	private final static String UPDATE_BestLevel = "UPDATE Stat SET statBestLevel=";
	private final static String UPDATE_NBGAMES = "UPDATE Stat SET StatNbGames=";
	private final static String UPDATE_NBTHROWED = "UPDATE Stat SET StatNbThrowed=";
	private final static String UPDATE_NbTouched = "UPDATE Stat SET StatNbTouched=";

	private static final int SKIN_BASE = 9;
	private static final int WEAPON_BASE = 10;

	public boolean isConnected = false;
	public int userId;
	public String pseudo = "";
	public int skinId;
	public int weaponId;
	
	//stats
	public int statNbKill;
	public int statBestLevel;
	public int statNbGames;
	public int statNbThrowed;
	public int statNbTouched;

	public DataBase() {

	}

	public void getConnexion() {
		if (con == null) {
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
				stmt = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getSkinId() {
		if (isConnected)
			return skinId;
		else
			return SKIN_BASE;
	}

	public int getWeaponId() {
		if (isConnected)
			return weaponId;
		else
			return WEAPON_BASE;
	}

	public boolean connect(String pseudo, String mdp) {
		try {
			String requete = CONNECT + pseudo + "'";
			System.out.println(requete);
			final ResultSet rset = stmt.executeQuery(CONNECT + pseudo + "'");
			if (rset.next()) {
				String test = rset.getString("userPassword");
				System.out.println("Test : " + test);
				if (encodePassword(mdp).equals(test)) {
					System.out.println("OUIIII");
					userId = rset.getInt("userId");
					System.out.println("OUIIII " + userId);
					this.pseudo = pseudo;
					isConnected = true;
					System.out.println("OUIIII");
					if (!getSkin())
						return false;
					System.out.println("OUIIII");
					if (!getWeapon())
						return false;
					System.out.println("Connexion etablie avec la bdd");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private String encodePassword(String mdp) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(mdp.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("Mot de passe hashé : " + sb.toString());
		return sb.toString();
	}

	public boolean getSkin() {
		if (isConnected) {
			try {
				final ResultSet rset = stmt.executeQuery(SKIN + userId);
				if (rset.next()) {
					skinId = rset.getInt("objectId");
					System.out.println("Skin récupéré : " + skinId);
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean getWeapon() {
		if (isConnected) {
			try {
				final ResultSet rset = stmt.executeQuery(WEAPON + userId);
				if (rset.next()) {
					weaponId = rset.getInt("objectId");
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public boolean sendStats() {
		Stats s = DisplayTaMere.personnage.getStats();
		if (isConnected) {
			try {
				final ResultSet rset = stmt.executeQuery(IDSTAT + userId + " and weaponId=" + weaponId + "");
				if (rset.next()) {
					statNbKill = rset.getInt("StatNbKill");
					statBestLevel = rset.getInt("StatBestLevel");
					statNbGames = rset.getInt("statNbGames");
					statNbThrowed = rset.getInt("StatNbThrowed");
					statNbTouched = rset.getInt("StatNbTouched");
					
					statNbKill += s.nbKills;
					if(statBestLevel < s.lvl) statBestLevel = s.lvl;
					statNbGames++;
					statNbThrowed += s.nbThrows;
					statNbTouched += s.nbHits;
					
					stmt.executeUpdate(UPDATE_NBKILL + statNbKill + " WHERE userId=" + userId + " and weaponId=" + weaponId);
					stmt.executeUpdate(UPDATE_BestLevel + statBestLevel + " WHERE userId=" + userId + " and weaponId=" + weaponId);
					stmt.executeUpdate(UPDATE_NBGAMES + statNbGames + " WHERE userId=" + userId + " and weaponId=" + weaponId);
					stmt.executeUpdate(UPDATE_NBTHROWED + statNbThrowed + " WHERE userId=" + userId + " and weaponId=" + weaponId);
					stmt.executeUpdate(UPDATE_NbTouched + statNbTouched + " WHERE userId=" + userId + " and weaponId=" + weaponId);
				}
				else {
					stmt.executeUpdate(INSERT_STAT + s.nbKills + "," + s.lvl + ",1," + s.nbThrows + "," + s.nbHits + "," + userId + "," + weaponId + ")");
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
		
	}

	public void closeConnection() {
		if (stmt != null) {
			try {
				// Le stmt.close ferme automatiquement le rset
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		isConnected = false;
		pseudo = "";
	}
}
