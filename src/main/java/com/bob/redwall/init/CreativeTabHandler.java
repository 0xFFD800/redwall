package com.bob.redwall.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabHandler {
	public static final CreativeTabs BLOCKS = new CreativeTabs("blocks") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(BlockHandler.ash_log);
		}
	};
	
	public static final CreativeTabs FOOD = new CreativeTabs("food") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemHandler.strawberry);
		}
	};
	
	public static final CreativeTabs COMBAT = new CreativeTabs("combat") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ArmorHandler.iron_mixed_helmet);
		}
	};
	
	public static final CreativeTabs MISC = new CreativeTabs("misc") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemHandler.cornstalk);
		}
	};
}
