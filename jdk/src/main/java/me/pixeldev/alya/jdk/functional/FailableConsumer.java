package me.pixeldev.alya.jdk.functional;

@FunctionalInterface
public interface FailableConsumer<T> {

	void accept(T instance) throws Exception;

}