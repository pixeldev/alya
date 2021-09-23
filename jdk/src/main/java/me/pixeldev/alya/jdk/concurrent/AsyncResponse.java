package me.pixeldev.alya.jdk.concurrent;

import java.util.concurrent.CompletableFuture;

public interface AsyncResponse<T> {

	CompletableFuture<Response<T>> getFuture();

	void callback(Callback<Response<T>> callback);

}
