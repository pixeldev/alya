package me.pixeldev.alya.versions.v1_17_R1.item;

import me.pixeldev.alya.abstraction.item.ItemNBTTagHandler;

import net.minecraft.nbt.NBTTagCompound;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public class ItemNBTTagHandler1_17_R1 implements ItemNBTTagHandler {
	@Override
	public boolean hasTag(ItemStack source, String key) {
		return getTagCompound(asNmsItem(source)).hasKey(key);
	}

	@Override
	public Optional<String> getTag(ItemStack source, String key) {
		String gottenKey = getTagCompound(asNmsItem(source)).getString(key);

		if (gottenKey.isEmpty()) {
			gottenKey = null;
		}

		return Optional.ofNullable(gottenKey);
	}

	@Override
	public ItemStack setTags(ItemStack source, Map<String, String> nbtTags) {
		net.minecraft.world.item.ItemStack itemStack = asNmsItem(source);
		NBTTagCompound tagCompound = getTagCompound(itemStack);

		nbtTags.forEach(tagCompound::setString);
		itemStack.setTag(tagCompound);

		return CraftItemStack.asBukkitCopy(itemStack);
	}

	@Override
	public ItemStack setTag(ItemStack source, String key, String value) {
		net.minecraft.world.item.ItemStack itemStack = asNmsItem(source);
		NBTTagCompound tagCompound = getTagCompound(itemStack);

		tagCompound.setString(key, value);
		itemStack.setTag(tagCompound);

		return CraftItemStack.asBukkitCopy(itemStack);
	}

	@Override
	public ItemStack removeTag(ItemStack source, String key) {
		net.minecraft.world.item.ItemStack itemStack = asNmsItem(source);
		NBTTagCompound tagCompound = getTagCompound(itemStack);

		tagCompound.remove(key);
		itemStack.setTag(tagCompound);

		return CraftItemStack.asBukkitCopy(itemStack);
	}

	private static net.minecraft.world.item.ItemStack asNmsItem(ItemStack source) {
		return CraftItemStack.asNMSCopy(source);
	}

	private static NBTTagCompound getTagCompound(net.minecraft.world.item.ItemStack source) {
		NBTTagCompound tagCompound = source.getTag();

		if (tagCompound == null) {
			tagCompound = new NBTTagCompound();
		}

		return tagCompound;
	}

}
