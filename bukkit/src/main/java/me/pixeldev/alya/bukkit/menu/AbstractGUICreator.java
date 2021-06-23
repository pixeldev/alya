package me.pixeldev.alya.bukkit.menu;

import me.yushust.message.MessageHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractGUICreator implements GUICreator {

	private static final String MENU_FORMAT = "menu.%s";
	private static final String MENU_NAME_FORMAT = MENU_FORMAT + ".title";
	private static final String ITEM_FORMAT = MENU_FORMAT + ".items.%s";
	private static final String ITEM_NAME_FORMAT = ITEM_FORMAT + ".name";
	private static final String ITEM_LORE_FORMAT = ITEM_FORMAT + ".lore";

	@Inject protected MessageHandler messageHandler;
	private final String menuKey;

	public AbstractGUICreator(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getTitle(Player issuer, Object... jitEntities) {
		return messageHandler.get(
				issuer,
				String.format(MENU_NAME_FORMAT, menuKey),
				jitEntities
		);
	}

	public ItemClickable createBackItem(Player issuer,
																			int slot,
																			ItemStack itemStack,
																			Supplier<GUICreator> backMenuSupplier,
																			Object... extraData) {
		return ItemClickable.builder(slot)
				.setItemStack(itemStack)
				.setAction(event -> {
					issuer.openInventory(backMenuSupplier.get().create(issuer, extraData));
					return true;
				})
				.build();
	}

	public ItemBuilder createItem(Player issuer,
																Material material, String itemKey,
																Object... jitEntities) {
		return ItemBuilder.newBuilder(material)
				.setName(getItemName(issuer, itemKey, jitEntities))
				.setLore(getItemLore(issuer, itemKey, jitEntities));
	}

	public String getItemName(Player issuer,
														String itemKey,
														Object... jitEntities) {
		return messageHandler.get(
				issuer,
				String.format(ITEM_NAME_FORMAT, menuKey, itemKey),
				jitEntities
		);
	}

	public List<String> getItemLore(Player issuer,
																	String itemKey,
																	Object... jitEntities) {
		return messageHandler.getMany(
				issuer,
				String.format(ITEM_LORE_FORMAT, menuKey, itemKey),
				jitEntities
		);
	}

}