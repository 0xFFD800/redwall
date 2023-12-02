package com.bob.redwall.core;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class RedwallDummyContainer extends DummyModContainer {
	public RedwallDummyContainer() {
		super(getCorrectMetadata());
	}

	@SuppressWarnings("deprecation")
	private static ModMetadata getCorrectMetadata() {
		ModMetadata meta = new ModMetadata();
		meta.modId = "redwallcore";
		meta.name = "The Redwall Mod (Core)";
		meta.version = "@VERSION@"; // String.format("%d.%d.%d.%d", majorVersion, minorVersion, revisionVersion,
									// buildVersion);
		meta.credits = "Emperor_Luke_II--Programming";
		meta.authorList = Arrays.asList("Emperor_Luke_II");
		meta.description = "";
		meta.url = "";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
		return meta;
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public void modConstruction(FMLConstructionEvent evt) {

	}

	@Subscribe
	public void init(FMLInitializationEvent evt) {

	}

	@Subscribe
	public void preInit(FMLPreInitializationEvent evt) {

	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent evt) {

	}
}