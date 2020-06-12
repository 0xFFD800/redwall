package com.bob.redwall.dimensions.shared.rtg.api.world.surface;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class SurfaceGeneric extends SurfaceBase {

    public SurfaceGeneric(BiomeConfig config, IBlockState top, IBlockState filler) {
        super(config, top, filler);
    }

    @Override
    public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
        @SuppressWarnings("unused")
		Random rand = rtgWorld.rand();

        for (int k = 255; k > -1; k--) {
            Block b = primer.getBlockState(x, k, z).getBlock();

            if (b == Blocks.AIR) {
                depth = -1;
            } else if (b == Blocks.STONE) {
                depth++;

                if (depth == 0 && k > 61) {
                    primer.setBlockState(x, k, z, topBlock);
                } else if (depth < 4) {
                    primer.setBlockState(x, k, z, fillerBlock);
                }
            }
        }
    }
}
