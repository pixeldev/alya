package me.pixeldev.alya.jdk.concurrent;

import java.util.concurrent.CompletableFuture;

public class SimpleAsyncResponse<T>
		implements AsyncResponse<T> {

	private final CompletableFuture<Response<T>> future;

	public SimpleAsyncResponse(CompletableFuture<Response<T>> future) {
		this.future = future;
	}

	@Override
	public CompletableFuture<Response<T>> getFuture() {
		return future;
	}

	@Override
	public void callback(Callback<Response<T>> callback) {
		future.whenComplete((response, error) -> {
			if (error == null) {
				callback.call(response);
			} else {
				callback.error(error);
			}
		});
	}

}
