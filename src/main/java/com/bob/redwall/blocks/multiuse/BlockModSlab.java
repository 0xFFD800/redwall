package com.bob.redwall.blocks.multiuse;

import java.util.Random;

import com.bob.redwall.blocks.ModBlock;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModSlab extends ModBlock {
	public static final PropertyEnum<BlockModSlab.EnumBlockHalf> HALF = PropertyEnum.<BlockModSlab.EnumBlockHalf>create("half", BlockModSlab.EnumBlockHalf.class);
	public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
	public static final PropertyBool BUMBUMBUM = PropertyBool.create("bumbumbum");
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockModSlab(Material materialIn, String string, CreativeTabs tab, float hardness, float resistance, int harvest, String tool) {
		super(materialIn, string, tab, hardness, resistance, harvest, tool);
		this.fullBlock = this.isDouble();
		this.setLightOpacity(255);
		IBlockState iblockstate = this.blockState.getBaseState();
		
		if (this.isDouble())
			iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.valueOf(false));
		else
			iblockstate = iblockstate.withProperty(HALF, BlockModSlab.EnumBlockHalf.BOTTOM);
		
		this.setDefaultState(iblockstate);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.isDouble() ? FULL_BLOCK_AABB : (state.getValue(HALF) == BlockModSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF);
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return ((BlockModSlab) state.getBlock()).isDouble() || state.getValue(HALF) == BlockModSlab.EnumBlockHalf.TOP;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return this.isDouble();
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
			return super.doesSideBlockRendering(state, world, pos, face);

		if (state.isOpaqueCube())
			return true;

		EnumBlockHalf side = state.getValue(HALF);
		return (side == EnumBlockHalf.TOP && face == EnumFacing.UP) || (side == EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, BlockModSlab.EnumBlockHalf.BOTTOM);
		return this.isDouble() ? iblockstate : (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate : iblockstate.withProperty(HALF, BlockModSlab.EnumBlockHalf.TOP));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();

		if (this.isDouble())
			iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.valueOf((meta & 8) != 0));
		else
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockModSlab.EnumBlockHalf.BOTTOM : BlockModSlab.EnumBlockHalf.TOP);

		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (this.isDouble())
			if (((Boolean) state.getValue(SEAMLESS)).booleanValue())
				i |= 8;
		else if (state.getValue(HALF) == BlockModSlab.EnumBlockHalf.TOP)
			i |= 8;

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] { SEAMLESS, BUMBUMBUM }) : new BlockStateContainer(this, new IProperty[] { HALF, BUMBUMBUM });
	}

	@Override
	public int quantityDropped(Random random) {
		return this.isDouble() ? 2 : 1;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return this.isDouble();
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (this.isDouble()) {
			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		} else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
			return false;
		}
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	public boolean isDouble() {
		return false;
	}

	public static enum EnumBlockHalf implements IStringSerializable {
		TOP("top"), BOTTOM("bottom");

		private final String name;

		private EnumBlockHalf(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	public static class BlockModDoubleSlab extends BlockModSlab {
		public BlockModDoubleSlab(Material materialIn, String string, CreativeTabs tab, float hardness, float resistance, int harvest, String tool) {
			super(materialIn, string, tab, hardness, resistance, harvest, tool);
		}

		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public Comparable<?> getTypeForItem(ItemStack itemstack) {
		return new Comparable<Object>() {
			@Override
			public int compareTo(Object o) {
				return 0;
			}
		};
	}

	public IProperty<?> getVariantProperty() {
		return BUMBUMBUM;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		if (state.getBlock() instanceof BlockSlab ? ((BlockSlab) state.getBlock()).isDouble() : ((BlockModSlab) state.getBlock()).isDouble())
			return BlockFaceShape.SOLID;
		else if (face == EnumFacing.UP && state.getValue(HALF) == BlockModSlab.EnumBlockHalf.TOP)
			return BlockFaceShape.SOLID;
		else
			return face == EnumFacing.DOWN && state.getValue(HALF) == BlockModSlab.EnumBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
}
