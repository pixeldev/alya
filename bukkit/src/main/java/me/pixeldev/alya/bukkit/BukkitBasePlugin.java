package me.pixeldev.alya.bukkit;

import me.pixeldev.alya.api.AlyaBasePlugin;
import me.pixeldev.alya.bukkit.translation.MessageModule;
import me.pixeldev.alya.bukkit.version.NMSModuleProvider;

import me.yushust.inject.Injector;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public abstract class BukkitBasePlugin
		extends JavaPlugin
		implements AlyaBasePlugin {

	public static final Random RANDOM = new Random();

	public void onLoad() {
		Injector.create(getMainModule(),
				new MessageModule(),
				NMSModuleProvider.getModule(
						"me.pixeldev.alya.versions"
				)
		)
				.injectMembers(this);
	}

}