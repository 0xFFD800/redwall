
package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;

import net.minecraft.util.math.ChunkPos;

/**
 *
 * @author Zeno410
 */
public class DecoSingleBiomeDecorations extends DecoBaseBiomeDecorations {

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {
        if (this.allowed) {
            // skip if already decorated
            ChunkPos position = new ChunkPos(worldX,worldZ);
            if (rtgWorld.decoratedChunks().contains(position)) return;
            rtgWorld.decoratedChunks().add(position);
            super.generate(biome, rtgWorld, rand, worldX, worldZ, strength, river, hasPlacedVillageBlocks); 
        }
    }

}
