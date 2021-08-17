package me.pixeldev.alya.jdk.http.callback;

import me.pixeldev.alya.jdk.http.HttpException;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface ParseCallback<T> {

	T call(HttpURLConnection connection, boolean stackTrace)
			throws HttpException, IOException;

}