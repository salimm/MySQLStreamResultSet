package experiments;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This experiment is a simple select from one table
 * 
 * @author Salim
 *
 */
public class Experiment1 {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, IOException, InterruptedException {

		String query = "SELECT * FROM TABLE1";
		Experiments.exec(query, "out/experiment1.csv");
	}
}
