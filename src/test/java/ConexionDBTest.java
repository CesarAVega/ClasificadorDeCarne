import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionDBTest {

	public void main(String[] argv) {

		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {
                        //String url = "jdbc:postgresql://ec2-54-235-177-62.compute-1.amazonaws.com:5432/d5eriguqd72a0?user=fjvuucnpabzyvf&password=sqnUkE88S8tz6rFRzZ8I4zbz8z&ssl=true";
			
                        connection = DriverManager.getConnection(
					"jdbc:postgresql://ec2-54-235-177-62.compute-1.amazonaws.com:5432/d5eriguqd72a0", 
                                "fjvuucnpabzyvf",
					"sqnUkE88S8tz6rFRzZ8I4zbz8z");
                                        
                        //connection = DriverManager.getConnection(url);
		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}