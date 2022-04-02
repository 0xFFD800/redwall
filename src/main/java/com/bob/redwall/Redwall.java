package com.bob.redwall;

import com.bob.redwall.proxy.IProxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author Luke
 *
 */
@Mod(modid = Ref.MODID, version = Ref.VERSION, useMetadata = true)
public class Redwall {
	@SidedProxy(modId = Ref.MODID, clientSide = Ref.CLIENT_PROXY, serverSide = Ref.SERVER_PROXY)
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		proxy.serverLoad(event);
	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event) {
		proxy.serverStopped(event);
	}

	@Mod.EventBusSubscriber(modid = Ref.MODID)
	public static class Registration {
		@SubscribeEvent
		public void registerItems(RegistryEvent.Register<Item> event) {
			proxy.registerItems(event);
		}

		@SubscribeEvent
		public void registerBlocks(RegistryEvent.Register<Block> event) {
			proxy.registerBlocks(event);
		}

		@SubscribeEvent
		public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			proxy.registerRecipes(event);
		}

		@SubscribeEvent
		public void registerStatusEffects(RegistryEvent.Register<Potion> event) {
			proxy.registerStatusEffects(event);
		}

		@SubscribeEvent
		public void registerStatusEffectTypes(RegistryEvent.Register<PotionType> event) {
			proxy.registerStatusEffectTypes(event);
		}

		@SubscribeEvent
		public void registerBiomes(RegistryEvent.Register<Biome> event) {
			proxy.registerBiomes(event);
		}
	}
}
