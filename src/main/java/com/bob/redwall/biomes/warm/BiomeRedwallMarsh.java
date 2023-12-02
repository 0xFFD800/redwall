package com.bob.redwall.biomes.warm;

import java.util.Random;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeRedwallMarsh extends Biome {
	protected static final IBlockState WATER_LILY = Blocks.WATERLILY.getDefaultState();

	public BiomeRedwallMarsh(Biome.BiomeProperties properties) {
		super(properties);

		this.decorator.treesPerChunk = 2;
		this.decorator.flowersPerChunk = 1;
		this.decorator.reedsPerChunk = 10;
		this.decorator.clayPerChunk = 1;
		this.decorator.sandPatchesPerChunk = 0;
		this.decorator.grassPerChunk = 20;

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
	public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
		return BlockFlower.EnumFlowerType.BLUE_ORCHID;
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		double d0 = GRASS_COLOR_NOISE.getValue((double) x * 0.25D, (double) z * 0.25D);

		if (d0 > 0.0D) {
			int i = x & 15;
			int j = z & 15;

			for (int k = 255; k >= 0; --k) {
				if (chunkPrimerIn.getBlockState(j, k, i).getMaterial() != Material.AIR) {
					if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER)
						chunkPrimerIn.setBlockState(j, k, i, WATER);

					break;
				}
			}
		}

		this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		for (int i1 = 0; i1 < this.decorator.clayPerChunk; ++i1) {
			rand.nextInt(16);
			rand.nextInt(16);
		}
		
		super.decorate(worldIn, rand, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos) {
		double d0 = GRASS_COLOR_NOISE.getValue((double) pos.getX() * 0.0225D, (double) pos.getZ() * 0.0225D);
		return d0 < -0.1D ? 5011004 : 6975545;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos) {
		return 6975545;
	}

	@Override
	public void addDefaultFlowers() {
		addFlower(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID), 10);
	}
}
