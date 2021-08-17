package me.pixeldev.alya.storage.sql.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.jdbi.v3.core.Jdbi;

public class SQLClient {

	private final Jdbi connection;

	private SQLClient(HikariDataSource dataSource) {
		connection = Jdbi.create(dataSource);
	}

	public Jdbi getConnection() {
		return connection;
	}

	public static class Builder {

		private static final String JDBC_FORMAT = "jdbc:%s://%s:%s/%s";

		private final HikariConfig hikariConfig;
		private	final String sqlProtocol;
		private String ip;
		private int port;
		private String database;
		private int maximumPoolSize = 6;

		public Builder(String sqlProtocol) {
			this.sqlProtocol = sqlProtocol;
			hikariConfig = new HikariConfig();
		}

		public Builder setIp(String ip) {
			this.ip = ip;
			return this;
		}

		public Builder setPort(int port) {
			this.port = port;
			return this;
		}

		public Builder setDatabase(String database) {
			this.database = database;
			return this;
		}

		public Builder setUser(String user) {
			hikariConfig.setUsername(user);
			return this;
		}

		public Builder setPassword(String password) {
			hikariConfig.setPassword(password);
			return this;
		}

		public Builder setMaximumPoolSize(int maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
			return this;
		}

		public SQLClient build() {
			hikariConfig.setJdbcUrl(String.format(
					JDBC_FORMAT,
					sqlProtocol, ip,
					port, database
			));
			hikariConfig.setMaximumPoolSize(maximumPoolSize);

			return new SQLClient(new HikariDataSource(hikariConfig));
		}

	}

}