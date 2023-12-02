package com.bob.redwall.blocks.plants;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockWheatgrass extends BlockModGrass {
	public BlockWheatgrass(Material mat, String name, CreativeTabs blocks) {
		super(mat, name, blocks);
	}

	@Override
	public NonNullList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (RANDOM.nextInt(8) != 0)
			return NonNullList.create();
		ItemStack seed = new ItemStack(Items.WHEAT_SEEDS);
		if (!seed.isEmpty())
			return NonNullList.withSize(1, seed);
		else return NonNullList.create();
	}
}
