package me.pixeldev.alya.jdk.functional;

@FunctionalInterface
public interface FailableSupplier<T> {

  T get() throws Throwable;

}