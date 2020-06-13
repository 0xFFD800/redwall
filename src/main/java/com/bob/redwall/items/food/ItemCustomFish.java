package com.bob.redwall.items.food;

import net.minecraft.creativetab.CreativeTabs;

public class ItemCustomFish extends ItemModFood {
	public ItemCustomFish(int amount, float saturation, String name, CreativeTabs tab, float prot, float carbs, float veg, float frut) {
		super(name, tab, amount, saturation, prot, carbs, veg, frut);
	}
}
