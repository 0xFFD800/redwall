package com.bob.redwall.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItem extends Item {
	public ModItem(String name, CreativeTabs tab){
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
	}
	
	public ModItem(String name, CreativeTabs tab, int stacksize){
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(stacksize);
	}
}
