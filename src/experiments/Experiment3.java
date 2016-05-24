package experiments;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This experiment with a joint query of one index column and one none index
 * column
 * 
 * @author Salim
 *
 */
public class Experiment3 {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, IOException {

		String query = "SELECT * FROM TABLE1 as t1, TABLE2 as t2 WHERE t1.id2 = t2.id  ";
		Experiments.exec(query);
	}
}
