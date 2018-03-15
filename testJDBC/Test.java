package testJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

	Connection con;
	Statement stmt = null;
	
	private static String URL = "jdbc:mysql://mysql-sebenforce.alwaysdata.net:3306/sebenforce_tamereio";
    private static String LOGIN = "81312";
    private static String PASSWORD = "sebastien2";
    
    private final static String QUERY = "SELECT * FROM Country ";
    private final static String QUERY2 = "SELECT * FROM Success ";

	public Test() {
		try {
			con = getConnexion();
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }
	
	public List<String> find(String query) {

        List<String> eleves = new ArrayList<String>();

        try {
            final ResultSet rset = stmt.executeQuery(query);

            while (rset.next()) {
            		String eleve;
            		if(query.equals(QUERY))
            			eleve = rsetToEleve(rset);
            		else
            			eleve = rsetToEleve2(rset);
            			
                eleves.add(eleve);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {

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
        }*/

        return eleves;
    }

    private String rsetToEleve(final ResultSet rset) throws SQLException {
        /*final Integer id = rset.getInt("id");
        final String nom = rset.getString("nom");
        final String prenom = rset.getString("prenom");
        final Integer annee = rset.getInt("classe");
        final Date dateNaissance = rset.getDate("date_naissance");
        final String adresse = rset.getString("adresse");
        final String sexeStr = rset.getString("sexe");

        final Eleve eleve = new Eleve(id, nom, prenom, annee, sexe, dateNaissance, adresse);*/
        return rset.getString("countryName");
    }
    
    private String rsetToEleve2(final ResultSet rset) throws SQLException {
        return rset.getString("successLabel");
    }
    
	public static void main(String[] args) {
		Test test = new Test();
		List<String> liste = test.find(QUERY);
		
		for(String s : liste) {
			System.out.println("Valeur : " + s);
		}
		
		liste = test.find(QUERY2);
		
		for(String s : liste) {
			System.out.println("Valeur : " + s);
		}

	}

}
