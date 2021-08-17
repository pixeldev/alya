package me.pixeldev.alya.jdk.http.retry;

public class ExponentialRetryPolicy implements RetryPolicy {

	private final float multiplier;
	private final long initialInterval;

	public ExponentialRetryPolicy(float multiplier, long initialInterval) {
		this.multiplier = multiplier;
		this.initialInterval = initialInterval;
	}

	@Override
	public SleepTimeGenerator createGenerator() {
		return new ExponentialSleepTimeGenerator();
	}

	private class ExponentialSleepTimeGenerator implements SleepTimeGenerator {

		private long interval = initialInterval;

		@Override
		public long nextSleepTime() {
			long currentInterval = interval;
			interval *= multiplier;
			return currentInterval;
		}
	}

}