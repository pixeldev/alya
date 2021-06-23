package me.pixeldev.alya.bukkit.tick;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerTickEvent extends Event {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	private final ServerTickCause serverTickCause;

	public ServerTickEvent(ServerTickCause serverTickCause) {
		this.serverTickCause = serverTickCause;
	}

	public ServerTickCause getServerTickCause() {
		return serverTickCause;
	}

	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}