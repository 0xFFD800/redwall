package com.bob.redwall.biomes;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.world.biome.Biome;

public class BiomeRedwallRiver extends Biome {
	boolean isPlateau = false;

	public BiomeRedwallRiver(Biome.BiomeProperties properties) {
		super(properties);

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	public BiomeRedwallRiver(boolean isPlateau, Biome.BiomeProperties properties) {
		this(properties);

		this.isPlateau = isPlateau;

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public float getSpawningChance() {
		return RedwallWorldProvider.NPC_SPAWN_CHANCE_WORLDGEN;
	}
}
