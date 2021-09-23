package me.pixeldev.alya.jdk.concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface Callback<T> {

	void call(T object);

	default void error(Throwable error) {
		Logger.getGlobal().log(
				Level.SEVERE,
				"Error executing callback.",
				error
		);
	}

}
