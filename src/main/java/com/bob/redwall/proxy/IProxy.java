package com.bob.redwall.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

public interface IProxy {
	public void preInit(FMLPreInitializationEvent event);
	public void init(FMLInitializationEvent event);
	public void postInit(FMLPostInitializationEvent event);
	public void serverLoad(FMLServerStartingEvent event);
	public void serverStopped(FMLServerStoppedEvent event);
	public void registerItems(Register<Item> event);
	public void registerBlocks(Register<Block> event);
	public void registerRecipes(Register<IRecipe> event);
	public void registerStatusEffects(Register<Potion> event);
	public void registerStatusEffectTypes(Register<PotionType> event);
	public void registerBiomes(Register<Biome> event);
}
