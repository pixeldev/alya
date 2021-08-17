package me.pixeldev.alya.bungee;

import me.pixeldev.alya.api.AlyaBasePlugin;

import me.pixeldev.alya.bungee.translation.MessageModule;
import me.yushust.inject.Injector;

import net.md_5.bungee.api.plugin.Plugin;

import java.util.Random;

public abstract class BungeeBasePlugin
		extends Plugin
		implements AlyaBasePlugin {

	public static Random RANDOM = new Random();

	@Override
	public void onLoad() {
		Injector.create(getMainModule(), new MessageModule()).injectMembers(this);
	}

}