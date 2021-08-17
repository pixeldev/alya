package me.pixeldev.alya.jdk.http;

import me.pixeldev.alya.jdk.http.callback.ParseCallback;

import java.io.IOException;

public interface HttpClient {

	<T> T executeRetrying(
			String url, ParseCallback<T> returnType,
			RequestOptions options, int maxRetries
	) throws HttpException, IOException;

	<T> T execute(
			String url, ParseCallback<T> returnType,
			RequestOptions options
	) throws HttpException, IOException;

}