package com.bob.redwall.dimensions.shared.rtg.world.gen;

import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IRealisticBiome;

/**
 *
 * @author Zeno410
 */
public class ChunkLandscape {
    public float [] noise = new float [256];
    public IRealisticBiome[] biome = new IRealisticBiome [256];
    public float [] river = new float [256];
}
