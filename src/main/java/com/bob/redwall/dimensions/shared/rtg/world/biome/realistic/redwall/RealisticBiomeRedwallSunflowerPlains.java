package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.CliffCalculator;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBaseBiomeDecorations;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.heighteffect.GroundEffect;
import com.bob.redwall.init.BiomeHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallSunflowerPlains extends RealisticBiomeRedwallBase {
	public static Biome biome = BiomeHandler.redwall_plains_sunflower;
	public static Biome river = BiomeHandler.redwall_river;

	public RealisticBiomeRedwallSunflowerPlains() {
		super(biome, river);
	}

	@Override
	public void initConfig() {}

	@Override
	public TerrainBase initTerrain() {
		return new TerrainVanillaSunflowerPlains();
	}

	public class TerrainVanillaSunflowerPlains extends TerrainBase {
		private GroundEffect groundEffect = new GroundEffect(4f);

		public TerrainVanillaSunflowerPlains() {}

		@Override
		public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
			// return terrainPlains(x, y, simplex, river, 160f, 10f, 60f, 200f, 65f);
			return riverized(65f + groundEffect.added(rtgWorld, x, y), river);
		}
	}

	@Override
	public SurfaceBase initSurface() {
		return new SurfaceVanillaSunflowerPlains(config, biome.topBlock, biome.fillerBlock);
	}

	public class SurfaceVanillaSunflowerPlains extends SurfaceBase {
		public SurfaceVanillaSunflowerPlains(BiomeConfig config, IBlockState top, IBlockState filler) {
			super(config, top, filler);
		}

		@Override
		public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
			Random rand = rtgWorld.rand();
			float c = CliffCalculator.calc(x, z, noise);
			boolean cliff = c > 1.4f ? true : false;

			for (int k = 255; k > -1; k--) {
				Block b = primer.getBlockState(x, k, z).getBlock();
				if (b == Blocks.AIR) {
					depth = -1;
				} else if (b == Blocks.STONE) {
					depth++;

					if (cliff) {
						if (depth > -1 && depth < 2) {
							if (rand.nextInt(3) == 0) {
								primer.setBlockState(x, k, z, hcCobble(rtgWorld, i, j, x, z, k));
							} else {
								primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
							}
						} else if (depth < 10) {
							primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
						}
					} else {
						if (depth == 0 && k > 61) {
							primer.setBlockState(x, k, z, topBlock);
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
		DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
		this.addDeco(decoBaseBiomeDecorations);
	}
}
