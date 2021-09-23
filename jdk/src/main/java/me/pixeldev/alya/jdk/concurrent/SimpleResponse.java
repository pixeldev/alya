package me.pixeldev.alya.jdk.concurrent;

public class SimpleResponse<T>
		implements Response<T>{

	private final int status;
	private final T response;
	private final Exception error;

	private SimpleResponse(int status, T response, Exception error) {
		this.status = status;
		this.response = response;
		this.error = error;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public T getResponse() {
		return response;
	}

	@Override
	public Exception getError() {
		return error;
	}

	public static <T> Response<T> success(T response) {
		return new SimpleResponse<>(0, response, null);
	}

	public static <T> Response<T> error(Exception error) {
		return new SimpleResponse<>(1, null, error);
	}

}
