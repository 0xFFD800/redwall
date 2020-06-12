package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.util.BlockUtil;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCrop;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree.LogCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFlowersRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGCeibaPentandra;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionSouthswardForest extends DecoCollectionBase {
	// Tends to return values between -3f to 5f, with some overflow.
    private DecoTree.Distribution forestDistribution = new DecoTree.Distribution(100f, 6f, 0.8f);

    public DecoCollectionSouthswardForest(BiomeConfig config) {

        super(config);

        this
	        .addDeco(pentandraTrees(66, 128)) // More short trees (on the other 'side' of the noise spectrum).
	        .addDeco(logs(), config.ALLOW_LOGS.get()) // Add some fallen trees of the oak and spruce variety (50/50 distribution).
            .addDeco(shrubs()) // Shrubs to fill in the blanks.
            .addDeco(bushes())
            .addDeco(flowers()) // Only 1-block tall flowers so we can see the trees better.
            .addDeco(grass()) // Grass filler.
        ;

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.setBoulderBlock(Blocks.MOSSY_COBBLESTONE.getDefaultState());
        decoBoulder.setChance(20);
        decoBoulder.setMaxY(95);
        decoBoulder.setStrengthFactor(2f);
        this.addDeco(decoBoulder);
    }
    
    private DecoHelperRandomSplit pentandraTrees(int minY, int maxY) {
    	return new DecoHelperRandomSplit()
    			.setDecos(new DecoTree[] {
    					this.pentandraTree(BlockUtil.getStateLog(0), BlockUtil.getStateLeaf(0), minY, maxY),
    					this.pentandraTree(BlockHandler.alder_log.getDefaultState(), BlockHandler.alder_leaves.getDefaultState(), minY, maxY),
    					this.pentandraTree(BlockHandler.ash_log.getDefaultState(), BlockHandler.ash_leaves.getDefaultState(), minY, maxY),
    					this.pentandraTree(BlockHandler.hornbeam_log.getDefaultState(), BlockHandler.hornbeam_leaves.getDefaultState(), minY, maxY),
    					this.pentandraTree(BlockHandler.maple_log.getDefaultState(), BlockHandler.maple_leaves.getDefaultState(), minY, maxY)
    			})
    			.setChances(new int[] {20, 10, 5, 7, 25});
    }
    
    private DecoTree pentandraTree(IBlockState log, IBlockState leaf, int minY, int maxY) {

        TreeRTG pentandraTree = new TreeRTGCeibaPentandra(13f, 3, 0.32f, 0.1f);
        pentandraTree.setLogBlock(log);
        pentandraTree.setLeavesBlock(leaf);
        pentandraTree.setMinTrunkSize(2);
        pentandraTree.setMaxTrunkSize(3);
        pentandraTree.setMinCrownSize(8);
        pentandraTree.setMaxCrownSize(16);
        pentandraTree.setNoLeaves(false);
        this.addTree(pentandraTree);

        return new DecoTree(pentandraTree)
            .setTreeType(DecoTree.TreeType.RTG_TREE)
            .setDistribution(forestDistribution)
            .setTreeCondition(DecoTree.TreeCondition.NOISE_GREATER_AND_RANDOM_CHANCE)
            .setTreeConditionNoise(-2f)
            .setTreeConditionChance(3)
            .setStrengthFactorForLoops(10f)
            .setMinY(minY)
            .setMaxY(maxY)
            .setScatter(new DecoTree.Scatter(16, 0));
    }

    private DecoHelperRandomSplit logs() {
        return new DecoHelperRandomSplit()
        		.setDecos(new DecoBase[]{
        				log(BlockUtil.getStateLog(0)),
        				log(BlockHandler.alder_log.getDefaultState()),
        				log(BlockHandler.ash_log.getDefaultState()),
        				log(BlockHandler.hornbeam_log.getDefaultState()),
        				log(BlockHandler.maple_log.getDefaultState())})
        		.setChances(new int[] {20, 10, 5, 7, 25});
    }

    private DecoFallenTree log(IBlockState log) {
        return new DecoFallenTree()
            .setLogCondition(LogCondition.RANDOM_CHANCE)
            .setLogConditionChance(5)
            .setMaxY(80)
            .setLogBlock(log)
            .setLeavesBlock(Blocks.LEAVES.getDefaultState())
            .setMinSize(3)
            .setMaxSize(6);
    }

    private DecoHelperRandomSplit shrubs() {
        return new DecoHelperRandomSplit().setDecos(new DecoBase[] {
    		new DecoShrub()
            .setMaxY(140)
            .setStrengthFactor(20f),
            new DecoShrub()
            .setMaxY(140)
            .setLogBlock(BlockHandler.alder_log.getDefaultState())
            .setLeavesBlock(BlockHandler.alder_leaves.getDefaultState())
            .setStrengthFactor(20f),
            new DecoShrub()
            .setMaxY(140)
            .setLogBlock(BlockHandler.ash_log.getDefaultState())
            .setLeavesBlock(BlockHandler.ash_leaves.getDefaultState())
            .setStrengthFactor(20f),
            new DecoShrub()
            .setMaxY(140)
            .setLogBlock(BlockHandler.hornbeam_log.getDefaultState())
            .setLeavesBlock(BlockHandler.hornbeam_leaves.getDefaultState())
            .setStrengthFactor(20f),
            new DecoShrub()
            .setMaxY(140)
            .setLogBlock(BlockHandler.maple_log.getDefaultState())
            .setLeavesBlock(BlockHandler.maple_leaves.getDefaultState())
            .setStrengthFactor(20f)}).setChances(new int[] {20, 10, 5, 7, 25});
    }

    private DecoFlowersRTG flowers() {
        return new DecoFlowersRTG()
            .setFlowers(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
            .setMaxY(128)
            .setStrengthFactor(6f);
    }

    private DecoGrass grass() {
        return new DecoGrass()
            .setMinY(60)
            .setMaxY(128)
            .setLoops(8);
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
