package com.bob.redwall.blocks.plants.saplings;

import java.util.Random;

import com.bob.redwall.dimensions.shared.rtg.api.world.gen.feature.WorldGenBigTrees;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.CreativeTabHandler;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockPearSapling extends BlockBush implements IGrowable {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public BlockPearSapling(String name, CreativeTabs tab) {
    	super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
        this.setCreativeTab(CreativeTabHandler.BLOCKS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand);

            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (((Integer)state.getValue(STAGE)).intValue() == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        IBlockState trunk = BlockHandler.pear_log.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
        IBlockState leaf = BlockHandler.pear_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);
        WorldGenerator worldgenerator = rand.nextInt(2) == 0 ? new WorldGenBigTrees(false, trunk, leaf) : new WorldGenTrees(false, 4, trunk, leaf, false);
        int i = 0;
        int j = 0;
        boolean flag = false;

        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

        if (flag) {
            worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
        } else {
            worldIn.setBlockState(pos, iblockstate2, 4);
        }

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j))) {
            if (flag) {
                worldIn.setBlockState(pos.add(i, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            } else {
                worldIn.setBlockState(pos, state, 4);
            }
        }
    }

    public boolean isTypeAt(World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        return iblockstate.getBlock() == this;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        this.grow(worldIn, pos, state, rand);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
        return i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {STAGE});
    }
}