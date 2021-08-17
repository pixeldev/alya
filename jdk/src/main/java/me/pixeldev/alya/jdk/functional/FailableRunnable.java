package me.pixeldev.alya.jdk.functional;

@FunctionalInterface
public interface FailableRunnable {

	void run() throws Exception;

}