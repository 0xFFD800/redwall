package com.bob.redwall.biomes;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.world.biome.Biome;

public class BiomeRedwallDeepOcean extends Biome {
	public BiomeRedwallDeepOcean(Biome.BiomeProperties properties) {
		super(properties);

		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
	}

	@Override
	public float getSpawningChance() {
		return RedwallWorldProvider.NPC_SPAWN_CHANCE_WORLDGEN;
	}

	@Override
	public Biome.TempCategory getTempCategory() {
		return Biome.TempCategory.OCEAN;
	}
}
