package java.sql;

/**
 * 
 * StreamResultSet used to stream mysql reads in segments
 * 
 * @author Salim
 *
 */
public class StreamResultSet {

	private String query;

	private int batchSize;

	private ResultSet currentResultSet;

	private PreparedStatement stmt;

	private StatementPreparer stmtPreparer;

	public StreamResultSet(Connection conn, String query, int batchSize)
			throws SQLException {
		this.setQuery(query);
		this.setBatchSize(batchSize);
		this.stmt = conn.prepareStatement(query.replace(";", "")
				+ " LIMIT ?, ?;");
		this.stmtPreparer = new StatementPreparer(stmt, batchSize);
		stmtPreparer.nextSegment();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public boolean next() throws SQLException {
		if (currentResultSet == null)
			return false;

		boolean flag = currentResultSet.next();
		if (!flag) {
			flag = readNextResultSet();
			if (!flag)
				currentResultSet = null;
		}
		return flag;
	}

	private boolean readNextResultSet() throws SQLException {
		currentResultSet = stmtPreparer.nextSegment().executeQuery();
		return currentResultSet.next();

	}

	public ResultSet getCurrentResultSet() {
		return currentResultSet;
	}

	public void setCurrentResultSet(ResultSet currentResultSet) {
		this.currentResultSet = currentResultSet;
	}

}

class StatementPreparer {

	private int offset = 1;
	private PreparedStatement stmt;
	private int batchSize;

	public StatementPreparer(PreparedStatement stmt, int batchSize) {
		this.stmt = stmt;
		this.batchSize = batchSize;
	}

	public PreparedStatement nextSegment() throws SQLException {
		offset += batchSize;
		stmt.setInt(1, offset);
		stmt.setInt(2, batchSize);
		return stmt;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}