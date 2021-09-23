package me.pixeldev.alya.versions.v1_16_R3;

import me.pixeldev.alya.abstraction.actionbar.ActionBarSender;
import me.pixeldev.alya.abstraction.item.ItemNBTTagHandler;
import me.pixeldev.alya.abstraction.packet.intercept.PacketInterceptorAssigner;
import me.pixeldev.alya.abstraction.title.TitleSender;
import me.pixeldev.alya.versions.v1_16_R3.actionbar.ActionBarSender1_16_R3;
import me.pixeldev.alya.versions.v1_16_R3.item.ItemNBTTagHandler1_16_R3;
import me.pixeldev.alya.versions.v1_16_R3.packet.PacketInterceptorAssigner1_16_R3;
import me.pixeldev.alya.versions.v1_16_R3.title.TitleSender1_16_R3;

import me.yushust.inject.AbstractModule;

public class NMSModule1_16_R3 extends AbstractModule {

	@Override
	protected void configure() {
		bind(TitleSender.class).to(TitleSender1_16_R3.class).singleton();
		bind(ActionBarSender.class).to(ActionBarSender1_16_R3.class).singleton();
		bind(PacketInterceptorAssigner.class).to(PacketInterceptorAssigner1_16_R3.class).singleton();
		bind(ItemNBTTagHandler.class).to(ItemNBTTagHandler1_16_R3.class).singleton();
	}

}
