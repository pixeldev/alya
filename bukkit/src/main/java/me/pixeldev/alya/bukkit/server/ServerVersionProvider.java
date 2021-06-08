package me.pixeldev.alya.bukkit.server;

import org.bukkit.Bukkit;

public final class ServerVersionProvider {

	public static final String SERVER_VERSION =
		Bukkit.getServer()
			.getClass().getPackage()
			.getName().split("\\.")[3]
			.substring(1);

	public static final int SERVER_VERSION_INT = SERVER_VERSION.charAt(2);

}