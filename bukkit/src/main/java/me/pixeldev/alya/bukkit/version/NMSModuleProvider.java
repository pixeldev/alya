package me.pixeldev.alya.bukkit.version;

import me.pixeldev.alya.bukkit.server.ServerVersionProvider;

import me.yushust.inject.Module;

public final class NMSModuleProvider {

	public static Module getModule() {
		try {
			return (Module) Class.forName("me.pixeldev.alya.versions.v"
				+ ServerVersionProvider.SERVER_VERSION +
				".NMSModule" + ServerVersionProvider.SERVER_VERSION)
				.getConstructor()
				.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Your server version isn't supported.");
		}
	}

}