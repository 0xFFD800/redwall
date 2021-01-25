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
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoGrassDoubleTallgrass;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoSandstoneSlab;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoShrub;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeCondition;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.DecoTree.TreeType;
import com.bob.redwall.dimensions.shared.rtg.api.world.deco.helper.DecoHelperRandomSplit;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenBigTrees;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTG;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGCupressusSempervirens;
import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.tree.rtg.TreeRTGPiceaSitchensis;
import com.bob.redwall.init.BlockHandler;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * @author WhichOnesPink
 */
public class DecoCollectionForestDeciduous extends DecoCollectionBase {
	// Tends to return values between -3f to 5f, with some overflow.
	private DecoTree.Distribution forestDistribution = new DecoTree.Distribution(100f, 6f, 0.8f);

	private float short1Min = -3f;
	private float short1Max = -1f;
	private float tallMin = -1f;
	private float tallMax = 3f;
	private float short2Min = 3f;
	private float short2Max = 5f;

	public DecoCollectionForestDeciduous(BiomeConfig config) {
		super(config);

		this.addDeco(shortTrees(short1Min, short1Max)) // Short trees next.
				.addDeco(smallTrees(short2Min, short2Max)) // More short trees (on the other 'side' of the noise
															// spectrum).
				.addDeco(randomTrees()) // More trees.
				.addDeco(logs(), config.ALLOW_LOGS.get()) // Add some fallen trees of the oak and spruce variety (50/50
															// distribution).
				.addDeco(shrubsOak()) // Shrubs to fill in the blanks.
				.addDeco(shrubsBirch()) // Fewer spruce shrubs than oak.
				.addDeco(bushes()).addDeco(flowers()) // Only 1-block tall flowers so we can see the trees better.
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
		return new DecoHelperRandomSplit().setDecos(new DecoBase[] { shortBirchTrees(BlockUtil.getStateLog(2), BlockUtil.getStateLeaf(2), noiseMin, noiseMax), shortAspenTrees(tallMin, tallMax) }).setChances(new int[] { 15, 18 });
	}

	private DecoTree shortBirchTrees(IBlockState log, IBlockState leaves, float noiseMin, float noiseMax) {
		TreeRTG piceaSitchensis = new TreeRTGPiceaSitchensis().setLogBlock(log).setLeavesBlock(leaves).setMinTrunkSize(4).setMaxTrunkSize(10).setMinCrownSize(6).setMaxCrownSize(14);

		this.addTree(piceaSitchensis);

		return new DecoTree(piceaSitchensis).setStrengthFactorForLoops(6f).setTreeType(TreeType.RTG_TREE).setDistribution(forestDistribution).setTreeCondition(TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE).setTreeConditionNoise(noiseMin).setTreeConditionNoise2(noiseMax).setTreeConditionChance(1).setMaxY(85);
	}

	private DecoTree shortAspenTrees(float noiseMin, float noiseMax) {
		TreeRTG worldgenerator = new TreeRTGCupressusSempervirens();
		worldgenerator.setLogBlock(BlockHandler.aspen_log.getDefaultState());
		worldgenerator.setLeavesBlock(BlockHandler.aspen_leaves.getDefaultState());
		worldgenerator.setMinTrunkSize(2);
		worldgenerator.setMaxTrunkSize(3);
		worldgenerator.setMinCrownSize(8);
		worldgenerator.setMaxCrownSize(16);
		worldgenerator.setNoLeaves(false);

		this.addTree(worldgenerator);

		return new DecoTree(worldgenerator).setStrengthFactorForLoops(6f).setTreeType(TreeType.RTG_TREE).setDistribution(forestDistribution).setTreeCondition(TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE).setTreeConditionNoise(noiseMin).setTreeConditionNoise2(noiseMax).setTreeConditionChance(1).setMaxY(85);
	}

	private DecoHelperRandomSplit randomTrees() {
		return new DecoHelperRandomSplit().setDecos(new DecoBase[] { randomVanillaTrees(), randomValourTrees(4, BlockHandler.maple_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.maple_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(4, BlockHandler.elm_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.elm_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(4, BlockHandler.ash_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.ash_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(4, BlockHandler.hornbeam_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.hornbeam_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(4, BlockHandler.beech_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.beech_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(4, BlockHandler.alder_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.alder_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true) }).setChances(new int[] { 16, 25, 5, 5, 7, 15, 10 });
	}

	private DecoHelperRandomSplit smallTrees(float noiseMin, float noiseMax) {
		return new DecoHelperRandomSplit().setDecos(new DecoBase[] { randomVanillaTrees(noiseMin, noiseMax), randomValourTrees(noiseMin, noiseMax, 4, BlockHandler.maple_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.maple_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(noiseMin, noiseMax, 4, BlockHandler.elm_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.elm_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(noiseMin, noiseMax, 4, BlockHandler.ash_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.ash_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(noiseMin, noiseMax, 4, BlockHandler.hornbeam_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.hornbeam_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(noiseMin, noiseMax, 4, BlockHandler.beech_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.beech_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true), randomValourTrees(noiseMin, noiseMax, 4, BlockHandler.alder_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), BlockHandler.alder_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false), true) }).setChances(new int[] { 16, 25, 5, 5, 7, 15, 10 });
	}

	private DecoTree randomVanillaTrees() {

		WorldGenerator worldGenTrees = new WorldGenBigTrees(false);

		return new DecoTree(worldGenTrees).setStrengthFactorForLoops(3f).setTreeType(TreeType.WORLDGEN).setTreeCondition(TreeCondition.RANDOM_CHANCE).setTreeConditionChance(3).setMaxY(120);
	}

	private DecoTree randomVanillaTrees(float noiseMin, float noiseMax) {

		WorldGenerator worldGenTrees = new WorldGenBigTrees(false);

		return new DecoTree(worldGenTrees).setStrengthFactorForLoops(3f).setTreeType(TreeType.WORLDGEN).setTreeCondition(TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE).setTreeConditionNoise(noiseMin).setTreeConditionNoise2(noiseMax).setTreeConditionChance(3).setMaxY(120);
	}

	private DecoTree randomValourTrees(int height, IBlockState trunk, IBlockState leaf, boolean bBig) {
		WorldGenerator worldGenTrees = new WorldGenBigTrees(false, trunk, leaf);

		return new DecoTree(worldGenTrees).setStrengthFactorForLoops(3f).setTreeType(TreeType.WORLDGEN).setTreeCondition(TreeCondition.RANDOM_CHANCE).setTreeConditionChance(3).setMaxY(120);
	}

	private DecoTree randomValourTrees(float noiseMin, float noiseMax, int height, IBlockState trunk, IBlockState leaf, boolean bBig) {
		WorldGenerator worldGenTrees = new WorldGenBigTrees(false, trunk, leaf);

		return new DecoTree(worldGenTrees).setStrengthFactorForLoops(3f).setTreeType(TreeType.WORLDGEN).setTreeCondition(TreeCondition.NOISE_BETWEEN_AND_RANDOM_CHANCE).setTreeConditionNoise(noiseMin).setTreeConditionNoise2(noiseMax).setTreeConditionChance(3).setMaxY(120);
	}

	private DecoHelperRandomSplit logs() {
		return new DecoHelperRandomSplit().setDecos(new DecoBase[] { oakLogs(), hornbeamLogs(), mapleLogs(), elmLogs(), ashLogs(), birchLogs(), alderLogs(), aspenLogs(), larchLogs() }).setChances(new int[] { 16, 10, 25, 5, 5, 15, 10, 10, 5 });
	}

	private DecoFallenTree oakLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(16).setMaxY(80).setLogBlock(Blocks.LOG.getDefaultState()).setLeavesBlock(Blocks.LEAVES.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree hornbeamLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.hornbeam_log.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree birchLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockUtil.getStateLog(2)).setLeavesBlock(BlockUtil.getStateLeaf(2)).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree larchLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.larch_log.getDefaultState()).setLeavesBlock(BlockHandler.larch_leaves.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree mapleLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.maple_log.getDefaultState()).setLeavesBlock(BlockHandler.maple_leaves.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree alderLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.alder_log.getDefaultState()).setLeavesBlock(BlockHandler.alder_leaves.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree elmLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.elm_log.getDefaultState()).setLeavesBlock(BlockHandler.elm_leaves.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree ashLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.ash_log.getDefaultState()).setLeavesBlock(BlockHandler.ash_leaves.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoFallenTree aspenLogs() {
		return new DecoFallenTree().setLogCondition(LogCondition.RANDOM_CHANCE).setLogConditionChance(24).setMaxY(80).setLogBlock(BlockHandler.aspen_log.getDefaultState()).setLeavesBlock(BlockHandler.aspen_leaves.getDefaultState()).setMinSize(3).setMaxSize(6);
	}

	private DecoShrub shrubsOak() {
		return new DecoShrub().setMaxY(140).setStrengthFactor(4f).setChance(3);
	}

	private DecoShrub shrubsBirch() {
		return new DecoShrub().setLogBlock(BlockUtil.getStateLog(2)).setLeavesBlock(BlockUtil.getStateLeaf(2)).setMaxY(140).setStrengthFactor(4f).setChance(9);
	}

	private DecoFlowersRTG flowers() {
		return new DecoFlowersRTG().setFlowers(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }).setMaxY(128).setStrengthFactor(6f);
	}

	private DecoHelperRandomSplit bushes() {
		return new DecoHelperRandomSplit().setDecos(new DecoBase[] { new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.blackberry_bush), new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.blueberry_bush), new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.raspberry_bush), new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.strawberry_bush), new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.elderberry_bush), new DecoCrop().setMaxY(80).setStrengthFactor(0.2F).setWater(false).setBlock(BlockHandler.wildberry_bush) }).setChances(new int[] { 1, 1, 1, 1, 1, 10 });
	}
}
