package me.pixeldev.alya.jdk.http.retry;

public interface RetryPolicy {

	SleepTimeGenerator createGenerator();

	interface SleepTimeGenerator {

		long nextSleepTime();

	}

}