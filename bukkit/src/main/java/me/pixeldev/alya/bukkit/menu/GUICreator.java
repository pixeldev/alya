package me.pixeldev.alya.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface GUICreator {

	Inventory create(Player issuer, Object... extraData);

}