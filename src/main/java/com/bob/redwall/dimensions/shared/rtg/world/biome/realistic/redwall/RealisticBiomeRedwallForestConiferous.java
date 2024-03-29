package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;
import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.util.CliffCalculator;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionForestConiferous;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallForestConiferous extends RealisticBiomeRedwallBase {
	public static Biome biome = BiomeHandler.redwall_forest_coniferous;
	public static Biome river = BiomeHandler.redwall_river;

	public RealisticBiomeRedwallForestConiferous() {
		super(biome, river);

		// Prevent ores from messing up the surface.
		this.rDecorator().graniteSize = 0;
		this.rDecorator().dioriteSize = 0;
		// this.rDecorator().andesiteSize = 0; // This looks good.
		// this.rDecorator().gravelSize = 0; // So does this.
		this.rDecorator().dirtSize = 0;
	}

	@Override
	public void initConfig() {
		this.getConfig().addProperty(this.getConfig().ALLOW_LOGS).set(true);
		this.getConfig().addProperty(this.getConfig().FALLEN_LOG_DENSITY_MULTIPLIER);

		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK).set("");
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK_META).set(0);
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_2_BLOCK).set("");
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_2_BLOCK_META).set(0);
	}

	@Override
	public TerrainBase initTerrain() {
		return new TerrainVanillaForest();
	}

	public class TerrainVanillaForest extends TerrainBase {
		private float hillStrength = 10f;// this needs to be linked to the

		public TerrainVanillaForest() {}

		@Override
		public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
			groundNoise = groundNoise(x, y, groundVariation, rtgWorld.simplex());
			float m = hills(x, y, hillStrength, rtgWorld.simplex(), river);
			float floNoise = 65f + groundNoise + m;

			return riverized(floNoise, river);
		}
	}

	@Override
	public SurfaceBase initSurface() {
		return new SurfaceVanillaForest(config, Blocks.GRASS.getDefaultState(), Blocks.DIRT.getDefaultState(), 0f, 1.5f, 60f, 65f, 1.5f, BlockUtil.getStateDirt(2), 0.6f, BlockUtil.getStateStone(BlockUtil.StoneType.STONE), -0.4f);
	}

	public class SurfaceVanillaForest extends SurfaceBase {
		private float min;

		private float sCliff = 1.5f;
		private float sHeight = 60f;
		private float sStrength = 65f;
		private float cCliff = 1.5f;

		private IBlockState mixBlock;
		private float mixHeight;
		private IBlockState mix2Block;
		private float mix2Height;

		public SurfaceVanillaForest(BiomeConfig config, IBlockState top, IBlockState fill, float minCliff, float stoneCliff, float stoneHeight, float stoneStrength, float clayCliff, IBlockState mix, float mixHeight, IBlockState mix2, float mix2Height) {
			super(config, top, fill);
			min = minCliff;

			sCliff = stoneCliff;
			sHeight = stoneHeight;
			sStrength = stoneStrength;
			cCliff = clayCliff;

			this.mixBlock = this.getConfigBlock(config.SURFACE_MIX_BLOCK.get(), config.SURFACE_MIX_BLOCK_META.get(), mix);
			this.mixHeight = mixHeight;
			this.mix2Block = this.getConfigBlock(config.SURFACE_MIX_2_BLOCK.get(), config.SURFACE_MIX_2_BLOCK_META.get(), mix2);
			this.mix2Height = mix2Height;
		}

		@Override
		public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
			Random rand = rtgWorld.rand();
			OpenSimplexNoise simplex = rtgWorld.simplex();
			float c = CliffCalculator.calc(x, z, noise);
			int cliff = 0;

			Block b;
			float mixNoise = simplex.noise2(i / 12f, j / 12f);
			for (int k = 255; k > -1; k--) {
				b = primer.getBlockState(x, k, z).getBlock();
				if (b == Blocks.AIR) {
					depth = -1;
				} else if (b == Blocks.STONE) {
					depth++;

					if (depth == 0) {

						float p = simplex.noise3(i / 8f, j / 8f, k / 8f) * 0.5f;
						if (c > min && c > sCliff - ((k - sHeight) / sStrength) + p) {
							cliff = 1;
						}
						if (c > cCliff) {
							cliff = 2;
						}

						if (cliff == 1) {
							if (rand.nextInt(3) == 0) {
								primer.setBlockState(x, k, z, hcCobble(rtgWorld, i, j, x, z, k));
							} else {
								primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
							}
						} else if (cliff == 2) {
							primer.setBlockState(x, k, z, getShadowStoneBlock(rtgWorld, i, j, x, z, k));
						} else if (k < RedwallWorldProvider.SEA_LEVEL - 1) {
							if (mixNoise < mix2Height) primer.setBlockState(x, k, z, Blocks.SAND.getDefaultState());
							else if (mixNoise < mixHeight) primer.setBlockState(x, k, z, Blocks.CLAY.getDefaultState());
							else primer.setBlockState(x, k, z, Blocks.GRAVEL.getDefaultState());
						} else {
							if (mixNoise < mix2Height) {
								primer.setBlockState(x, k, z, mix2Block);
							} else if (mixNoise > mixHeight) {
								primer.setBlockState(x, k, z, mixBlock);
							} else {
								primer.setBlockState(x, k, z, topBlock);
							}
						}
					} else if (depth < 6) {
						if (cliff == 1) {
							primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
						} else if (cliff == 2) {
							primer.setBlockState(x, k, z, getShadowStoneBlock(rtgWorld, i, j, x, z, k));
						} else {
							primer.setBlockState(x, k, z, fillerBlock);
						}
					}
				}
			}
		}
	}

	@Override
	public void initDecos() {
		this.addDecoCollection(new DecoCollectionForestConiferous(this.getConfig()));
	}
}
