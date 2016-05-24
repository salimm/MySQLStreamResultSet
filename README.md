# MySQLStreamScanner

MySQLStreamScanner is a util library that support two different approches to streaming from a MySQL large tables. MySQLStreamScanner currently supports:

1. Reading rows of a SELECT query from MySQL database one by one 
2. Batch reading rows using LIMIT 


Using MySQLStreamScanner can be used to setup Statement instances or ResultSets to support both methods. To support the builtin MySQL method to stream rows one by one MSS configures the statement as suggested [here](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-implementation-notes.html). For the latter option, MSS provides a wrapper over a PreparedStatement that is very similar to a ResultSet but at the backend multiple queries are submitted to fetch subsequent batches/segments of the stream.

## Default MySQL Stream One by One
