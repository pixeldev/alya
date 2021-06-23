package me.pixeldev.alya.bukkit.version;

import me.yushust.inject.Module;
import team.unnamed.gui.core.version.ServerVersionProvider;

public final class NMSModuleProvider {

	public static Module getModule(String mainPackage) {
		try {
			return (Module) Class.forName(mainPackage + ".v"
					+ ServerVersionProvider.SERVER_VERSION +
					".NMSModule" + ServerVersionProvider.SERVER_VERSION)
					.getConstructor()
					.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Your server version isn't supported.");
		}
	}

}