package me.pixeldev.alya.jdk.functional;

@FunctionalInterface
public interface FailableConsumer {

	void accept() throws Exception;

}