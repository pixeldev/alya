package me.pixeldev.alya.jdk.http;

import me.pixeldev.alya.jdk.http.callback.ParseCallback;
import me.pixeldev.alya.jdk.http.config.HttpFactoryConfig;
import me.pixeldev.alya.jdk.http.retry.RetryPolicy;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class SimpleHttpClient implements HttpClient {

	private static final long MAX_SLEEP_TIME = TimeUnit.SECONDS.toMillis(30);

	private @Inject HttpFactoryConfig httpFactoryConfig;
	private @Inject RetryPolicy retryPolicy;

	@Override
	public <T> T executeRetrying(String url, ParseCallback<T> returnType, RequestOptions options, int maxRetries)
			throws HttpException, IOException {

		long totalSleepTime = 0;
		int retries = 0;
		HttpException lastError;

		do {
			try {
				return execute(url, returnType, options);
			} catch (HttpException e) {

				if (e.getStatusCode() >= HttpStatusCodes.BAD_REQUEST
						&& e.getStatusCode() < HttpStatusCodes.INTERNAL_SERVER_ERROR) {
					// don't retry on '4XX' errors
					throw e;
				}

				lastError = e;
				retries++;

				try {
					long sleepTime = retryPolicy.createGenerator().nextSleepTime();
					Thread.sleep(sleepTime);
					totalSleepTime += sleepTime;
				} catch (InterruptedException ignored) {
					Thread.currentThread().interrupt();
				}
			}
		} while (retries < maxRetries && totalSleepTime < MAX_SLEEP_TIME);

		throw lastError;
	}

	@Override
	public <T> T execute(String urlString, ParseCallback<T> callback, RequestOptions options)
			throws HttpException, IOException {

		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(httpFactoryConfig.getConnectTimeout());
		connection.setReadTimeout(httpFactoryConfig.getReadTimeout());

		connection.setRequestMethod(options.getMethod().name());
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("User-Agent", "unnamed-http-client");
		options.getHeaders().forEach(connection::setRequestProperty);

		String body = options.getBody();

		if (body != null && body.length() > 0) {
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			try (OutputStream output = connection.getOutputStream()) {
				byte[] data = body.getBytes(StandardCharsets.UTF_8);
				output.write(data, 0, data.length);
			}
		}

		return callback.call(connection, httpFactoryConfig.writeStackTraces());
	}

}