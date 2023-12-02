package com.bob.redwall.init;

import com.bob.redwall.Ref;
import com.bob.redwall.tileentity.TileEntityBrewingGuosim;
import com.bob.redwall.tileentity.TileEntityBrewingRedwall;
import com.bob.redwall.tileentity.TileEntityBrewingVerminMossflower;
import com.bob.redwall.tileentity.TileEntityCookingGeneric;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;
import com.bob.redwall.tileentity.TileEntityPlate;
import com.bob.redwall.tileentity.TileEntitySmeltery;
import com.bob.redwall.tileentity.TileEntitySmithingGeneric;
import com.bob.redwall.tileentity.TileEntitySmithingGuosim;
import com.bob.redwall.tileentity.TileEntitySmithingRedwall;
import com.bob.redwall.tileentity.TileEntityWeaponRack;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void register() {
		GameRegistry.registerTileEntity(TileEntityWeaponRack.class, new ResourceLocation(Ref.MODID, "weapon_rack"));
		GameRegistry.registerTileEntity(TileEntitySmithingGeneric.class, new ResourceLocation(Ref.MODID, "smithing_generic"));
		GameRegistry.registerTileEntity(TileEntitySmeltery.class, new ResourceLocation(Ref.MODID, "smeltery"));
		GameRegistry.registerTileEntity(TileEntityCookingGeneric.class, new ResourceLocation(Ref.MODID, "cooking"));
		GameRegistry.registerTileEntity(TileEntitySmithingRedwall.class, new ResourceLocation(Ref.MODID, "smithing_redwall"));
		GameRegistry.registerTileEntity(TileEntitySmithingGuosim.class, new ResourceLocation(Ref.MODID, "smithing_guosim"));
		GameRegistry.registerTileEntity(TileEntityBrewingRedwall.class, new ResourceLocation(Ref.MODID, "brewing_redwall"));
		GameRegistry.registerTileEntity(TileEntityBrewingGuosim.class, new ResourceLocation(Ref.MODID, "brewing_guosim"));
		GameRegistry.registerTileEntity(TileEntityBrewingVerminMossflower.class, new ResourceLocation(Ref.MODID, "brewing_vermin_mossflower"));
		GameRegistry.registerTileEntity(TileEntityDrinkVessel.class, new ResourceLocation(Ref.MODID, "drink_vessel"));
		GameRegistry.registerTileEntity(TileEntityPlate.class, new ResourceLocation(Ref.MODID, "plate"));
	}
}
