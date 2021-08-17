package me.pixeldev.alya.storage.sql.identity;

public enum SQLConstraint {

	NOT_NULL("NOT NULL"),
	UNIQUE("UNIQUE"),
	PRIMARY("PRIMARY KEY")

	;

	private final String sql;

	SQLConstraint(String sql) {
		this.sql = sql;
	}

	public String toSql() {
		return sql;
	}

}