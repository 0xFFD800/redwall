package com.bob.redwall.items.food;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSeedFood;

public class ItemModSeedFood extends ItemSeedFood {
	public ItemModSeedFood(String name, CreativeTabs tab, int healAmount, float saturation, Block crops, Block soil) {
		super(healAmount, saturation, crops, soil);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}
