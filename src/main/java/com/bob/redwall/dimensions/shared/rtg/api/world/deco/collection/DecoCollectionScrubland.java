package com.bob.redwall.dimensions.shared.rtg.api.world.deco.collection;

import com.bob.redwall.dimensions.shared.rtg.api.config.BiomeConfig;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBase;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoBoulder;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFallenTree.LogCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoFlowersRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeType;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenBigTrees;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;


/**
 * @author WhichOnesPink
 */
public class DecoCollectionScrubland extends DecoCollectionBase {

    private float short2Min = 3f;
    private float short2Max = 5f;

    public DecoCollectionScrubland(BiomeConfig config) {

        super(config);

        this
            .addDeco(smallTrees(short2Min, short2Max)) // More short trees (on the other 'side' of the noise spectrum).
            .addDeco(randomTrees()) // More trees.
            .addDeco(logs(), config.ALLOW_LOGS.get()) // Add some fallen trees of the oak and spruce variety (50/50 distribution).
            .addDeco(shrubsOak()) // Shrubs to fill in the blanks.
            .addDeco(shrubsMaple()) // Fewer spruce shrubs than oak.
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
    }

    private DecoHelperRandomSplit smallTrees(float noiseMin, float noiseMax) {
        return new DecoHelperRandomSplit()
            .setDecos(new DecoBase[]{
            		randomVanillaTrees(), 
            		randomValourTrees(4, BlockHandler.maple_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.maple_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), 
            		randomValourTrees(4, BlockHandler.elm_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.elm_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true),
            		randomValourTrees(4, BlockHandler.ash_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.ash_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true)})
            .setChances(new int[]{6, 4, 1, 1});
    }

    private DecoHelperRandomSplit randomTrees() {
        return new DecoHelperRandomSplit()
            .setDecos(new DecoBase[]{
            		randomVanillaTrees(), 
            		randomValourTrees(4, BlockHandler.maple_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.maple_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), 
            		randomValourTrees(4, BlockHandler.elm_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.elm_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true),
            		randomValourTrees(4, BlockHandler.ash_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.ash_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true)})
            .setChances(new int[]{6, 4, 1, 1});
    }

    private DecoTree randomVanillaTrees() {

        WorldGenerator worldGenTrees = new WorldGenBigTrees(false);

        return new DecoTree(worldGenTrees)
            .setStrengthFactorForLoops(3f)
            .setTreeType(TreeType.WORLDGEN)
            .setTreeCondition(TreeCondition.RANDOM_CHANCE)
            .setTreeConditionChance(10)
            .setMaxY(120);
    }

    private DecoTree randomValourTrees(int height, IBlockState trunk, IBlockState leaf, boolean bBig) {

        WorldGenerator worldGenTrees = new WorldGenBigTrees(false, trunk, leaf);
        if(bBig) {
        	worldGenTrees = new WorldGenBigTrees(false, trunk, leaf);
        }
        

        return new DecoTree(worldGenTrees)
            .setStrengthFactorForLoops(3f)
            .setTreeType(TreeType.WORLDGEN)
            .setTreeCondition(TreeCondition.RANDOM_CHANCE)
            .setTreeConditionChance(10)
            .setMaxY(120);
    }

    private DecoHelperRandomSplit logs() {
        return new DecoHelperRandomSplit().setDecos(new DecoBase[]{oakLogs(), mapleLogs(), elmLogs(), ashLogs(), larchLogs()}).setChances(new int[] {16, 25, 5, 5, 5});
    }

    private DecoFallenTree oakLogs() {
        return new DecoFallenTree()
            .setLogCondition(LogCondition.RANDOM_CHANCE)
            .setLogConditionChance(16)
            .setMaxY(80)
            .setLogBlock(Blocks.LOG.getDefaultState())
            .setLeavesBlock(Blocks.LEAVES.getDefaultState())
            .setMinSize(3)
            .setMaxSize(6);
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

    private DecoFallenTree mapleLogs() {
        return new DecoFallenTree()
            .setLogCondition(LogCondition.RANDOM_CHANCE)
            .setLogConditionChance(24)
            .setMaxY(80)
            .setLogBlock(BlockHandler.maple_log.getDefaultState())
            .setLeavesBlock(BlockHandler.maple_leaves.getDefaultState())
            .setMinSize(3)
            .setMaxSize(6);
    }

    private DecoFallenTree elmLogs() {
        return new DecoFallenTree()
            .setLogCondition(LogCondition.RANDOM_CHANCE)
            .setLogConditionChance(24)
            .setMaxY(80)
            .setLogBlock(BlockHandler.elm_log.getDefaultState())
            .setLeavesBlock(BlockHandler.elm_leaves.getDefaultState())
            .setMinSize(3)
            .setMaxSize(6);
    }

    private DecoFallenTree ashLogs() {
        return new DecoFallenTree()
            .setLogCondition(LogCondition.RANDOM_CHANCE)
            .setLogConditionChance(24)
            .setMaxY(80)
            .setLogBlock(BlockHandler.ash_log.getDefaultState())
            .setLeavesBlock(BlockHandler.ash_leaves.getDefaultState())
            .setMinSize(3)
            .setMaxSize(6);
    }

    private DecoShrub shrubsOak() {
        return new DecoShrub()
            .setMaxY(140)
            .setStrengthFactor(8f)
            .setChance(2);
    }

    private DecoShrub shrubsMaple() {
        return new DecoShrub()
            .setLogBlock(BlockHandler.maple_log.getDefaultState())
            .setLeavesBlock(BlockHandler.maple_leaves.getDefaultState())
            .setMaxY(140)
            .setStrengthFactor(8f)
            .setChance(2);
    }

    private DecoFlowersRTG flowers() {
        return new DecoFlowersRTG()
            .setFlowers(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
            .setMaxY(128)
            .setStrengthFactor(6f);
    }
}
