package me.pixeldev.alya.jdk.concurrent;

import me.pixeldev.alya.jdk.functional.FailableConsumer;

import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public final class AsyncExecutor {

	public static final Executor EXECUTOR = Executors.newCachedThreadPool();

	public CompletableFuture<Void> run(FailableConsumer runnable) {
		return CompletableFuture.runAsync(() -> {
			try {
				runnable.accept();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}, EXECUTOR);
	}

	public <T> CompletableFuture<T> supply(Callable<T> supplier) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.call();
			} catch (Throwable throwable) {
				throwable.printStackTrace();

				return null;
			}
		}, EXECUTOR);
	}

}