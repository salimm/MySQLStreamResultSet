package experiments;


import java.io.IOException;
import java.sql.SQLException;

/**
 * This experiment is using a query that joins to table using the primary key (indexed columns)
 * 
 * @author Salim
 *
 */
public class Experiment2 {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, IOException, InterruptedException {

		String query = "SELECT * FROM TABLE1 as t1, TABLE2 as t2 WHERE t1.id = t2.id  ";
		Experiments.exec(query, "out/experiment2.csv");
	}
}
