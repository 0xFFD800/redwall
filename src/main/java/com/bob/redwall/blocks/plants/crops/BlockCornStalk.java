package com.bob.redwall.blocks.plants.crops;

import java.util.Random;

import javax.annotation.Nullable;

import com.bob.redwall.blocks.ModBlock;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.ItemHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCornStalk extends ModBlock implements net.minecraftforge.common.IPlantable {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
	public static final PropertyBool CORN = PropertyBool.create("corn");
	protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

	public BlockCornStalk(String name, Material mat) {
		super(mat, name, null, 0.0F, 0.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(CORN, Boolean.valueOf(false)));
		this.setTickRandomly(true);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return REED_AABB;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.getBlockState(pos.down()).getBlock() == BlockHandler.cornstalk || this.checkForDrop(worldIn, pos, state)) {
			if (worldIn.isAirBlock(pos.up())) {
				int i;

				for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {}

				if (i < 2) {
					int j = ((Integer) state.getValue(AGE)).intValue();

					if (new Random().nextInt(3) == 0) {
						if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
							if (j == 7) {
								worldIn.setBlockState(pos.up(), this.getDefaultState());
								worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 2);
							} else {
								worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 2);
							}
							net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
						}
					}
				} else if (i == 2 && state.getValue(CORN).booleanValue() == false) {
					int j = ((Integer) state.getValue(AGE)).intValue();
					if (j == 7) {
						if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
							worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 2);
							worldIn.setBlockState(pos, state.withProperty(CORN, true), 2);
						}
					} else {
						worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 2);
					}
				}
			}
		}
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		Block block = state.getBlock();
		if (block.canSustainPlant(state, worldIn, pos.down(), EnumFacing.UP, this))
			return true;

		if (block == this)
			return true;
		else if (block != Blocks.FARMLAND)
			return false;
		else
			return false;
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkForDrop(worldIn, pos, state);
	}

	protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (this.canBlockStay(worldIn, pos)) {
			return true;
		} else {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos) {
		return this.canPlaceBlockAt(worldIn, pos);
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemHandler.cornstalk;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(ItemHandler.cornstalk);
	}

	public IBlockState getStateFromMeta(int meta) {
		return meta < 8 ? this.getDefaultState().withProperty(AGE, Integer.valueOf(meta)) : this.getDefaultState().withProperty(AGE, Integer.valueOf(meta - 8)).withProperty(CORN, true);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(CORN) ? ((Integer) state.getValue(AGE)).intValue() + 8 : ((Integer) state.getValue(AGE)).intValue();
	}

	@Override
	public net.minecraftforge.common.EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return net.minecraftforge.common.EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return this.getDefaultState();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE, CORN });
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!state.getValue(CORN)) {
			return false;
		} else if (!worldIn.isRemote) {
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemHandler.corn, new Random().nextInt(1) + 1)));
			worldIn.setBlockState(pos, state.withProperty(CORN, false).withProperty(AGE, 0), 2);
			return true;
		} else {
			return false;
		}
	}
}
