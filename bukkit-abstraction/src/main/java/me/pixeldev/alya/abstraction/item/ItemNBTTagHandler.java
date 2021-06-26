package me.pixeldev.alya.abstraction.item;

import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public interface ItemNBTTagHandler {

	boolean hasTag(ItemStack source, String key);

	Optional<String> getTag(ItemStack source, String key);

	ItemStack setTags(ItemStack source, Map<String, String> nbtTags);

	ItemStack setTag(ItemStack source, String key, String value);

	ItemStack removeTag(ItemStack source, String key);

}