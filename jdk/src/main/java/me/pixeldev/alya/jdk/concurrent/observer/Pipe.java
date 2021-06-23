package me.pixeldev.alya.jdk.concurrent.observer;

import java.util.function.Function;
import java.util.function.Supplier;

public class Pipe<T> {

	private final Supplier<T> supplier;

	public Pipe(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	public <R> Pipe<R> forkJoin(Function<T, Observable<R>> join) {
		return new Pipe<>(() -> join.apply(supplier.get()).getSupplier().get());
	}

	public <R> Pipe<R> map(Function<T, R> mapper) {
		return new Pipe<>(() -> mapper.apply(supplier.get()));
	}

	public <R, M> Pipe<R> mergeMap(Function<T, Observable<M>> join, Function<M, R> mapper) {
		return new Pipe<>(() -> mapper.apply(join.apply(supplier.get()).getSupplier().get()));
	}

	protected Observable<T> buildObservable() {
		return new Observable<>(supplier);
	}

}