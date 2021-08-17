package me.pixeldev.alya.storage.universal;

public interface Model {

	String getId();

	default String getCacheIdentifier() {
		return getId();
	}

}