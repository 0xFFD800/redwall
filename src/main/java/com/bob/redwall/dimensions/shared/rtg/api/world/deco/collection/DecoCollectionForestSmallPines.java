package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeType;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGPiceaSitchensis;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTrees;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionForestSmallPines extends DecoCollectionBase {

    public DecoCollectionForestSmallPines(BiomeConfig config) {

        super(config);

        TreeRTG sitchensisTree = new TreeRTGPiceaSitchensis();
        sitchensisTree.setLogBlock(Blocks.LOG.getDefaultState());
        sitchensisTree.setLeavesBlock(Blocks.LEAVES.getDefaultState());
        sitchensisTree.setMinTrunkSize(4);
        sitchensisTree.setMaxTrunkSize(10);
        sitchensisTree.setMinCrownSize(6);
        sitchensisTree.setMaxCrownSize(14);
        this.addTree(sitchensisTree);

        DecoTree oakPine = new DecoTree(sitchensisTree);
        oakPine.setStrengthFactorForLoops(3f);
        oakPine.setTreeType(TreeType.RTG_TREE);
        oakPine.setTreeCondition(TreeCondition.RANDOM_CHANCE);
        oakPine.setTreeConditionChance(4);
        oakPine.setMaxY(110);

        DecoTree vanillaTrees = new DecoTree(new WorldGenTrees(false));
        vanillaTrees.setStrengthFactorForLoops(3f);
        vanillaTrees.setTreeType(TreeType.WORLDGEN);
        vanillaTrees.setTreeCondition(TreeCondition.RANDOM_CHANCE);
        vanillaTrees.setTreeConditionChance(4);
        vanillaTrees.setMaxY(110);

        DecoHelperRandomSplit decoHelperRandomSplit = new DecoHelperRandomSplit();
        decoHelperRandomSplit.decos = new DecoBase[]{oakPine, vanillaTrees};
        decoHelperRandomSplit.chances = new int[]{8, 4};
        this.addDeco(decoHelperRandomSplit);
    }
}
