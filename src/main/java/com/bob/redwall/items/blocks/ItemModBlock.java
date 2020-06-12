package com.bob.redwall.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class ItemModBlock extends ItemBlock {
	protected final Block block;

    public ItemModBlock(Block block, ResourceLocation resourceLocation) {
    	super(block);
    	this.setRegistryName(resourceLocation);
        this.block = block;
    }
}
