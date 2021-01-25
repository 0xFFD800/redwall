package com.bob.redwall.dimensions;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	public static final int DIM_REDWALL_ID = -10;
	public static final DimensionType REDWALL = DimensionType.register("Redwall", "_redwall", DIM_REDWALL_ID, RedwallWorldProvider.class, false);
	
	public static void init(){
		registerDimensions();
	}
	
	private static void registerDimensions(){
		DimensionManager.registerDimension(DIM_REDWALL_ID, REDWALL);
	}
}
