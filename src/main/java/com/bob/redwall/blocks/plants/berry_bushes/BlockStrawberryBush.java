package com.bob.redwall.blocks.plants.berry_bushes;

import com.bob.redwall.blocks.plants.BlockModBush;
import com.bob.redwall.init.ItemHandler;

import net.minecraft.item.Item;

public class BlockStrawberryBush extends BlockModBush {
	public BlockStrawberryBush(String name) {
		super(name);
	}

	@Override
	protected Item getSeed() {
		return ItemHandler.strawberry_seeds;
	}

	@Override
	protected Item getCrop() {
		return ItemHandler.strawberry;
	}
}
