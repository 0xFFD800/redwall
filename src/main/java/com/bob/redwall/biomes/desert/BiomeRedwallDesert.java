package com.bob.redwall.biomes.desert;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeRedwallDesert extends Biome {
	public BiomeRedwallDesert(Biome.BiomeProperties properties) {
        super(properties);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SAND.getDefaultState();
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 2;
        this.decorator.reedsPerChunk = 50;
        this.decorator.cactiPerChunk = 10;

        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        //this.spawnableCreatureList.add(new SpawnListEntry(EntityBird.class, 1, 3, 4));
    }

    @Override
    public float getSpawningChance() {
        return RedwallWorldProvider.NPC_SPAWN_CHANCE_WORLDGEN / 2.0F;
    }
}
