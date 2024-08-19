
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBRetrieval {

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Properties prop = new Properties();

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("database.config");

	        prop.load(inputStream);
	        
	        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(prop.getProperty("JDBC_URL"), prop.getProperty("JDBC_USER"), prop.getProperty("JDBC_PASS"));
			System.out.println("Connected With the database successfully");
			
		} catch (SQLException e) {
			System.out.println("Error while connecting to the database");
			e.printStackTrace();
		} catch (IOException e) {
	        System.out.println("Error reading properties file");
	        e.printStackTrace();
		}
 		
		return connection;
	}
}
