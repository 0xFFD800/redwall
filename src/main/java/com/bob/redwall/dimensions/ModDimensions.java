package com.bob.redwall.dimensions;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	public static int redwallDimId = -10;
	public static final DimensionType REDWALL = DimensionType.register("Redwall", "_redwall", redwallDimId, RedwallWorldProvider.class, false);
	
	public static void init(){
		registerDimensions();
	}
	
	private static void registerDimensions(){
		DimensionManager.registerDimension(redwallDimId, REDWALL);
	}
}
