package me.pixeldev.alya.jdk.concurrent.observer;

import me.pixeldev.alya.jdk.concurrent.AsyncExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

public class Observable<T> {

	private static final Observable<?> EMPTY_OBSERVABLE = new Observable<>(() -> null);

	private final Supplier<T> supplier;
	private final List<Subscription<T>> subscriptions;

	public Observable(Supplier<T> supplier) {
		this.supplier = supplier;
		subscriptions = new ArrayList<>();
	}

	public Supplier<T> getSupplier() {
		return supplier;
	}

	public List<Subscription<T>> getSubscriptions() {
		return subscriptions;
	}

	public Observable<T> subscribe(Observer<T> observer) {
		Subscription<T> subscription = new Subscription<>(this, observer);

		for (Subscription<T> registeredSubscription : subscriptions) {
			registeredSubscription.getObserver().onSubscribe(subscription);
		}

		subscriptions.add(subscription);

		return this;
	}

	public <R> Observable<R> pipe(Function<Pipe<T>, Pipe<R>> function) {
		return function.apply(new Pipe<>(supplier)).buildObservable();
	}

	public CompletableFuture<T> query() {
		return query(AsyncExecutor.EXECUTOR);
	}

	public CompletableFuture<T> query(Executor executor) {
		return CompletableFuture.supplyAsync(supplier, executor)
				.whenComplete((response, error) -> {
					for (Subscription<T> subscription : subscriptions) {
						Observer<T> observer = subscription.getObserver();
						observer.onComplete();

						if (error == null) {
							observer.onNext(response);
						} else {
							observer.onError(error);
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	public static <T> Observable<T> empty() {
		return (Observable<T>) EMPTY_OBSERVABLE;
	}

}