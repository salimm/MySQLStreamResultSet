# MySQLStreamScanner

MySQLStreamScanner is a util library that support two different approches to streaming from a MySQL large tables. MySQLStreamScanner currently supports:

1. Reading rows of a SELECT query from MySQL database one by one 
2. Batch reading rows using LIMIT 


Using MySQLStreamScanner can be used to setup Statement instances or ResultSets to support both methods. To support the builtin MySQL method to stream rows one by one MSS configures the statement as suggested [here](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-implementation-notes.html). For the latter option, MSS provides a wrapper over a PreparedStatement that is very similar to a ResultSet but at the backend multiple queries are submitted to fetch subsequent batches/segments of the stream.

### Default MySQL Stream One by One

As suggested by MySQL JDBC documentations one can setup a Statement as shown below, which signals the server that results should be provided row by row:

```java
stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
              java.sql.ResultSet.CONCUR_READ_ONLY);
stmt.setFetchSize(Integer.MIN_VALUE);
```

In order to do this in MySQLStreamScanner you can use the following code:

```java
ResultSet results = MySQLStreamScanner.createResultSetOneByOne(conn,
				query);
```

Example implementations and experiments are implemented in experiments package. the static function Experiments.exp1 provides and example experiments that read 300,000 rows row by row using this method and logs the time per 10,000.

### Stream Rows in Batches Using LIMIT

In this method, MySQLStreamScanner provides a wrapper to ResultSet that reads results from MySQL in segments/batches and once rows in a batch are finished, it submits a new query to fetch the next batch in a seamless way. The StreamResultSet class maintains an instance of ResultSet known as currentResultSet that is substitued with a new ResultSet as soon as the rows in the current ResultSet instance are finished. As soon as the ResultSet.next() returns false, StreamResultSet.next() tries submitting a new query by advances the OFFSET of the LIMIT expression by the size of the batch and uses the new ResultSet if not empty.

In order to use this method one can use the following code. StreamResultSet is designed to minimze the changes to code needed to use this method. Experiments.exp2 provides and example implementation of Experiments.exp1 using this method

```java
StreamResultSet results = MySQLStreamScanner.createResultSet(conn, query,
				batchSize);
```

Fore more description and performance analysis please visit my blog post [here]()


