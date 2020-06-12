package com.bob.redwall.items.food;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCustomFish extends ItemFood {
	public ItemCustomFish(int amount, float saturation, String name, CreativeTabs tab) {
		super(amount, saturation, false);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}
