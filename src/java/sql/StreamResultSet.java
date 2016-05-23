package java.sql;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.StatementImpl;

public class StreamResultSet extends ResultSetImpl{

	public StreamResultSet(long updateCount, long updateID,
			MySQLConnection conn, StatementImpl creatorStmt) {
		super(updateCount, updateID, conn, creatorStmt);
	}

}
