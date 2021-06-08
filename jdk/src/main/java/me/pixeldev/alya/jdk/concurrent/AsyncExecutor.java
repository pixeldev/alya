package me.pixeldev.alya.jdk.concurrent;

import me.pixeldev.alya.jdk.functional.FailableConsumer;
import me.pixeldev.alya.jdk.functional.FailableSupplier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncExecutor {

  private static final Executor EXECUTOR = Executors.newCachedThreadPool();

  public static CompletableFuture<Void> run(FailableConsumer runnable) {
    return CompletableFuture.runAsync(() -> {
      try {
        runnable.accept();
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }, EXECUTOR);
  }

  public static <T> CompletableFuture<T> supply(FailableSupplier<T> supplier) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return supplier.get();
      } catch (Throwable throwable) {
        throwable.printStackTrace();

        return null;
      }
    }, EXECUTOR);
  }

}