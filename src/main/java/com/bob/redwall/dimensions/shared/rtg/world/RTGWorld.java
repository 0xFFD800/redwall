package com.bob.redwall.dimensions.shared.rtg.world;

import java.util.Random;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;
import com.bob.redwall.dimensions.shared.rtg.api.util.TimedHashSet;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.CellNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SimplexOctave;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SpacedCellNoise;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.IBiomeProviderRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.biome.OrganicBiomeGenerator;
import com.bob.redwall.dimensions.shared.rtg.world.gen.LandscapeGenerator;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeMesa;

/**
 * @author topisani
 */
public class RTGWorld implements IRTGWorld {
    public final World world;
    public final OpenSimplexNoise simplex;
    public final CellNoise cell;
    public final CellNoise voronoi;
    public final Random rand;
    public final SimplexOctave.Disk surfaceJitter = new SimplexOctave.Disk();
    public final TimedHashSet<ChunkPos> decoratedChunks = new TimedHashSet<ChunkPos>(5000);
    public final BiomeMesa mesaBiome;
    public final OrganicBiomeGenerator organicBiomeGenerator;
    public final LandscapeGenerator landscapeGenerator;

    public RTGWorld(World world) {
        this.world = world;
        this.simplex = new OpenSimplexNoise(RedwallWorldProvider.VALOUR_SEED);
        this.voronoi = new SpacedCellNoise(RedwallWorldProvider.VALOUR_SEED);
        // Simplex Cell noise deleted because point assignments are defecting producing serious artifacts
        // I can't figure out how to fix it. - Zeno
        this.cell = new SpacedCellNoise(RedwallWorldProvider.VALOUR_SEED);
        this.rand = world.rand;
        mesaBiome = (BiomeMesa)Biomes.MESA;
        mesaBiome.generateBands(world.getSeed());
        this.organicBiomeGenerator = new OrganicBiomeGenerator(this);
        this.landscapeGenerator = new LandscapeGenerator(this);
    }

    @Override
    public World world() {
        return this.world;
    }

    @Override
    public OpenSimplexNoise simplex() {
        return this.simplex;
    }

    @Override
    public CellNoise cell() {
        return this.cell;
    }

    @Override
    public CellNoise voronoi() {
        return this.voronoi;
    }

    @Override
    public Random rand() {
        return this.rand;
    }

    @Override
    public SimplexOctave.Disk surfaceJitter() {
        return this.surfaceJitter;
    }

    @Override
    public TimedHashSet<ChunkPos> decoratedChunks() {
        return this.decoratedChunks;
    }

    @Override
    public BiomeMesa mesaBiome() {
        return this.mesaBiome;
    }

    @Override
    public OrganicBiomeGenerator organicBiomeGenerator() {
        return this.organicBiomeGenerator;
    }

    @Override
    public int getRepairedBiomeAt(IBiomeProviderRTG cmr, int cx, int cz) {
        return this.landscapeGenerator.getBiomeDataAt(cmr, cx, cz);
    }
}
