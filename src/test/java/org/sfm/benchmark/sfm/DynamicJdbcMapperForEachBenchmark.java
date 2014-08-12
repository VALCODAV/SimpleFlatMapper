package org.sfm.benchmark.sfm;

import java.sql.Connection;
import java.sql.SQLException;

import org.sfm.benchmark.QueryExecutor;
import org.sfm.jdbc.JdbcMapperFactory;

public class DynamicJdbcMapperForEachBenchmark<T> extends ForEachMapperQueryExecutor<T> implements QueryExecutor {
	public DynamicJdbcMapperForEachBenchmark(Connection conn, Class<T> target)
			throws NoSuchMethodException, SecurityException, SQLException {
		super(JdbcMapperFactory.newInstance().newMapper(target), conn, target);
	}
}
