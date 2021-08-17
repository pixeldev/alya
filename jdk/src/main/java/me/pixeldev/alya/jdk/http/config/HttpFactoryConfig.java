package me.pixeldev.alya.jdk.http.config;

public interface HttpFactoryConfig {

	int getConnectTimeout();

	int getReadTimeout();

	int getMaxTries();

	boolean writeStackTraces();

}