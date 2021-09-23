package me.pixeldev.alya.versions.v1_17_R1;

import me.pixeldev.alya.abstraction.actionbar.ActionBarSender;
import me.pixeldev.alya.abstraction.item.ItemNBTTagHandler;
import me.pixeldev.alya.abstraction.packet.intercept.PacketInterceptorAssigner;
import me.pixeldev.alya.abstraction.title.TitleSender;
import me.pixeldev.alya.versions.v1_17_R1.actionbar.ActionBarSender1_17_R1;
import me.pixeldev.alya.versions.v1_17_R1.item.ItemNBTTagHandler1_17_R1;
import me.pixeldev.alya.versions.v1_17_R1.packet.PacketInterceptorAssigner1_17_R1;
import me.pixeldev.alya.versions.v1_17_R1.title.TitleSender1_17_R1;
import me.yushust.inject.AbstractModule;

public class NMSModule1_17_R1 extends AbstractModule {

	@Override
	protected void configure() {
		bind(TitleSender.class).to(TitleSender1_17_R1.class).singleton();
		bind(ActionBarSender.class).to(ActionBarSender1_17_R1.class).singleton();
		bind(PacketInterceptorAssigner.class).to(PacketInterceptorAssigner1_17_R1.class).singleton();
		bind(ItemNBTTagHandler.class).to(ItemNBTTagHandler1_17_R1.class).singleton();
	}

}
