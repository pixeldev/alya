package me.pixeldev.alya.storage.sql.identity;

import me.pixeldev.alya.storage.sql.mysql.MySQLElement;

import java.util.List;

public interface SQLElement {

	boolean isPrimary();

	boolean isNullable();

	boolean isUnique();

	String toParameter();

	String toDeclaration();

	String getColumn();

	DataType getType();

	List<SQLConstraint> getConstraints();

	static SQLElement mysqlElement(String column,
																 DataType type,
																 SQLConstraint... constraints) {
		return new MySQLElement(column, type, constraints);
	}

}