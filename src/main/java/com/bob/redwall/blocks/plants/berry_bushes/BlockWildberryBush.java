package com.bob.redwall.blocks.plants.berry_bushes;

import com.bob.redwall.blocks.plants.BlockModBush;
import com.bob.redwall.init.ItemHandler;

import net.minecraft.item.Item;

public class BlockWildberryBush extends BlockModBush {
	public BlockWildberryBush(String name) {
		super(name);
	}

	@Override
	protected Item getSeed() {
		return ItemHandler.wildberry_seeds;
	}

	@Override
	protected Item getCrop() {
		return ItemHandler.wildberry;
	}
}
