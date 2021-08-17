package me.pixeldev.alya.jdk.concurrent.observer;

import me.pixeldev.alya.jdk.functional.FailableRunnable;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Observables {

	private static final Function<Throwable, Object> COMMON_ERROR_HANDLER = throwable -> {
		throwable.printStackTrace();

		return null;
	};

	private Observables() {
		throw new UnsupportedOperationException("This class cannot be initialized.");
	}

	public static <T> Observable<T> safeObservable(Callable<T> callable,
																								 Function<Throwable, T> errorHandler) {
		return new Observable<>(() -> {
			try {
				return callable.call();
			} catch (Exception e) {
				return errorHandler.apply(e);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> Observable<T> safeObservable(Callable<T> callable) {
		return new Observable<>(() -> {
			try {
				return callable.call();
			} catch (Exception e) {
				return (T) COMMON_ERROR_HANDLER.apply(e);
			}
		});
	}

	public static Observable<Void> safeObservable(FailableRunnable consumer) {
		return new Observable<>(() -> {
			try {
				consumer.run();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		});
	}

	public static Observable<Void> safeObservable(FailableRunnable consumer,
																								Consumer<Throwable> errorHandler) {
		return new Observable<>(() -> {
			try {
				consumer.run();
			} catch (Exception e) {
				errorHandler.accept(e);
			}

			return null;
		});
	}

}