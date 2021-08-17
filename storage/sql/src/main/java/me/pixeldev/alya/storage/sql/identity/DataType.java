package me.pixeldev.alya.storage.sql.identity;

public enum DataType {

	TIMESTAMP("TIMESTAMP"),
	DATE("DATE"),
	BOOLEAN("TINYINT(1)"),
	TEXT("TEXT"),
	STRING("VARCHAR(100)"),
	NUMBER("INT"),
	EPOCH("INT(21)"),
	UUID("VARCHAR(36)")

	;

	private final String sql;

	DataType(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

}