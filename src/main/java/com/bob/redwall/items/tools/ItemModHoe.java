package com.bob.redwall.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class ItemModHoe extends ItemHoe {
	public ItemModHoe(String name, CreativeTabs tab, Item.ToolMaterial material) {
		super(material);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}
