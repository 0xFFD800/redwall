package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.util.CliffCalculator;
import com.bob.redwall.dimensions.shared.rtg.api.util.PlateauStep;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SimplexOctave;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionDesertRiver;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection.DecoCollectionMesa;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.heighteffect.VoronoiPlateauEffect;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallMesa extends RealisticBiomeRedwallBase {
	public static Biome biome = Biomes.DEEP_OCEAN;
	public static Biome river = BiomeHandler.redwall_river;

	public RealisticBiomeRedwallMesa() {
		super(biome, river);
	}

	@Override
	public void initConfig() {
		this.getConfig().ALLOW_SCENIC_LAKES.set(false);

		this.getConfig().addProperty(this.getConfig().ALLOW_CACTUS).set(false);
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK).set("");
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_BLOCK_META).set(0);
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_2_BLOCK).set("");
		this.getConfig().addProperty(this.getConfig().SURFACE_MIX_2_BLOCK_META).set(0);

		this.getConfig().addProperty(this.getConfig().ALLOW_PLATEAU_MODIFICATIONS).set(false);
		this.getConfig().addProperty(this.getConfig().PLATEAU_GRADIENT_BLOCK_ID).set("minecraft:stained_hardened_clay");
		this.getConfig().addProperty(this.getConfig().PLATEAU_GRADIENT_METAS).set(BiomeConfig.MESA_PLATEAU_GRADIENT_METAS);
		this.getConfig().addProperty(this.getConfig().PLATEAU_BLOCK_ID).set("minecraft:hardened_clay");
		this.getConfig().addProperty(this.getConfig().PLATEAU_BLOCK_META).set(0);
	}

	@Override
	public TerrainBase initTerrain() {
		return new TerrainRTGMesaPlateau(67);
		// return new TerrainVanillaMesaPlateau(true, 35f, 160f, 60f, 40f, 69f);
	}

	public static class TerrainRTGMesaPlateau extends TerrainBase {
		final PlateauStep step;
		final VoronoiPlateauEffect plateau;
		final int groundNoise;
		private SimplexOctave.Disk jitter = new SimplexOctave.Disk();
		private float jitterWavelength = 30;
		private float jitterAmplitude = 10;
		private float bumpinessMultiplier = 0.05f;
		private float bumpinessWavelength = 10f;
		private int bumpinessOctave = 2;

		public TerrainRTGMesaPlateau(float base) {
			plateau = new VoronoiPlateauEffect();
			step = new PlateauStep();
			step.finish = 0.4f;
			step.start = 0.25f;
			plateau.pointWavelength = 200;
			this.base = base;
			groundNoise = 4;
		}

		@Override
		public float generateNoise(IRTGWorld rtgWorld, int passedX, int passedY, float border, float river) {
			rtgWorld.simplex().riverJitter().evaluateNoise((float) passedX / jitterWavelength, (float) passedY / jitterWavelength, jitter);
			float x = (float) (passedX + jitter.deltax() * jitterAmplitude);
			float y = (float) (passedY + jitter.deltay() * jitterAmplitude);
			float simplex = plateau.added(rtgWorld, x, y);
			// if (simplex > river) simplex = river;
			float bordercap = border * 3.5f - 2.5f;
			if (bordercap > 1) bordercap = 1;
			float rivercap = 3f * river;
			if (rivercap > 1) rivercap = 1;
			simplex *= rivercap * bordercap;
			float bumpiness = rtgWorld.simplex().octave(bumpinessOctave).noise2(x / bumpinessWavelength, y / bumpinessWavelength) * bumpinessMultiplier;
			simplex += bumpiness;
			// if (simplex > bordercap) simplex = bordercap;
			float added = step.increase(simplex) / border;
			return riverized(base + TerrainBase.groundNoise(x, y, groundNoise, rtgWorld.simplex()), river) + added;
		}

	}

	public class TerrainVanillaMesaPlateau extends TerrainBase {
		private float[] height;
		private int heightLength;
		private float strength;

		/*
		 * Example parameters:
		 *
		 * allowed to generate rivers? riverGen = true
		 *
		 * canyon jump heights heightArray = new float[]{2.0f, 0.5f, 6.5f, 0.5f, 14.0f,
		 * 0.5f, 19.0f, 0.5f}
		 *
		 * strength of canyon jump heights heightStrength = 35f
		 *
		 * canyon width (cliff to cliff) canyonWidth = 160f
		 *
		 * canyon heigth (total heigth) canyonHeight = 60f
		 *
		 * canyon strength canyonStrength = 40f
		 *
		 */
		public TerrainVanillaMesaPlateau(boolean riverGen, float heightStrength, float canyonWidth, float canyonHeight, float canyonStrength, float baseHeight) {
			/**
			 * Values come in pairs per layer. First is how high to step up. Second is a
			 * value between 0 and 1, signifying when to step up.
			 */
			height = new float[] { 32.0f, 0.4f };
			/**
			 * lower values = smoother.
			 */
			strength = 10f;
			heightLength = height.length;
			base = baseHeight;
		}

		@Override
		public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
			return terrainPlateau(x, y, rtgWorld.simplex(), river, height, border, strength, heightLength, 100f, false);
		}
	}

	@Override
	public SurfaceBase initSurface() {
		return new SurfaceVanillaMesaPlateau(config, biome.topBlock, BlockUtil.getStateClay(1), 0);
	}

	@Override
	public void rReplace(ChunkPrimer primer, int i, int j, int x, int y, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
		this.rReplaceWithRiver(primer, i, j, x, y, depth, rtgWorld, noise, river, base);
	}

	@Override
	public int getExtraGoldGenCount() {
		return 20;
	}

	public class SurfaceVanillaMesaPlateau extends SurfaceBase {
		private int grassRaise = 0;
		private IBlockState mixBlock;
		private IBlockState mix2Block;

		public SurfaceVanillaMesaPlateau(BiomeConfig config, IBlockState top, IBlockState fill, int grassHeight) {
			super(config, top, fill);
			grassRaise = grassHeight;

			this.mixBlock = this.getConfigBlock(config.SURFACE_MIX_BLOCK.get(), config.SURFACE_MIX_BLOCK_META.get(), BlockUtil.getStateClay(1));
			this.mix2Block = this.getConfigBlock(config.SURFACE_MIX_2_BLOCK.get(), config.SURFACE_MIX_2_BLOCK_META.get(), Blocks.RED_SANDSTONE.getDefaultState());
		}

		@Override
		public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
			Random rand = rtgWorld.rand();
			float c = CliffCalculator.calc(x, z, noise);
			boolean cliff = c > 1.3f;
			Block b;

			for (int k = 255; k > -1; k--) {
				b = primer.getBlockState(x, k, z).getBlock();
				if (b == Blocks.AIR) {
					depth = -1;
				} else if (b == Blocks.STONE) {
					depth++;

					if (cliff) {
						primer.setBlockState(x, k, z, plateauBand.getPlateauBand(rtgWorld, RealisticBiomeRedwallMesa.this, i, k, j));
					} else {

						if (k > 74 + grassRaise) {
							if (depth == 0) {
								if (rand.nextInt(5) == 0) {
									primer.setBlockState(x, k, z, mix2Block);
								} else {
									primer.setBlockState(x, k, z, topBlock);
								}
							} else if (depth < 4) {
								primer.setBlockState(x, k, z, fillerBlock);
							}
						} else if (depth == 0 && k > 61) {
							int r = (int) ((k - (62 + grassRaise)) / 2f);
							if (rand.nextInt(r + 2) == 0) {
								primer.setBlockState(x, k, z, mixBlock);
							} else if (rand.nextInt((int) (r / 2f) + 2) == 0) {
								primer.setBlockState(x, k, z, mix2Block);
							} else {
								primer.setBlockState(x, k, z, topBlock);
							}
						} else if (depth < 4) {
							primer.setBlockState(x, k, z, fillerBlock);
						}
					}
				}
			}
		}
	}

	@Override
	public void initDecos() {
		this.addDecoCollection(new DecoCollectionDesertRiver(this.getConfig()));
		this.addDecoCollection(new DecoCollectionMesa(this.getConfig()));
	}

	@Override
	public int waterSurfaceLakeChance() {
		return 30;
	}
}
