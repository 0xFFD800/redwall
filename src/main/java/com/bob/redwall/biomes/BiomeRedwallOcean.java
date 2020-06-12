package com.bob.redwall.biomes;

import net.minecraft.world.biome.Biome;

public class BiomeRedwallOcean extends Biome {
	public BiomeRedwallOcean(Biome.BiomeProperties properties) {
        super(properties);
        
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
    }

    public Biome.TempCategory getTempCategory() {
        return Biome.TempCategory.OCEAN;
    }
}
