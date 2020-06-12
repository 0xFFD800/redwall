package com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature;

import java.util.Random;

import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterReed extends WorldGenerator {
	@Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 20; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));

            if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
                int j = 2 + rand.nextInt(rand.nextInt(3) + 1);

                for (int k = 0; k < j; ++k) {
                    if (BlockHandler.water_reeds.canPlaceBlockAt(worldIn, blockpos.up(k))) {
                        worldIn.setBlockState(blockpos.up(k), BlockHandler.water_reeds.getStateForPlacement(worldIn, blockpos.up(k), EnumFacing.UP, 0, 0, 0, 0, null, null), 2);
                    }
                }
            }
        }

        return true;
    }
}