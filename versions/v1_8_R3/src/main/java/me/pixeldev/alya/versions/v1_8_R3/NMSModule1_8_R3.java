package me.pixeldev.alya.versions.v1_8_R3;

import me.pixeldev.alya.abstraction.actionbar.ActionBarSender;
import me.pixeldev.alya.abstraction.packet.PacketSender;
import me.pixeldev.alya.abstraction.title.TitleSender;
import me.pixeldev.alya.versions.v1_8_R3.actionbar.ActionBarSender1_8_R3;
import me.pixeldev.alya.versions.v1_8_R3.packet.PacketSender1_8_R3;
import me.pixeldev.alya.versions.v1_8_R3.title.TitleSender1_8_R3;

import me.yushust.inject.AbstractModule;

public class NMSModule1_8_R3 extends AbstractModule {

	@Override
	protected void configure() {
		bind(PacketSender.class).to(PacketSender1_8_R3.class).singleton();
		bind(TitleSender.class).to(TitleSender1_8_R3.class).singleton();
		bind(ActionBarSender.class).to(ActionBarSender1_8_R3.class).singleton();
	}

}