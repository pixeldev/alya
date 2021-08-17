package me.pixeldev.alya.storage.sql.meta;

import me.pixeldev.alya.storage.sql.SQLModel;
import me.pixeldev.alya.storage.sql.connection.SQLProtocols;
import me.pixeldev.alya.storage.sql.identity.SQLElement;
import me.pixeldev.alya.storage.sql.identity.Table;
import me.pixeldev.alya.storage.sql.mysql.MySQLTable;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;

import java.util.ArrayList;
import java.util.List;

public class SQLModelMeta<T extends SQLModel> extends ModelMeta<T> {

	private final Table table;

	public SQLModelMeta(Class<T> type) {
		super(type);

		SQLTable sqlTable = type.getAnnotation(SQLTable.class);

		if (sqlTable == null) {
			throw new IllegalStateException(
					"SQL Model "
							+ type.getSimpleName()
							+ " doesn't have SQLTable annotation"
			);
		}

		List<SQLElement> elements = new ArrayList<>();

		for (SQLTable.Element element : sqlTable.elements()) {
			elements.add(SQLElement.mysqlElement(
					element.column(),
					element.type(),
					element.constraints()
			));
		}

		if (SQLProtocols.MYSQL.equals(sqlTable.protocol())) {
			table = new MySQLTable(sqlTable.name(), elements);
		} else {
			table = null;
			throw new IllegalStateException(
					"SQL Model "
							+ type.getSimpleName()
							+ " doesn't have a valid protocol."
			);
		}
	}

	public Table getTable() {
		return table;
	}

}