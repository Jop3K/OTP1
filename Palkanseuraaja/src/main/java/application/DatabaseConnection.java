package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private final String URL = "jdbc:mariadb://localhost:2250/tietokanta";
	private final String USERNAME = "root";
	private final String PASSWORD= "palkanseuraaja";

	private Connection connection = null;


	public DatabaseConnection() {

		try {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		//connection = DriverManager.getConnection("jdbc:mariadb://localhost:2250/tietokanta?user=root&password=palkanseuraaja");
	
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			System.exit(1);
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	public  void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
