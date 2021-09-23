package me.pixeldev.alya.storage.sql;

import me.pixeldev.alya.jdk.functional.FailableConsumer;
import me.pixeldev.alya.storage.sql.connection.SQLClient;
import me.pixeldev.alya.storage.sql.identity.SQLMapSerializer;
import me.pixeldev.alya.storage.sql.identity.Table;
import me.pixeldev.alya.storage.sql.meta.SQLModelMeta;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;
import me.pixeldev.alya.storage.universal.service.type.AbstractRemoteModelService;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class SQLModelService<T extends SQLModel> extends AbstractRemoteModelService<T> {

	private final SQLClient client;
	private final Jdbi connection;
	private final RowMapper<T> rowMapper;
	private final SQLMapSerializer<T> mapSerializer;
	private final Table table;

	public SQLModelService(ModelMeta<T> modelMeta,
												 SQLClient client,
												 RowMapper<T> rowMapper,
												 SQLMapSerializer<T> mapSerializer) {
		super(modelMeta);
		this.client = client;
		this.rowMapper = rowMapper;
		connection = client.getConnection();
		this.table = ((SQLModelMeta<T>) modelMeta).getTable();
		this.mapSerializer = mapSerializer;

		try (Handle handle = connection.open()) {
			handle.execute(
					"CREATE TABLE IF NOT EXISTS " + table.getName() + "(" + table.getDeclaration() + ")"
			);
		}
	}

	@Override
	protected T internalFind(String id) {
		try (Handle handle = connection.open()) {
			return handle.select("SELECT * FROM <TABLE> WHERE <COLUMN> = :n")
					.define("TABLE", table.getName())
					.define("COLUMN", table.getPrimaryColumn())
					.bind("n", id)
					.map(rowMapper)
					.findFirst()
					.orElse(null);
		}
	}

	@Override
	protected boolean internalDelete(String id) {
		try (Handle handle = connection.open()) {
			return handle.createUpdate("DELETE FROM <TABLE> WHERE <COLUMN> = :n")
					.define("TABLE", table.getName())
					.define("COLUMN", table.getPrimaryColumn())
					.bind("n", id)
					.execute() > 0;
		}
	}

	@Override
	protected List<T> internalFindAll(FailableConsumer<T> consumer) throws Exception {
		try (Handle handle = connection.open()) {
			List<T> models = new ArrayList<>();

			for (T model : handle.select("SELECT * FROM <TABLE>")
					.define("TABLE", table.getName())
					.define("COLUMN", table.getPrimaryColumn())
					.map(rowMapper)
			) {
				consumer.accept(model);
				models.add(model);
			}

			return models;
		}
	}

	@Override
	protected void internalUpload(T model) {
		try (Handle handle = connection.open()) {
			handle.createUpdate("REPLACE INTO <TABLE> (<COLUMNS>) VALUES (<VALUES>)")
					.define("TABLE", table.getName())
					.define("COLUMNS", table.getColumns())
					.define("VALUES", table.getParameters())
					.bindMap(mapSerializer.serialize(model))
					.execute();
		}
	}

	public SQLClient getClient() {
		return client;
	}

}
