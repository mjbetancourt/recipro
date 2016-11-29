import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javafx.collections.*;

// NOTE ResultTable is deprecated; can now be dropped from DB.

/**
 * Created by Robert Russell
 *
 */
public class Connect {

	private static final String Server = "68.0.192.250";
	private static final String port = "53978";
	private static final String user = "bahama";
	private static final String password = "recipro";
	private static final String database = "ReciProDB";

	private static Connection connection;
	private static Statement statement;
	private static ResultSet set;

	//Creates the connection string required to connect to the DB
	private static String jdbcurl = "jdbc:sqlserver://" + Server + ":" + port + ";databaseName=" + database + ";user=" + user
			+ ";password=" + password;

	/**
	 * Returns an ObservableList containing all recipes in the database.
	 * @throws SQLException
	 */
	public static ObservableList<Recipe> getAll() throws SQLException {
		ObservableList<Recipe> results = FXCollections.observableArrayList();

		try {
			connector();

			final String SQL = "SELECT * FROM MasterTable";
			statement = connection.createStatement();
			set = statement.executeQuery(SQL);

			while (set.next()) {
				// NOTE How do we retrieve also the record's index in the DB?
				Recipe r = new Recipe(set.getString(1), set.getString(3));
				results.addAll(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * Returns an ObservableList containing recipes from the database
	 * that are like given keywords in a String.
	 * @param s
	 * @throws SQLException
	 */
	public static ObservableList<Recipe> getByKeyword(String s) throws SQLException {
		ObservableList<Recipe> results = FXCollections.observableArrayList();

		final ArrayList<String> keywords = new ArrayList<>();
        for (String keyword : s.split(" ")) {
        	keywords.add(keyword);
        }

		try {
			connector();

			String SQL = "SELECT DISTINCT * FROM MasterTable WHERE ";

			for (int i = 0; i < keywords.size(); i++) {
				if (i == 0) {
					SQL += "(dishName LIKE '%" + keywords.get(i) + "%' OR ingredients LIKE '%"
						+ keywords.get(i) + "%' OR recipe LIKE '%" + keywords.get(i) + "%')";
				} else {
					SQL += "OR (dishName LIKE '%" + keywords.get(i) + "%' OR ingredients LIKE'%"
						+ keywords.get(i) + "%' OR recipe LIKE '%" + keywords.get(i) + "%')";
				}
			}

			statement = connection.createStatement();
			set = statement.executeQuery(SQL);

			while (set.next() != false) {
				Recipe r = new Recipe(set.getString(1), set.getString(3));
				results.addAll(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * This method will connect the user to the database
	 */
	private static void connector() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(jdbcurl);
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
	public static void add(String name, String ingredient, String recipe) throws SQLException {
		try {
			connector();

			final String SQL = "INSERT INTO MasterTable VALUES('"+name+"', '"+ingredient+"', '"+recipe+"')";
			statement = connection.createStatement();
			statement.executeUpdate(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates an existing recipe by index.
	 * @param index
	 * @param name
	 * @param ingredient
	 * @param recipe
	 * @throws SQLException
	 */
	public static void update(int index, String name, String ingredient, String recipe) throws SQLException {
		try {
			connector();

			final String SQL = "UPDATE MasterTable SET dishName='"+name+"', recipe='"+recipe+"' WHERE dishName='"+name+"';"; // NOTE Complete the statement.
			statement = connection.createStatement();
			statement.executeUpdate(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
