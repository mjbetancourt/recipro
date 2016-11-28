import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javafx.collections.*;

/**
 * Created by Robert Russell
 *
 */
public class Connect {

	private final String Server = "68.0.192.250";
	private final String port = "53978";
	private final String user = "bahama";
	private final String password = "recipro";
	private final String database = "ReciProDB";

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private ArrayList<String> keywords = new ArrayList<>();

	//Creates the connection string required to connect to the DB
	private String jdbcurl = "jdbc:sqlserver://" + Server + ":" + port + ";databaseName=" + database + ";user=" + user
			+ ";password=" + password;

	/**
	 * Default constructor
	 */
	public Connect() {}

	/**
	 * Accepts an ArrayList of keywords and initiates a search.
	 */
	public Connect(ArrayList<String> keywords) throws ClassNotFoundException, SQLException {
		this.keywords = keywords;

		try {
			for (String keyword : keywords) {
				query(keyword);
				getResults();
			}

			clearTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will call the connector() Method
	 * Then it will execute a SQL query looking for similar matches based on what the user entered into
	 * the search field.
	 * If user inputs nothing and hits search everything will be a match
	 * Every match will then get inserted into a results table
	 * @param s
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void query(String s) throws ClassNotFoundException, SQLException {
		try {
			connector();

			String SQL = "SELECT DISTINCT * from MasterTable WHERE (dishName LIKE '%"+s+"%'"+
					"OR ingredients LIKE '%"+s+"%')";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				SQL = "INSERT INTO ResultTable VALUES ('"+rs.getString(1)+"', '"+rs.getString(2)+"', '"+rs.getString(3)+"');";
				con.createStatement();
				stmt.executeUpdate(SQL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will call the connector() method
	 * Then it will retrieve all unique/distinct results from the results table and returns them to the user
	 * This will prevent the user from getting duplicate results
	 * Then it will call the clearTable() method
	 * @throws SQLException
	 */
	public ObservableList<String> getResults() throws SQLException {
		ObservableList<String> results = FXCollections.observableArrayList();

		try {
			connector();

			final String SQL = "SELECT DISTINCT * FROM ResultTable";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				//System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));

				results.addAll(rs.getString(1));
			}

			clearTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * This method will call the connector() method
	 * Then it will execute an update to the result table deleting all the results
	 * but leaving the structure of the table intact
	 * This will prevent our results table from becoming too big
	 * @throws SQLException
	 */
	private void clearTable() throws SQLException {
		connector();

		final String SQL = "DELETE FROM ResultTable";
		stmt = con.createStatement();
		stmt.executeUpdate(SQL);
	}

	/**
	 * This method will connect the user to the database
	 */
	private void connector() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(jdbcurl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method will call the connector method
	 * Then it will take the parameters and make a new entry
	 * into the database for a new recipe
	 * @param name
	 * @param ingredient
	 * @param recipe
	 * @throws SQLException
	 */
	public void addRecipe(String name, String ingredient, String recipe) throws SQLException {
		connector();

		final String SQL = "INSERT INTO MasterTable VALUES('"+name+"', '"+ingredient+"', '"+recipe+"')";
		stmt = con.createStatement();
		stmt.executeUpdate(SQL);
	}
}
