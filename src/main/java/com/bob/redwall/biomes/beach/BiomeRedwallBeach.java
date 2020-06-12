package com.bob.redwall.biomes.beach;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeRedwallBeach extends Biome {
	public BiomeRedwallBeach(Biome.BiomeProperties properties) {
        super(properties);
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SAND.getDefaultState();
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;

        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        //this.spawnableCreatureList.add(new SpawnListEntry(EntityBird.class, 1, 2, 4));
    }
}
