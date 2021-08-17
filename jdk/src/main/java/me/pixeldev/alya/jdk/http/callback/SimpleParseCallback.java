package me.pixeldev.alya.jdk.http.callback;

import me.pixeldev.alya.jdk.http.HttpException;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class SimpleParseCallback<T> implements ParseCallback<T> {

	private final Class<T> type;
	private final HttpCallbackMapper<T> mapper;

	public SimpleParseCallback(Class<T> type, HttpCallbackMapper<T> mapper) {
		this.type = type;
		this.mapper = mapper;
	}

	private String readAsString(InputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
			int character;
			while ((character = reader.read()) != -1) {
				builder.append((char) character);
			}
		}
		return builder.toString();
	}

	@Override
	public T call(HttpURLConnection connection, boolean writeStackTrace)
			throws IOException, HttpException {
		try {
			InputStream responseStream = connection.getInputStream();
			int status = connection.getResponseCode();
			if (status == 200) {
				try (InputStreamReader reader = new InputStreamReader(responseStream)) {
					if (type == String.class) {
						@SuppressWarnings("unchecked")
						T value = (T) readAsString(responseStream);
						return value;
					} else {
						return mapper.map(reader, type);
					}
				}
			}
		} catch (IOException ignored) { }

		InputStream errorStream = connection.getErrorStream();
		System.out.println(errorStream);
		throw new HttpException(
				readAsString(errorStream),
				connection.getResponseCode(),
				writeStackTrace
		);
	}

}