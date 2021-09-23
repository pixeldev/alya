package me.pixeldev.alya.jdk.concurrent;

import java.util.function.Consumer;

/**
 * Represents a response of any operation.
 * Will be used in {@link AsyncResponse} to
 * handle the response of the async operation.
 */
public interface Response<T> {

	/**
	 * @return Status of the operation.
	 * Will return 0 if was successful or
	 * 1 if there was an error.
	 */
	int getStatus();

	/**
	 * @return A nullable instance of the response.
	 */
	T getResponse();

	/**
	 * @return A nullable instance of the error.
	 */
	Exception getError();

	/**
	 * @return Will checks if the response has been successful.
	 */
	default boolean isSuccessful() {
		return getStatus() == 0;
	}

	/**
	 * Performs the action in the {@code consumer} only
	 * if the response was successful.
	 * @param consumer Action to perform.
	 */
	default void ifSuccessful(Consumer<? super T> consumer) {
		if (isSuccessful()) {
			consumer.accept(getResponse());
		}
	}

}
