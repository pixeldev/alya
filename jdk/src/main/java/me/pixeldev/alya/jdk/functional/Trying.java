package me.pixeldev.alya.jdk.functional;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class Trying<T> {

	private static final Object PRESENT = new Object();
	private static final Trying<?> SUCCESS = new Trying<>(PRESENT, null);
	private static final Trying<?> FAILURE = new Trying<>(null, PRESENT);

	private final Object value;
	private final Object failCause;

	private Trying(Object value, Object failCause) {
		this.value = value;
		this.failCause = failCause;
	}

	public Optional<T> getIfSuccess() {
		return Optional.ofNullable(getValueOrNull());
	}

	public Optional<Throwable> getIfFailure() {
		if (failCause instanceof Throwable) {
			return Optional.of((Throwable) failCause);
		} else {
			return Optional.empty();
		}
	}

	public boolean isSuccess() {
		return value != null;
	}

	public boolean isFailure() {
		return failCause != null;
	}

	public T orElse(T other) {
		return getIfSuccess().orElse(other);
	}

	@SuppressWarnings("unchecked")
	private T getValueOrNull() {
		Object valueOrNull = value == PRESENT ? null : value;
		if (valueOrNull == null) {
			return null;
		} else {
			return (T) valueOrNull;
		}
	}

	public Trying<? super T> orElse(Trying<? super T> other) {
		return isSuccess() ? this : other;
	}

	/**
	 * Executes the specified action if the response
	 * was completed exceptionally
	 * @param action The executed action
	 * @return The same response, for a fluent-api
	 * @see Trying#isFailure
	 */
	public Trying<T> ifFailure(Consumer<? super Throwable> action) {
		Optional<Throwable> cause = getIfFailure();

		if (isFailure()) {
			action.accept(cause.orElse(null));
		}

		return this;
	}

	/**
	 * Executes the specified action if the response
	 * was completed successfully
	 * @param action The executed action
	 * @return The same response, for a fluent-api
	 * @see Trying#isSuccess
	 */
	public Trying<T> ifSuccess(Consumer<? super T> action) {
		if (isSuccess()) {
			action.accept(getValueOrNull());
		}

		return this;
	}

	/**
	 * Executes immediately the specified supplier, catches
	 * any exception. If the supplier is executed successfully,
	 * returns a completed {@linkplain Trying}, using {@link Trying#success}
	 * passing the result of <code>supplier.get()</code>. If
	 * the supplier results in an {@link Exception} being
	 * thrown, the returned {@linkplain Trying} is created
	 * using {@link Trying#failure(Throwable)} passing the
	 * exception as the cause
	 * @param supplier The evaluated operation
	 * @param <T>      The expected operation return type
	 * @return The operation result
	 */
	public static <T> Trying<T> of(Callable<? extends T> supplier) {
		try {
			return success(supplier.call());
		} catch (Throwable throwable) {
			return failure(throwable);
		}
	}

	public static Trying<Void> ofAction(FailableRunnable action) {
		try {
			action.run();

			return success(null);
		} catch (Throwable throwable) {
			return failure(throwable);
		}
	}

	/**
	 * Returns a Trying that's immediately completed, if the
	 * specified value is null, casts {@link Trying#SUCCESS}
	 * to the expected operation return type. If the
	 * value isn't null, returns a new {@linkplain Trying}
	 * passing a null error cause and the specified value
	 * @param value The result value
	 * @param <T>   The expected return type of an operation
	 * @return The operation response
	 */
	@SuppressWarnings("unchecked")
	public static <T> Trying<T> success(T value) {
		return value == null ? (Trying<T>) SUCCESS : new Trying<>(value, null);
	}

	/**
	 * Returns a Trying that immediately fails, if the
	 * specified cause is null, casts {@link Trying#FAILURE}
	 * to the expected operation return type. If the
	 * exception isn't null, returns a new {@linkplain Trying}
	 * passing a null value and the specified cause
	 * @param cause The cause for the failed result
	 * @param <T>   The expected return type of an operation
	 * @return The operation response
	 */
	@SuppressWarnings("unchecked")
	public static <T> Trying<T> failure(Throwable cause) {
		return cause == null ? (Trying<T>) FAILURE : new Trying<>(null, cause);
	}

}