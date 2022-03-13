package me.pixeldev.alya.versions.v1_18_R2;

import me.pixeldev.alya.abstraction.actionbar.ActionBarSender;
import me.pixeldev.alya.abstraction.item.ItemNBTTagHandler;
import me.pixeldev.alya.abstraction.packet.intercept.PacketInterceptorAssigner;
import me.pixeldev.alya.abstraction.title.TitleSender;
import me.pixeldev.alya.versions.v1_18_R2.actionbar.ActionBarSender1_18_R2;
import me.pixeldev.alya.versions.v1_18_R2.item.ItemNBTTagHandler1_18_R2;
import me.pixeldev.alya.versions.v1_18_R2.packet.PacketInterceptorAssigner1_18_R2;
import me.pixeldev.alya.versions.v1_18_R2.title.TitleSender1_18_R2;
import me.yushust.inject.AbstractModule;

public class NMSModule1_18_R2 extends AbstractModule {

	@Override
	protected void configure() {
		bind(TitleSender.class).to(TitleSender1_18_R2.class).singleton();
		bind(ActionBarSender.class).to(ActionBarSender1_18_R2.class).singleton();
		bind(PacketInterceptorAssigner.class).to(PacketInterceptorAssigner1_18_R2.class).singleton();
		bind(ItemNBTTagHandler.class).to(ItemNBTTagHandler1_18_R2.class).singleton();
	}
	
}
