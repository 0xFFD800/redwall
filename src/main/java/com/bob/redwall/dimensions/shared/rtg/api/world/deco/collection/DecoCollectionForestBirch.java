package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import static com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree.LogCondition.RANDOM_CHANCE;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCrop;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFlowersRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoSandstoneSlab;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGBetulaPapyrifera;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTrees;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionForestBirch extends DecoCollectionBase {
    public DecoCollectionForestBirch(BiomeConfig config) {
        super(config);
        this
            .addDeco(tallBirchTrees())
            .addDeco(randomTrees())
            .addDeco(logs(), config.ALLOW_LOGS.get()) // Add some fallen birch trees.
            .addDeco(shrubsBirch()) // Oak shrubs to fill in the blanks.
            .addDeco(bushes())
            .addDeco(flowers()) // Only 1-block tall flowers so we can see the trees better.
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

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.setBoulderBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState());
        decoBoulder.setChance(20);
        decoBoulder.setMaxY(95);
        decoBoulder.setStrengthFactor(2f);
        this.addDeco(decoBoulder);

        DecoSandstoneSlab decoSandstoneSlab = new DecoSandstoneSlab();
        decoSandstoneSlab.setChance(200);
        decoSandstoneSlab.setMaxY(95);
        decoSandstoneSlab.setStrengthFactor(2f);
        this.addDeco(decoSandstoneSlab);
    }

    private DecoHelperRandomSplit randomTrees() {
        return new DecoHelperRandomSplit()
            .setDecos(new DecoBase[]{tallBirchTrees(), vanillaTrees()})
            .setChances(new int[]{10, 20});
    }

    private DecoTree tallBirchTrees() {
        TreeRTG birchTree = new TreeRTGBetulaPapyrifera()
            .setLogBlock(BlockUtil.getStateLog(2))
            .setLeavesBlock(BlockUtil.getStateLeaf(2))
            .setMinTrunkSize(4)
            .setMaxTrunkSize(10)
            .setMinCrownSize(8)
            .setMaxCrownSize(19);

        this.addTree(birchTree);

        return new DecoTree(birchTree)
            .setStrengthFactorForLoops(3f)
            .setTreeType(DecoTree.TreeType.RTG_TREE)
            .setTreeCondition(DecoTree.TreeCondition.ALWAYS_GENERATE)
            .setMaxY(100);
    }

    private DecoTree vanillaTrees() {
        return new DecoTree(new WorldGenTrees(false))
            .setTreeType(DecoTree.TreeType.WORLDGEN)
            .setLogBlock(BlockUtil.getStateLog(2))
            .setLeavesBlock(BlockUtil.getStateLeaf(2))
            .setStrengthFactorForLoops(3f)
            .setTreeCondition(DecoTree.TreeCondition.RANDOM_CHANCE)
            .setMaxY(100);
    }

    private DecoFallenTree logs() {
        return new DecoFallenTree()
            .setLogCondition(RANDOM_CHANCE)
            .setLogConditionChance(8)
            .setLogBlock(BlockUtil.getStateLog(2))
            .setLeavesBlock(BlockUtil.getStateLeaf(2))
            .setMinSize(3)
            .setMaxSize(6);
    }

    private DecoShrub shrubsBirch() {
        return new DecoShrub()
            .setMaxY(120)
            .setUseDefaultRandom(false)
            .setLogBlock(BlockUtil.getStateLog(2))
            .setLeavesBlock(BlockUtil.getStateLeaf(2))
            .setRandomLogBlocks(new IBlockState[] {BlockUtil.getStateLog(2)})
            .setRandomLeavesBlocks(new IBlockState[] {BlockUtil.getStateLeaf(2)})
            .setStrengthFactor(3f);
    }

    private DecoFlowersRTG flowers() {
        return new DecoFlowersRTG()
            .setFlowers(new int[]{3, 6})
            .setMaxY(128)
            .setStrengthFactor(12f);
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
