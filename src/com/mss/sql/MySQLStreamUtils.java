package com.mss.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLStreamUtils {

	public static ResultSet createResultSetOneByOne(Connection conn,String query)
			throws SQLException {
		Statement stmt = conn.createStatement(
				java.sql.ResultSet.TYPE_FORWARD_ONLY,
				java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(Integer.MIN_VALUE);
		
		return stmt.executeQuery(query);
	}

	public static StreamResultSet createResultSet(Connection conn,
			String query, int batchSize) throws SQLException {
		return new StreamResultSet(conn, query, batchSize);
	}

}
