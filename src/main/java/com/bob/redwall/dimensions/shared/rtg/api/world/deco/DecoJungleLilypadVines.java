package com.bob.redwall.dimensions.shared.rtg.api.world.deco;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenVinesRTG;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * @author WhichOnesPink
 */
public class DecoJungleLilypadVines extends DecoBase {
    public DecoJungleLilypadVines() {
        super();

        this.addDecoTypes(DecoType.LILYPAD, DecoType.VINE);
    }

    /**
     * No config options for this one yet. Just ripped it directly from the old code.
     */
	@Override
    @SuppressWarnings("deprecation")
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {
        if (this.allowed) {
            if (TerrainGen.decorate(rtgWorld.world(), rand, new BlockPos(worldX, 0, worldZ), LILYPAD)) {
                WorldGenerator worldgeneratorLilypads = new WorldGenWaterlily();
                WorldGenerator worldgeneratorVines = new WorldGenVinesRTG();

                for (int b33 = 0; b33 < 5; b33++) {
                    int j6 = worldX + rand.nextInt(16) + 8;
                    int k10 = worldZ + rand.nextInt(16) + 8;
                    int z52 = rtgWorld.world().getHeight(new BlockPos(j6, 0, k10)).getY();

                    for (int h44 = 0; h44 < 8; h44++) {
                        if (z52 > 64) {
                            worldgeneratorLilypads.generate(rtgWorld.world(), rand, new BlockPos(j6, z52, k10));
                        }
                    }

                    for (int h44 = 100; h44 > 0; h44--) {
                        worldgeneratorVines.generate(rtgWorld.world(), rand, new BlockPos(j6, z52, k10));
                    }
                }
            }
        }
    }
}
