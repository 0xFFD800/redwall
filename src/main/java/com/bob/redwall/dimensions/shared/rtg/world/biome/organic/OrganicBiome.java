package com.bob.redwall.dimensions.shared.rtg.world.biome.organic;

import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBaseBiomeDecorations;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceOrganic;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainOrganic;
import com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.RealisticBiomeBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.world.biome.Biome;

/**
 * @author topisani
 */
public class OrganicBiome extends RealisticBiomeBase {
    public OrganicBiome(Biome biome) {
        super(biome, biome.getDefaultTemperature() < 0.15f ? BiomeHandler.redwall_river_frozen : BiomeHandler.redwall_river);
    }

    @Override
    public Biome baseBiome() {
        return this.baseBiome;
    }

    @Override
    public Biome riverBiome() {
        return this.riverBiome;
    }

    @Override
    public void initConfig() {

    }

    @Override
    public boolean hasConfig() {
        return false;
    }

    @Override
    public TerrainBase initTerrain() {
        return new TerrainOrganic();
    }

    @Override
    public SurfaceBase initSurface() {
        return new SurfaceOrganic(this.config, this.baseBiome.topBlock, this.baseBiome.fillerBlock);
    }

    @Override
    public void initDecos() {
        this.addDeco(new DecoBaseBiomeDecorations());
    }
}
