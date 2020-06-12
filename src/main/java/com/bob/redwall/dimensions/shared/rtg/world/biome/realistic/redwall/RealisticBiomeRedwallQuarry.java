package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.util.CliffCalculator;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.OpenSimplexNoise;
import com.bob.redwall.dimensions.shared.rtg.api.util.noise.SimplexOctave;
import com.bob.redwall.dimensions.shared.rtg.api.world.IRTGWorld;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
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
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallQuarry extends RealisticBiomeRedwallBase {
    public static Biome biome = BiomeHandler.redwall_quarry;
    public static Biome river = BiomeHandler.redwall_river;

    public RealisticBiomeRedwallQuarry() {
        super(biome, river);

        // Prevent ores from messing up the surface.
        this.rDecorator().graniteSize = 0;
        this.rDecorator().dioriteSize = 0;
        //this.rDecorator().andesiteSize = 0; // This looks good.
        //this.rDecorator().gravelSize = 0; // So does this.
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
        return new TerrainRTGMesaPlateau(67);
        //return new TerrainVanillaMesaPlateau(true, 35f, 160f, 60f, 40f, 69f);
    }

    public static class TerrainRTGMesaPlateau extends TerrainBase {
        final int groundNoise;
        private SimplexOctave.Disk jitter = new SimplexOctave.Disk();
        private float jitterWavelength = 30;
        private float jitterAmplitude = 10;
        
        public TerrainRTGMesaPlateau(float base) {
            this.base = base;
            groundNoise = 4;
        }

        @Override
        public float generateNoise(IRTGWorld rtgWorld, int passedX, int passedY, float border, float river) {
            rtgWorld.simplex().riverJitter().evaluateNoise((float) passedX / jitterWavelength, (float) passedY / jitterWavelength, jitter);
            float x = (float)(passedX + jitter.deltax() * jitterAmplitude);
            float y = (float)(passedY + jitter.deltay() * jitterAmplitude);
            float added = -32F / (border + 0.001F);
            float next = base + TerrainBase.groundNoise(x, y, groundNoise, rtgWorld.simplex());
            return next + added;
        }
    }

    @Override
    public SurfaceBase initSurface() {
        return new SurfaceVanillaForest(config, Blocks.GRASS.getDefaultState(), Blocks.RED_SANDSTONE.getDefaultState(), 0f, 1.5f, 60f, 65f, 1.5f, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND), 0.9f, Blocks.RED_SANDSTONE.getDefaultState(), 0.65f, BlockUtil.getStateDirt(1), 0.1f);
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
        private IBlockState mix3Block;
        private float mix3Height;

        public SurfaceVanillaForest(BiomeConfig config, IBlockState top, IBlockState fill, float minCliff, float stoneCliff, float stoneHeight, float stoneStrength, float clayCliff, IBlockState mix, float mixHeight, IBlockState mix2, float mix2Height, IBlockState mix3, float mix3Height) {
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
            this.mix3Block = mix3;
            this.mix3Height = mix3Height;
        }

        @Override
        public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, IRTGWorld rtgWorld, float[] noise, float river, Biome[] base) {
            Random rand = rtgWorld.rand();
            OpenSimplexNoise simplex = rtgWorld.simplex();
            float c = CliffCalculator.calc(x, z, noise);
            int cliff = 0;
            Block b;
            
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
                        } else if (k < 31) {
                            if (k < 30) {
                                primer.setBlockState(x, k, z, fillerBlock);
                            } else {
                                primer.setBlockState(x, k, z, topBlock);
                            }
                        } else {
                            float mixRandom = rand.nextFloat();

                            if(mixRandom < mix3Height) {
                                primer.setBlockState(x, k, z, mix3Block);
                            } else if (mixRandom < mix2Height) {
                                primer.setBlockState(x, k, z, mix2Block);
                            } else if (mixRandom < mixHeight) {
                                primer.setBlockState(x, k, z, mixBlock);
                            } else {
                                primer.setBlockState(x, k, z, topBlock);
                            }
                        }
                    } else if (depth < 6) {
                        if (cliff == 1) {
                            primer.setBlockState(x, k, z, hcStone(rtgWorld, i, j, x, z, k));
                        } else if (cliff == 2) {
                            primer.setBlockState(x, k, z, Blocks.RED_SANDSTONE.getDefaultState());
                        } else {
                            primer.setBlockState(x, k, z, fillerBlock);
                        }
                    }
                } else if(b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
                	primer.setBlockState(x, k, z, Blocks.AIR.getDefaultState());
                	depth = -1;
                }
            }
        }
    }

    @Override
    public void initDecos() {
        // Shrubs.
        DecoShrub decoShrubOak = new DecoShrub();
        decoShrubOak.setLogBlock(Blocks.LOG.getDefaultState());
        decoShrubOak.setLeavesBlock(Blocks.LEAVES.getDefaultState());
        decoShrubOak.setMaxY(200);
        decoShrubOak.setLoops(1);
        decoShrubOak.setChance(2);
        this.addDeco(decoShrubOak);

        DecoShrub decoShrubMaple = new DecoShrub();
        decoShrubMaple.setLogBlock(BlockHandler.maple_log.getDefaultState());
        decoShrubMaple.setLeavesBlock(BlockHandler.maple_leaves.getDefaultState());
        decoShrubMaple.setMaxY(200);
        decoShrubMaple.setLoops(1);
        decoShrubMaple.setChance(2);
        this.addDeco(decoShrubMaple);

        // Lots of grass, but not as much as vanilla.
        DecoGrass decoGrass = new DecoGrass();
        decoGrass.setMinY(60);
        decoGrass.setMaxY(128);
        decoGrass.setLoops(6);
        this.addDeco(decoGrass);

        // Very rare fat oak/birch trees.

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
        oakTrees.setTreeConditionChance(96);

        TreeRTG roburTree2 = new TreeRTGQuercusRobur();
        roburTree2.setLogBlock(BlockHandler.maple_log.getDefaultState());
        roburTree2.setLeavesBlock(BlockHandler.maple_leaves.getDefaultState());
        roburTree2.setMinTrunkSize(3);
        roburTree2.setMaxTrunkSize(5);
        roburTree2.setMinCrownSize(7);
        roburTree2.setMaxCrownSize(9);
        this.addTree(roburTree2);

        DecoTree mapleTrees = new DecoTree(roburTree2);
        mapleTrees.setTreeType(DecoTree.TreeType.RTG_TREE);
        mapleTrees.setTreeCondition(DecoTree.TreeCondition.NOISE_GREATER_AND_RANDOM_CHANCE);
        mapleTrees.setDistribution(new DecoTree.Distribution(100f, 6f, 0.8f));
        mapleTrees.setTreeConditionNoise(0.4f);
        mapleTrees.setTreeConditionChance(96);

        this.addDeco(new DecoHelperThisOrThat(4, DecoHelperThisOrThat.ChanceType.NOT_EQUALS_ZERO, oakTrees, mapleTrees));

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.setBoulderBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState());
        decoBoulder.setChance(20);
        decoBoulder.setMaxY(95);
        decoBoulder.setStrengthFactor(2f);
        this.addDeco(decoBoulder);

        // Vanilla trees look awful in this biome, so let's make sure they don't generate.
        //DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
        //this.addDeco(decoBaseBiomeDecorations);
    }

    @Override
    public int waterSurfaceLakeChance() {
        return 0;
    }

    @Override
    public int lavaSurfaceLakeChance() {
        return 0;
    }
    
    @Override
    public boolean noWaterBelowSeaLevel() {
    	return true;
    }
}
