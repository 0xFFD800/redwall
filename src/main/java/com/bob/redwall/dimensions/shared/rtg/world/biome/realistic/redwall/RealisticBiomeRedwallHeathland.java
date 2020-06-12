package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
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
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeType;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperThisOrThat;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGPiceaPungens;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGQuercusRobur;
import com.bob.redwall.dimensions.shared.rtg.api.world.surface.SurfaceBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.terrain.TerrainBase;
import com.bob.redwall.init.BiomeHandler;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class RealisticBiomeRedwallHeathland extends RealisticBiomeRedwallBase {
    public static Biome biome = BiomeHandler.redwall_heathland;
    public static Biome river = BiomeHandler.redwall_heathland;

    public RealisticBiomeRedwallHeathland() {
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
        return new TerrainScrubland();
    }

    public class TerrainScrubland extends TerrainBase {
        private float hillStrength = 12f;// this needs to be linked to the

        public TerrainScrubland() { }

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
        return new SurfaceVanillaForest(
            config, Blocks.GRASS.getDefaultState(), Blocks.DIRT.getDefaultState(),
            0f, 1.5f, 60f, 65f, 1.5f,
            Blocks.SAND.getDefaultState(), 0.5f, Blocks.GRAVEL.getDefaultState(), 0.25f, BlockUtil.getStateDirt(1), 0.2f
        );
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
                        } else if (k < 62) {
                            if (k < 61) {
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
        //Sparse wheat
        DecoCrop decoCropWheat = new DecoCrop();
        decoCropWheat.setSize(8);
        decoCropWheat.setDensity(5);
        decoCropWheat.setChance(this.getConfig().WHEAT_CHANCE.get());
        decoCropWheat.setType(3);
        decoCropWheat.setWater(false);
        decoCropWheat.setMinY(this.getConfig().WHEAT_MIN_Y.get());
        decoCropWheat.setMaxY(this.getConfig().WHEAT_MAX_Y.get());
        this.addDeco(decoCropWheat, this.getConfig().ALLOW_WHEAT.get());

        // Shrubs.
        DecoShrub decoShrubOak = new DecoShrub();
        decoShrubOak.setLogBlock(Blocks.LOG.getDefaultState());
        decoShrubOak.setLeavesBlock(Blocks.LEAVES.getDefaultState());
        decoShrubOak.setMaxY(110);
        decoShrubOak.setLoops(1);
        decoShrubOak.setChance(10);
        this.addDeco(decoShrubOak);

        DecoShrub decoShrubMaple = new DecoShrub();
        decoShrubMaple.setLogBlock(BlockHandler.maple_log.getDefaultState());
        decoShrubMaple.setLeavesBlock(BlockHandler.maple_leaves.getDefaultState());
        decoShrubMaple.setMaxY(110);
        decoShrubMaple.setLoops(1);
        decoShrubMaple.setChance(10);
        this.addDeco(decoShrubMaple);
        
        //Occasional grove of firs.
        TreeRTG piceaPungens = new TreeRTGPiceaPungens()
            .setLogBlock(BlockHandler.fir_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, EnumAxis.Y))
            .setLeavesBlock(BlockHandler.fir_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false))
            .setMinTrunkSize(1)
            .setMaxTrunkSize(3)
            .setMinCrownSize(3)
            .setMaxCrownSize(5);

        this.addTree(piceaPungens);

        this.addDeco(new DecoTree(piceaPungens)
                .setStrengthFactorForLoops(20f)
                .setTreeType(TreeType.RTG_TREE)
                .setDistribution(new DecoTree.Distribution(100f, 6f, 0.8f))
                .setTreeCondition(TreeCondition.NOISE_GREATER_AND_RANDOM_CHANCE)
                .setTreeConditionChance(2)
                .setTreeConditionNoise(0.6f)
                .setMaxY(100));

        // The occasional flower.
        DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.setFlowers(new int[]{0, 2, 3, 4, 5, 6, 7, 8, 9});
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
}
