package com.bob.redwall.init;

import com.bob.redwall.Ref;
import com.bob.redwall.tileentity.TileEntityCookingGeneric;
import com.bob.redwall.tileentity.TileEntitySmeltery;
import com.bob.redwall.tileentity.TileEntitySmithingGeneric;
import com.bob.redwall.tileentity.TileEntityWeaponRack;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void register() {
		GameRegistry.registerTileEntity(TileEntityWeaponRack.class, new ResourceLocation(Ref.MODID, "weapon_rack"));
		GameRegistry.registerTileEntity(TileEntitySmithingGeneric.class, new ResourceLocation(Ref.MODID, "smithing_generic"));
		GameRegistry.registerTileEntity(TileEntitySmeltery.class, new ResourceLocation(Ref.MODID, "smeltery"));
		GameRegistry.registerTileEntity(TileEntityCookingGeneric.class, new ResourceLocation(Ref.MODID, "cooking"));
	}
}
