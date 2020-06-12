package com.bob.redwall.dimensions.shared.rtg.api.world;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.util.TimedHashSet;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.CellNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SimplexOctave;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IBiomeProviderRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.OrganicBiomeGenerator;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeMesa;

/**
 * Created by WhichOnesPink on 26/06/2017.
 */
public interface IRTGWorld {
    World world();
    OpenSimplexNoise simplex();
    CellNoise cell();
    CellNoise voronoi();
    Random rand();
    SimplexOctave.Disk surfaceJitter();
    TimedHashSet<ChunkPos> decoratedChunks();
    BiomeMesa mesaBiome();
    OrganicBiomeGenerator organicBiomeGenerator();
    int getRepairedBiomeAt(IBiomeProviderRTG cmr, int cx, int cz);
}
