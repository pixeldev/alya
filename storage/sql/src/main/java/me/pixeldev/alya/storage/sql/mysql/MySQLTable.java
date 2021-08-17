package me.pixeldev.alya.storage.sql.mysql;

import me.pixeldev.alya.storage.sql.identity.SQLElement;
import me.pixeldev.alya.storage.sql.identity.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQLTable implements Table {

	private final String tableName;

	private String primaryColumn;

	private final String columns;
	private final String parameters;
	private final String declaration;

	public MySQLTable(String tableName,
										List<SQLElement> elements) {
		this.tableName = tableName;

		List<String> columnsBuilder = new ArrayList<>();
		List<String> parametersBuilder = new ArrayList<>();
		List<String> declarationBuilder = new ArrayList<>();

		for (SQLElement element : elements) {
			if (element.isPrimary()) {
				primaryColumn = element.getColumn();
			}

			columnsBuilder.add(element.getColumn());
			parametersBuilder.add(element.toParameter());
			declarationBuilder.add(element.toDeclaration());
		}

		columns = String.join(", ", columnsBuilder);
		parameters = String.join(", ", parametersBuilder);
		declaration = String.join(", ", declarationBuilder);
	}

	public MySQLTable(String tableName,
										SQLElement... elements) {
		this(tableName, Arrays.asList(elements));
	}

	@Override
	public String getName() {
		return tableName;
	}

	@Override
	public String getPrimaryColumn() {
		return primaryColumn;
	}

	@Override
	public String getColumns() {
		return columns;
	}

	@Override
	public String getParameters() {
		return parameters;
	}

	@Override
	public String getDeclaration() {
		return declaration;
	}

}