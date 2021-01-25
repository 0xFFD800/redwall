package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoCrop;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree.LogCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFlowersRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoSandstoneSlab;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeType;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGPiceaSitchensis;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionForestLarch extends DecoCollectionBase {

    // Tends to return values between -3f to 5f, with some overflow.
    private DecoTree.Distribution forestDistribution = new DecoTree.Distribution(100f, 6f, 0.8f);

    private float short1Min = -3f;
    private float short1Max = -1f;
    private float short2Min = 3f;
    private float short2Max = 5f;

    public DecoCollectionForestLarch(BiomeConfig config) {

        super(config);

        this
            .addDeco(shortTrees(short1Min, short1Max)) // Short trees next.
            .addDeco(shortTrees(short2Min, short2Max)) // More short trees (on the other 'side' of the noise spectrum).=
            .addDeco(logs(), config.ALLOW_LOGS.get()) // Add some fallen trees of the oak and spruce variety (50/50 distribution).
            .addDeco(shrubsSpruce()) // Fewer spruce shrubs than oak.
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

    private DecoHelperRandomSplit shortTrees(float noiseMin, float noiseMax) {
    	return new DecoHelperRandomSplit().setDecos(new DecoBase[] {
                shortPineTrees(BlockHandler.larch_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, EnumAxis.Y), BlockHandler.larch_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), noiseMin, noiseMax)}
            ).setChances(new int[] {20});
    }

    private DecoTree shortPineTrees(IBlockState log, IBlockState leaves, float noiseMin, float noiseMax) {
        TreeRTG piceaSitchensis = new TreeRTGPiceaSitchensis()
            .setLogBlock(log)
            .setLeavesBlock(leaves)
            .setMinTrunkSize(4)
            .setMaxTrunkSize(10)
            .setMinCrownSize(6)
            .setMaxCrownSize(14);

        this.addTree(piceaSitchensis);

        return new DecoTree(piceaSitchensis)
            .setStrengthFactorForLoops(6f)
            .setTreeType(TreeType.RTG_TREE)
            .setDistribution(forestDistribution)
            .setTreeCondition(TreeCondition.ALWAYS_GENERATE)
            .setTreeConditionNoise(noiseMin)
            .setTreeConditionNoise2(noiseMax)
            .setTreeConditionChance(1)
            .setMaxY(85);
    }

    private DecoHelperRandomSplit logs() {
        return new DecoHelperRandomSplit().setDecos(new DecoBase[]{larchLogs()}).setChances(new int[] {5});
    }

    private DecoFallenTree larchLogs() {
        return new DecoFallenTree()
            .setLogCondition(LogCondition.RANDOM_CHANCE)
            .setLogConditionChance(24)
            .setMaxY(80)
            .setLogBlock(BlockHandler.larch_log.getDefaultState())
            .setLeavesBlock(BlockHandler.larch_leaves.getDefaultState())
            .setMinSize(3)
            .setMaxSize(6);
    }

    private DecoShrub shrubsSpruce() {
        return new DecoShrub()
            .setLogBlock(BlockHandler.larch_log.getDefaultState())
            .setLeavesBlock(BlockHandler.larch_leaves.getDefaultState())
            .setMaxY(140)
            .setStrengthFactor(4f)
            .setChance(9);
    }

    private DecoFlowersRTG flowers() {
        return new DecoFlowersRTG()
            .setFlowers(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
            .setMaxY(128)
            .setStrengthFactor(6f);
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
