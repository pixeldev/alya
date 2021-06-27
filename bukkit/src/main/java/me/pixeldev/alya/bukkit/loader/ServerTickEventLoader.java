package me.pixeldev.alya.bukkit.loader;

import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.alya.bukkit.tick.ServerTickCause;
import me.pixeldev.alya.bukkit.tick.ServerTickEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.inject.Inject;

public class ServerTickEventLoader implements Loader {

	private static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
	private static final int SECOND = 20;
	private static final int MINUTE = 1200;

	@Inject private Plugin plugin;

	private int currentSeconds = SECOND;
	private int currentMinutes = MINUTE;

	@Override
	public void load() {
		Bukkit.getScheduler().runTaskTimer(
				plugin,
				() -> {
					currentSeconds--;
					currentMinutes--;

					PLUGIN_MANAGER.callEvent(new ServerTickEvent(ServerTickCause.TICK));

					if (currentSeconds == 0) {
						currentSeconds = SECOND;

						PLUGIN_MANAGER.callEvent(new ServerTickEvent(ServerTickCause.SECOND));
					}

					if (currentMinutes == 0) {
						currentMinutes = MINUTE;

						PLUGIN_MANAGER.callEvent(new ServerTickEvent(ServerTickCause.MINUTE));
					}
				}, 0, 1
		);
	}

}