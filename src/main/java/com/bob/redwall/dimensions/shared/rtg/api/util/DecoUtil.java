package com.bob.redwall.dimensions.shared.rtg.api.util;

import com.bob.redwall.dimensions.shared.rtg.api.RTGAPI;
import com.bob.redwall.dimensions.shared.rtg.api.config.RTGConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;

public class DecoUtil {
    private RTGConfig rtgConfig = RTGAPI.config();

    public DecoUtil(DecoBase deco) { }

    public int calculateLoopCountFromTreeDensity(int loopCount, IRealisticBiome biome) {
        float multiplier = rtgConfig.TREE_DENSITY_MULTIPLIER.get();
        float biomeMultiplier = biome.getConfig().TREE_DENSITY_MULTIPLIER.get();

        if (biomeMultiplier >= 0f) {
            multiplier = (biomeMultiplier > RTGConfig.MAX_TREE_DENSITY) ? RTGConfig.MAX_TREE_DENSITY : biomeMultiplier;
        }

        loopCount = (int)((float)loopCount * multiplier);

        return loopCount;
    }

    public int adjustChanceFromMultiplier(int chanceIn, float multiplier) {
        int chanceOut = (multiplier != 0f) ? ((int) Math.floor((float)chanceIn / multiplier)) : chanceIn;
        return (chanceOut == 0) ? 1 : chanceOut;
    }
}