package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import static com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree.LogCondition.NOISE_GREATER_AND_RANDOM_CHANCE;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCrop;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoDeadBush;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoPond;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoSandstoneSlab;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGCeibaPentandra;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGCeibaRosea;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGRhizophoraMucronata;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.init.Blocks;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionForestAsh extends DecoCollectionBase {

    // Tends to return values between -3f to 5f, with some overflow.
    private DecoTree.Distribution forestDistribution = new DecoTree.Distribution(100f, 6f, 0.8f);

    private float distributionNoiseMin = -3.2f;
    private float distributionNoiseMax = 5.4f;

    private int treesMinY = 63;
    private int treesMaxY = 225;

    public DecoCollectionForestAsh(BiomeConfig config, int treesMinY, int treesMaxY) {
        super(config);

        this.treesMinY = treesMinY;
        this.treesMaxY = treesMaxY;

        this
            .addDeco(ponds(), config.ALLOW_PONDS_WATER.get())
            .addDeco(mucronataTrees(treesMinY, treesMaxY))
            .addDeco(pentandraTrees(treesMinY, treesMaxY))
            .addDeco(roseaTrees(treesMinY, treesMaxY))
            .addDeco(logs(), config.ALLOW_LOGS.get())
            .addDeco(ashShrubs())
            .addDeco(bushes())
            .addDeco(boulders())
            .addDeco(deadBushes())
        ;

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

        DecoSandstoneSlab decoSandstoneSlab = new DecoSandstoneSlab();
        decoSandstoneSlab.setChance(200);
        decoSandstoneSlab.setMaxY(95);
        decoSandstoneSlab.setStrengthFactor(2f);
        this.addDeco(decoSandstoneSlab);
    }

    private DecoTree mucronataTrees(int minY, int maxY) {

        TreeRTG mucronataTree = new TreeRTGRhizophoraMucronata(3, 4, 13f, 0.32f, 0.1f);
        mucronataTree.setLogBlock(BlockHandler.ash_log.getDefaultState());
        mucronataTree.setLeavesBlock(BlockHandler.ash_leaves.getDefaultState());
        mucronataTree.setMinTrunkSize(2);
        mucronataTree.setMaxTrunkSize(3);
        mucronataTree.setMinCrownSize(8);
        mucronataTree.setMaxCrownSize(16);
        mucronataTree.setNoLeaves(false);
        this.addTree(mucronataTree);

        return new DecoTree(mucronataTree)
            .setTreeType(DecoTree.TreeType.RTG_TREE)
            .setDistribution(forestDistribution)
            .setTreeCondition(DecoTree.TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE)
            .setTreeConditionNoise(distributionNoiseMin)
            .setTreeConditionNoise2(distributionNoiseMax)
            .setTreeConditionChance(5)
            .setStrengthFactorForLoops(10f)
            .setMinY(minY)
            .setMaxY(maxY)
            .setScatter(new DecoTree.Scatter(16, 0));
    }

    private DecoTree pentandraTrees(int minY, int maxY) {

        TreeRTG pentandraTree = new TreeRTGCeibaPentandra(13f, 3, 0.32f, 0.1f);
        pentandraTree.setLogBlock(BlockHandler.ash_log.getDefaultState());
        pentandraTree.setLeavesBlock(BlockHandler.ash_leaves.getDefaultState());
        pentandraTree.setMinTrunkSize(2);
        pentandraTree.setMaxTrunkSize(3);
        pentandraTree.setMinCrownSize(8);
        pentandraTree.setMaxCrownSize(16);
        pentandraTree.setNoLeaves(false);
        this.addTree(pentandraTree);

        return new DecoTree(pentandraTree)
            .setTreeType(DecoTree.TreeType.RTG_TREE)
            .setDistribution(forestDistribution)
            .setTreeCondition(DecoTree.TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE)
            .setTreeConditionNoise(distributionNoiseMin)
            .setTreeConditionNoise2(distributionNoiseMax)
            .setTreeConditionChance(5)
            .setStrengthFactorForLoops(10f)
            .setMinY(minY)
            .setMaxY(maxY)
            .setScatter(new DecoTree.Scatter(16, 0));
    }

    private DecoTree roseaTrees(int minY, int maxY) {

        TreeRTG roseaTree = new TreeRTGCeibaRosea(16f, 5, 0.32f, 0.1f);
        roseaTree.setLogBlock(BlockHandler.ash_log.getDefaultState());
        roseaTree.setLeavesBlock(BlockHandler.ash_leaves.getDefaultState());
        roseaTree.setMinTrunkSize(2);
        roseaTree.setMaxTrunkSize(3);
        roseaTree.setMinCrownSize(8);
        roseaTree.setMaxCrownSize(16);
        roseaTree.setNoLeaves(false);
        this.addTree(roseaTree);

        return new DecoTree(roseaTree)
            .setTreeType(DecoTree.TreeType.RTG_TREE)
            .setDistribution(forestDistribution)
            .setTreeCondition(DecoTree.TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE)
            .setTreeConditionNoise(distributionNoiseMin)
            .setTreeConditionNoise2(distributionNoiseMax)
            .setTreeConditionChance(5)
            .setStrengthFactorForLoops(10f)
            .setMinY(minY)
            .setMaxY(maxY)
            .setScatter(new DecoTree.Scatter(16, 0));
    }

    private DecoPond ponds() {
        return new DecoPond()
            .setChunksPerPond(10)
            .setMaxY(67)
            .setLoops(1);
    }

    private DecoFallenTree logs() {
        return new DecoFallenTree()
            .setDistribution(new DecoFallenTree.Distribution(80f, 60f, -15f))
            .setLogCondition(NOISE_GREATER_AND_RANDOM_CHANCE)
            .setLogConditionChance(16)
            .setLogConditionNoise(0f)
            .setLogBlock(BlockHandler.ash_log.getDefaultState())
            .setLeavesBlock(BlockHandler.elm_log.getDefaultState())
            .setMinSize(4)
            .setMaxSize(9)
            .setMaxY(72);
    }

    private DecoShrub ashShrubs() {
        return new DecoShrub()
            .setLogBlock(BlockHandler.ash_log.getDefaultState())
            .setLeavesBlock(BlockHandler.ash_leaves.getDefaultState())
            .setStrengthFactor(40f)
            .setMinY(treesMinY)
            .setMaxY(treesMaxY);
    }

    private DecoBoulder boulders() {
        return new DecoBoulder()
            .setBoulderBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState())
            .setChance(12)
            .setMaxY(80)
            .setStrengthFactor(2f);
    }

    private DecoDeadBush deadBushes() {
        return new DecoDeadBush()
            .setChance(2)
            .setStrengthFactor(2f);
    }

    private DecoHelperRandomSplit bushes() {
        return new DecoHelperRandomSplit()
        		.setDecos(new DecoBase[] {
        				new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.blackberry_bush),
        				new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.blueberry_bush),
        				new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.raspberry_bush),
        				new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.strawberry_bush),
        				new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.elderberry_bush),
        				new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.wildberry_bush)})
        		.setChances(new int[] {1, 1, 1, 1, 1, 10});
    }
}
