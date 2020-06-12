package com.bob.redwall.blocks.plants.berry_bushes;

import com.bob.redwall.blocks.plants.BlockModBush;
import com.bob.redwall.init.ItemHandler;

import net.minecraft.item.Item;

public class BlockElderberryBush extends BlockModBush {
	public BlockElderberryBush(String name) {
		super(name);
	}

	@Override
	protected Item getSeed() {
		return ItemHandler.elderberry_seeds;
	}

	@Override
	protected Item getCrop() {
		return ItemHandler.elderberry;
	}
}
