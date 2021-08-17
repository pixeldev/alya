package me.pixeldev.alya.storage.sql.identity;

public interface Table {

	String getName();

	String getPrimaryColumn();

	String getColumns();

	String getParameters();

	String getDeclaration();

}