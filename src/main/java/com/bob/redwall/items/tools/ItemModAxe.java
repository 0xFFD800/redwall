package com.bob.redwall.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class ItemModAxe extends ItemAxe {
	public ItemModAxe(String name, CreativeTabs tab, float spd, float dmg, Item.ToolMaterial material) {
		super(material, dmg, spd);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}
