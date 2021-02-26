package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.blocks.plants.treeleaves.BlockModLeaves;
import com.bob.redwall.dimensions.redwall.RedwallWorldProvider;
import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.CliffCalculator;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCrop;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFlowersRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperThisOrThat;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGQuercusRobur;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallHills extends RealisticBiomeRedwallBase {
	public static Biome biome = BiomeHandler.redwall_hills;
	public static Biome river = BiomeHandler.redwall_river;

	public RealisticBiomeRedwallHills() {
		super(biome, river);
	}

	@Override
	public void initConfig() {
		this.getConfig().addProperty(this.getConfig().ALLOW_WHEAT).set(true);
		this.getConfig().addProperty(this.getConfig().WHEAT_CHANCE).set(50);
		this.getConfig().addProperty(this.getConfig().WHEAT_MIN_Y).set(63);
		this.getConfig().addProperty(this.getConfig().WHEAT_MAX_Y).set(255);
	}

	@Override
	public TerrainBase initTerrain() {
		return new TerrainVanillaPlains(10f, 80f, 68f, 200f);
	}

	public class TerrainVanillaPlains extends TerrainBase {
		private float start;
		private float height;
		private float width;

		public TerrainVanillaPlains(float hillStart, float landHeight, float baseHeight, float hillWidth) {
			start = hillStart;
			height = landHeight;
			base = baseHeight;
			width = hillWidth;
		}

		@Override
		public float generateNoise(IRTGWorld rtgWorld, int x, int y, float border, float river) {
			return terrainHighland(x, y, rtgWorld.simplex(), rtgWorld.cell(), river, start, width, height, base - 62f);
		}
	}

	@Override
	public SurfaceBase initSurface() {
		return new SurfaceVanillaPlains(config, biome.topBlock, biome.fillerBlock);
	}

	public class SurfaceVanillaPlains extends SurfaceBase {
		public SurfaceVanillaPlains(BiomeConfig config, IBlockState top, IBlockState filler) {
			super(config, top, filler);
		}

		@Override
		public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
			Random rand = rtgWorld.rand();
			float c = CliffCalculator.calc(x, z, noise);
			boolean cliff = c > 1.4f ? true : false;

            OpenSimplexNoise simplex = rtgWorld.simplex();
            float mixNoise = simplex.noise2(i / 12f, j / 12f);
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
						if (depth == 0) {
							 if (k < RedwallWorldProvider.SEA_LEVEL - 1) {
								if(mixNoise < 0.6F) primer.setBlockState(x, k, z, Blocks.SAND.getDefaultState());
								if(mixNoise < -0.4F) primer.setBlockState(x, k, z, Blocks.CLAY.getDefaultState());
								else primer.setBlockState(x, k, z, Blocks.GRAVEL.getDefaultState());
							} else primer.setBlockState(x, k, z, topBlock);
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
		// Sparse wheat
		DecoCrop decoCropWheat = new DecoCrop();
		decoCropWheat.setSize(8);
		decoCropWheat.setDensity(5);
		decoCropWheat.setChance(this.getConfig().WHEAT_CHANCE.get());
		decoCropWheat.setType(3);
		decoCropWheat.setWater(false);
		decoCropWheat.setMinY(this.getConfig().WHEAT_MIN_Y.get());
		decoCropWheat.setMaxY(this.getConfig().WHEAT_MAX_Y.get());
		this.addDeco(decoCropWheat, this.getConfig().ALLOW_WHEAT.get());

		// Very sparse shrubs.
		DecoShrub decoShrubOak = new DecoShrub();
		decoShrubOak.setLogBlock(Blocks.LOG.getDefaultState());
		decoShrubOak.setLeavesBlock(Blocks.LEAVES.getDefaultState());
		decoShrubOak.setMaxY(110);
		decoShrubOak.setLoops(1);
		decoShrubOak.setChance(36);
		this.addDeco(decoShrubOak);

		// The occasional flower.
		DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
		decoFlowersRTG.setFlowers(new int[] { 0, 2, 3, 4, 5, 6, 7, 8, 9 });
		decoFlowersRTG.setMaxY(128);
		decoFlowersRTG.setStrengthFactor(2f);
		this.addDeco(decoFlowersRTG);

		// Lots of grass, but not as much as vanilla.
		DecoGrass decoGrass = new DecoGrass();
		decoGrass.setMinY(60);
		decoGrass.setMaxY(128);
		decoGrass.setLoops(3);
		this.addDeco(decoGrass);

		DecoGrass decoShortGrass = new DecoGrass(BlockHandler.shortgrass.getDefaultState());
		decoShortGrass.setMinY(60);
		decoShortGrass.setMaxY(128);
		decoShortGrass.setLoops(16);
		this.addDeco(decoShortGrass);

		DecoGrassDoubleTallgrass decoTallGrass = new DecoGrassDoubleTallgrass();
		decoTallGrass.setMaxY(128);
		decoTallGrass.setLoops(40);
		decoTallGrass.setGrassChance(10);
		this.addDeco(decoTallGrass);

		// Very rare fat oak/maple trees.

		TreeRTG roburTree1 = new TreeRTGQuercusRobur();
		roburTree1.setLogBlock(Blocks.LOG.getDefaultState());
		roburTree1.setLeavesBlock(Blocks.LEAVES.getDefaultState());
		roburTree1.setMinTrunkSize(3);
		roburTree1.setMaxTrunkSize(5);
		roburTree1.setMinCrownSize(7);
		roburTree1.setMaxCrownSize(9);
		this.addTree(roburTree1);

		DecoTree oakTrees = new DecoTree(roburTree1);
		oakTrees.setTreeType(DecoTree.TreeType.RTG_TREE);
		oakTrees.setTreeCondition(DecoTree.TreeCondition.NOISE_GREATER_AND_RANDOM_CHANCE);
		oakTrees.setDistribution(new DecoTree.Distribution(100f, 6f, 0.8f));
		oakTrees.setTreeConditionNoise(0.4f);
		oakTrees.setTreeConditionChance(48);

		TreeRTG roburTree2 = new TreeRTGQuercusRobur();
		roburTree2.setLogBlock(BlockHandler.maple_log.getDefaultState());
		roburTree2.setLeavesBlock(BlockHandler.maple_leaves.getDefaultState().withProperty(BlockModLeaves.CHECK_DECAY, false));
		roburTree2.setMinTrunkSize(3);
		roburTree2.setMaxTrunkSize(5);
		roburTree2.setMinCrownSize(7);
		roburTree2.setMaxCrownSize(9);
		this.addTree(roburTree2);

		DecoTree birchTrees = new DecoTree(roburTree2);
		birchTrees.setTreeType(DecoTree.TreeType.RTG_TREE);
		birchTrees.setTreeCondition(DecoTree.TreeCondition.NOISE_GREATER_AND_RANDOM_CHANCE);
		birchTrees.setDistribution(new DecoTree.Distribution(100f, 6f, 0.8f));
		birchTrees.setTreeConditionNoise(0.4f);
		birchTrees.setTreeConditionChance(48);

		this.addDeco(new DecoHelperThisOrThat(4, DecoHelperThisOrThat.ChanceType.NOT_EQUALS_ZERO, oakTrees, birchTrees));

		DecoBoulder decoBoulder = new DecoBoulder();
		decoBoulder.setBoulderBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState());
		decoBoulder.setChance(20);
		decoBoulder.setMaxY(95);
		decoBoulder.setStrengthFactor(2f);
		this.addDeco(decoBoulder);

		// Vanilla trees look awful in this biome, so let's make sure they don't
		// generate.
		// DecoBaseBiomeDecorations decoBaseBiomeDecorations = new
		// DecoBaseBiomeDecorations();
		// this.addDeco(decoBaseBiomeDecorations);
	}
}
