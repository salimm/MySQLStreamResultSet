package experiments;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ExperimentDataGenerator {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/MYSQL_STREAM";

	// Database credentials
	static final String USER = "STREAM";
	static final String PASS = "STREAM123";

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		// STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		// STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");

		Statement stmt = conn.createStatement();
		stmt.executeUpdate("TRUNCATE TABLE Table1");

		stmt.executeUpdate("TRUNCATE TABLE Table2");

		System.out.println("trucnated tables....");

		Random rnd = new Random();
		for (int i = 0; i < 10000000; i++) {
			String txt = randomString(45);
			double val = rnd.nextDouble();
			stmt.executeUpdate("INSERT INTO TABLE1(TXT,VALUE) VALUES('" + txt
					+ "'," + val + ")");
			if (i % 10000 == 0)
				System.out.println(i);
		}

		for (int i = 0; i < 10000000; i++) {
			double val = rnd.nextDouble();
			double val2 = rnd.nextDouble();
			stmt.executeUpdate("INSERT INTO TABLE2(VALUE1,VALUE2) VALUES("
					+ val + "," + val2 + ")");
			if (i % 10000 == 0)
				System.out.println(i);
		}
	}

	static SecureRandom rnd = new SecureRandom();

	public static String randomString(int len) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
