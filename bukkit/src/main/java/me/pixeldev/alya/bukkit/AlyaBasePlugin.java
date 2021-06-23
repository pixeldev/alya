package me.pixeldev.alya.bukkit;

import me.pixeldev.alya.bukkit.translation.MessageModule;
import me.pixeldev.alya.bukkit.version.NMSModuleProvider;

import me.yushust.inject.Injector;
import me.yushust.inject.Module;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.SplittableRandom;

public abstract class AlyaBasePlugin extends JavaPlugin {

	public static final SplittableRandom RANDOM = new SplittableRandom();

	public void onLoad() {
		Injector.create(getMainModule(),
				new MessageModule(),
				NMSModuleProvider.getModule(
						"me.pixeldev.alya.versions"
				)
		)
				.injectMembers(this);
	}

	protected abstract Module getMainModule();

}