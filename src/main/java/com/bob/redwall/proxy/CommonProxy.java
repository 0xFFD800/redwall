package com.bob.redwall.proxy;

import com.bob.redwall.Redwall.Registration;
import com.bob.redwall.Ref;
import com.bob.redwall.common.commands.CommandFactions;
import com.bob.redwall.common.commands.CommandSeason;
import com.bob.redwall.crafting.smithing.EquipmentModifier;
import com.bob.redwall.dimensions.shared.rtg.RTG;
import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.entity.statuseffect.StatusEffectType;
import com.bob.redwall.init.ArmorHandler;
import com.bob.redwall.init.BiomeHandler;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.CapabilityHandler;
import com.bob.redwall.init.CraftingHandler;
import com.bob.redwall.init.EntityHandler;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.init.KeyBindingHandler;
import com.bob.redwall.init.TileEntityHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

public class CommonProxy implements IProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
        Ref.LOGGER = event.getModLog();
		OBJLoader.INSTANCE.addDomain(Ref.MODID);
		
		MinecraftForge.EVENT_BUS.register(new Registration());
		
		KeyBindingHandler.init();
		EntityHandler.init();
		EntityHandler.initModels();
		BlockHandler.init();
		StatusEffect.init();
		StatusEffectType.init();
		ItemHandler.init();
		ArmorHandler.init();
		BiomeHandler.init();
		TileEntityHandler.register();
		EquipmentModifier.registerModifiers();

		//Register Capabilities
		CapabilityHandler.register();
		
		RTG.initPre(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		RTG.initPost(event);
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
		Ref.LOGGER.info("Registering commands...");
		event.registerServerCommand(new CommandSeason());
		event.registerServerCommand(new CommandFactions());
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event) {
		RTG.onServerStopped(event);
	}
	
	@Override
	public void registerItems(RegistryEvent.Register<Item> event) {
		Ref.LOGGER.info("Registering items...");
		ItemHandler.register(event);
		ArmorHandler.register(event);
		BlockHandler.registerItemBlocks(event);
	}
	
	@Override
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		Ref.LOGGER.info("Registering blocks...");
		BlockHandler.register(event);
	}
	
	@Override
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		Ref.LOGGER.info("Registering recipes...");
		CraftingHandler.register(event);
	}
	
	@Override
	public void registerStatusEffects(RegistryEvent.Register<Potion> event) {
		Ref.LOGGER.info("Registering potions...");
		StatusEffect.register(event);
	}

	@Override
	public void registerStatusEffectTypes(Register<PotionType> event) {
		Ref.LOGGER.info("Registering potion types...");
		StatusEffectType.register(event);
	}

	@Override
	public void registerBiomes(Register<Biome> event) {
		Ref.LOGGER.info("Registering biomes...");
		BiomeHandler.register(event);
	}
}
