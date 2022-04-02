package com.bob.redwall.biomes.warm;

import java.util.Random;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeRedwallGrassland extends Biome {
	private static final WorldGenSavannaTree SAVANNA_TREE = new WorldGenSavannaTree(false);

	public BiomeRedwallGrassland(Biome.BiomeProperties properties) {
		super(properties);

		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();

		this.decorator.treesPerChunk = 1;
		this.decorator.flowersPerChunk = 4;
		this.decorator.grassPerChunk = 20;
	}

	@Override
	public float getSpawningChance() {
		return RedwallWorldProvider.NPC_SPAWN_CHANCE_WORLDGEN;
	}

	public WorldGenAbstractTree genBigTreeChance(Random rand) {
		return (WorldGenAbstractTree) (rand.nextInt(5) > 0 ? SAVANNA_TREE : TREE_FEATURE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

		if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, pos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
			for (int i = 0; i < 7; ++i) {
				int j = rand.nextInt(16) + 8;
				int k = rand.nextInt(16) + 8;
				int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
				DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
			}

		super.decorate(worldIn, rand, pos);
	}
}
