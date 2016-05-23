package java.sql;

public class MySQLStreamUtils {

	public Statement createStatementOneByOne(Connection conn)
			throws SQLException {
		Statement stmt = conn.createStatement(
				java.sql.ResultSet.TYPE_FORWARD_ONLY,
				java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchDirection(Integer.MIN_VALUE);
		return stmt;
	}

}
