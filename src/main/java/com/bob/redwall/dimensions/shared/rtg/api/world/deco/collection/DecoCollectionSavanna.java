package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree.LogCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeType;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGAcaciaBucheri;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.init.Blocks;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionSavanna extends DecoCollectionBase {

    public DecoCollectionSavanna(BiomeConfig config) {

        super(config);

        DecoShrub acaciaShrub = new DecoShrub();
        acaciaShrub.setLogBlock(Blocks.LOG2.getDefaultState());
        acaciaShrub.setLeavesBlock(Blocks.LEAVES2.getDefaultState());
        acaciaShrub.setMaxY(160);
        acaciaShrub.setStrengthFactor(2f);
        acaciaShrub.setChance(12);
        this.addDeco(acaciaShrub);

        DecoFallenTree decoFallenTree = new DecoFallenTree();
        decoFallenTree.setLoops(1);
        decoFallenTree.getDistribution().setNoiseDivisor(100f);
        decoFallenTree.getDistribution().setNoiseFactor(6f);
        decoFallenTree.getDistribution().setNoiseAddend(0.8f);
        decoFallenTree.setLogCondition(LogCondition.NOISE_GREATER_AND_RANDOM_CHANCE);
        decoFallenTree.setLogConditionNoise(0f);
        decoFallenTree.setLogConditionChance(36);
        decoFallenTree.setLogBlock(Blocks.LOG2.getDefaultState());
        decoFallenTree.setLeavesBlock(Blocks.LEAVES2.getDefaultState());
        decoFallenTree.setMinSize(3);
        decoFallenTree.setMaxSize(6);
        this.addDeco(decoFallenTree, config.ALLOW_LOGS.get());

        TreeRTG bucheriTree = new TreeRTGAcaciaBucheri();
        bucheriTree.setLogBlock(Blocks.LOG2.getDefaultState());
        bucheriTree.setLeavesBlock(Blocks.LEAVES2.getDefaultState());
        bucheriTree.setMinTrunkSize(4);
        bucheriTree.setMaxTrunkSize(9);
        this.addTree(bucheriTree);

        DecoTree bucheriTrees = new DecoTree(bucheriTree);
        bucheriTrees.setLoops(1);
        bucheriTrees.setTreeType(TreeType.RTG_TREE);
        bucheriTrees.getDistribution().setNoiseDivisor(80f);
        bucheriTrees.getDistribution().setNoiseFactor(60f);
        bucheriTrees.getDistribution().setNoiseAddend(-15f);
        bucheriTrees.setTreeCondition(TreeCondition.NOISE_GREATER_AND_RANDOM_CHANCE);
        bucheriTrees.setTreeConditionNoise(-0.4f);
        bucheriTrees.setTreeConditionChance(28);
        this.addDeco(bucheriTrees);

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.setBoulderBlock(Blocks.COBBLESTONE.getDefaultState());
        decoBoulder.setChance(32);
        decoBoulder.setMaxY(95);
        this.addDeco(decoBoulder);

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
        decoTallGrass.setLoops(70);
        decoTallGrass.setGrassChance(10);
        this.addDeco(decoTallGrass);
    }
}
