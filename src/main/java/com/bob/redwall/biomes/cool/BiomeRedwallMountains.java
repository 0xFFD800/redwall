package com.bob.redwall.biomes.cool;

import java.util.Random;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeRedwallMountains extends Biome {
	private final BiomeRedwallMountains.Type type;

	public BiomeRedwallMountains(BiomeRedwallMountains.Type p_i46710_1_, Biome.BiomeProperties properties) {
		super(properties);

		if (p_i46710_1_ == BiomeRedwallMountains.Type.EXTRA_TREES)
			this.decorator.treesPerChunk = 3;

		this.type = p_i46710_1_;

		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		// this.spawnableCreatureList.add(new SpawnListEntry(EntityBird.class, 1, 1,
		// 1));
	}

	@Override
	public float getSpawningChance() {
		return RedwallWorldProvider.NPC_SPAWN_CHANCE_WORLDGEN;
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		super.decorate(worldIn, rand, pos);
		net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, rand, pos));
		WorldGenerator emeralds = new EmeraldGenerator();
		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, emeralds, pos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.EMERALD))
			emeralds.generate(worldIn, rand, pos);
		net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, rand, pos));
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		this.topBlock = Blocks.GRASS.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();

		if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.type == BiomeRedwallMountains.Type.MUTATED) {
			this.topBlock = Blocks.GRAVEL.getDefaultState();
			this.fillerBlock = Blocks.GRAVEL.getDefaultState();
		} else if (noiseVal > 1.0D && this.type != BiomeRedwallMountains.Type.EXTRA_TREES) {
			this.topBlock = Blocks.STONE.getDefaultState();
			this.fillerBlock = Blocks.STONE.getDefaultState();
		}

		this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}

	public static enum Type {
		NORMAL, EXTRA_TREES, MUTATED;
	}

	private static class EmeraldGenerator extends WorldGenerator {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos pos) {
			int count = 3 + rand.nextInt(6);
			for (int i = 0; i < count; i++) {
				BlockPos blockpos = pos.add(rand.nextInt(16), rand.nextInt(28) + 4, rand.nextInt(16));

				net.minecraft.block.state.IBlockState state = worldIn.getBlockState(blockpos);
				if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.STONE)))
					worldIn.setBlockState(blockpos, Blocks.EMERALD_ORE.getDefaultState(), 2);
			}
			return true;
		}
	}
}
