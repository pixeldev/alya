package me.pixeldev.alya.jdk.http.config;

public class SimpleHttpFactoryConfig implements HttpFactoryConfig {

	private final int connectTimeout;
	private final int readTimeout;
	private final int maxTries;
	private final boolean writeStackTraces;

	public SimpleHttpFactoryConfig(int connectTimeout, int readTimeout, int maxTries, boolean writeStackTraces) {
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.maxTries = maxTries;
		this.writeStackTraces = writeStackTraces;
	}

	@Override
	public int getConnectTimeout() {
		return connectTimeout;
	}

	@Override
	public int getReadTimeout() {
		return readTimeout;
	}

	@Override
	public int getMaxTries() {
		return maxTries;
	}

	@Override
	public boolean writeStackTraces() {
		return writeStackTraces;
	}

}