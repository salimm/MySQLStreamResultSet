package experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mss.sql.MySQLStreamScanner;
import com.mss.sql.StreamResultSet;

public class Experiments {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/MYSQL_STREAM";

	// Database credentials
	static final String USER = "STREAM";
	static final String PASS = "STREAM123";

	public static void exec(String query,String outFileName) throws ClassNotFoundException,
			SQLException, IOException {

		// STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		// STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");

		int step = 10000;

		List<List<Long>> stats = new ArrayList<List<Long>>();
		stats.add(exp1(1, conn, query, step));
		stats.add(exp2(2, conn, query, step, 10));
		stats.add(exp2(3, conn, query, step, 100));
		stats.add(exp2(4, conn, query, step, 1000));
		stats.add(exp2(5, conn, query, step, 10000));

		saveResults(stats, step, outFileName);

	}

	private static void saveResults(List<List<Long>> stats, int step,String outFileName)
			throws IOException {
		String tmp = "";
		for (int i = 0; i < stats.size(); i++) {
			tmp += "Time Exp " + i + ",";
		}
		tmp = tmp.substring(0, tmp.length() - 1) + "\n";
		int index = 0;
		while (index < stats.get(0).size()) {
			for (List<Long> list : stats) {
				tmp += list.get(index) + ",";
			}
			tmp = tmp.substring(0, tmp.length() - 1) + "\n";
			index++;
		}
		FileWriter fw = new FileWriter(new File(outFileName));
		fw.write(tmp);
		fw.flush();
		fw.close();

	}

	/**
	 * This experiment is for one by one read from one table
	 * 
	 * @throws SQLException
	 */
	public static List<Long> exp1(int exp, Connection conn, String query,
			int step) throws SQLException {
		System.out.println("Starting expriment " + exp);
		ResultSet results = MySQLStreamScanner.createResultSetOneByOne(conn,
				query);
		List<Long> stats = new ArrayList<Long>();

		int count = 1;
		long t1 = System.currentTimeMillis();
		int round = 0;
		while (results.next() && count < 300000) {
			results.getDouble("VALUE");
			results.getString("TXT");
			count++;
			if (count % step == 0) {
				round++;
				long t2 = System.currentTimeMillis();
				long time = t2 - t1;
				System.out.println("round: " + round + " time: " + time);
				stats.add(time);
				t1 = System.currentTimeMillis();
			}
		}
		results.getStatement().close();
		return stats;
	}

	/**
	 * This experiment is for one by one read from one table
	 * 
	 * @throws SQLException
	 */
	public static List<Long> exp2(int exp, Connection conn, String query,
			int step, int batchSize) throws SQLException {
		System.out.println("Starting expriment " + exp);
		StreamResultSet results = MySQLStreamScanner.createResultSet(conn, query,
				batchSize);
		List<Long> stats = new ArrayList<Long>();

		int count = 1;
		long t1 = System.currentTimeMillis();
		int round = 0;
		while (results.next() && count < 300000) {
			results.getResultSet().getDouble("VALUE");
			results.getResultSet().getString("TXT");
			count++;
			if (count % step == 0) {
				round++;
				long t2 = System.currentTimeMillis();
				long time = t2 - t1;
				System.out.println("round: " + round + " time: " + time);
				stats.add(time);
				t1 = System.currentTimeMillis();
			}
		}

		return stats;
	}

}
