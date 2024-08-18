import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class test {

	public static void main(String[] args) throws IOException, SQLException {
//
//		String userDirectoryPath = System.getProperty("user.dir");
//
//		System.out.println("Current Directory = \"" + userDirectoryPath + "\"" );

		Properties prop = new Properties();
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.config");
		
        prop.load(inputStream);
		System.out.println(prop.getProperty("JDBC_URL"));
		System.out.println(prop.getProperty("JDBC_USER"));
		

		Connection connection = DriverManager.getConnection(prop.getProperty("JDBC_URL"), prop.getProperty("JDBC_USER"), prop.getProperty("JDBC_PASS"));
		System.out.println("Connected With the database successfully");
	}

}
