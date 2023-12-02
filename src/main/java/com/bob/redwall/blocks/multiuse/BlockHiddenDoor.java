package com.bob.redwall.blocks.multiuse;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.bob.redwall.blocks.ModBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHiddenDoor extends ModBlock {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyEnum<BlockHiddenDoor.EnumHingePosition> HINGE = PropertyEnum.<BlockHiddenDoor.EnumHingePosition>create("hinge", BlockHiddenDoor.EnumHingePosition.class);
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyEnum<BlockHiddenDoor.EnumDoorHalf> HALF = PropertyEnum.<BlockHiddenDoor.EnumDoorHalf>create("half", BlockHiddenDoor.EnumDoorHalf.class);
	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.9D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.9D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1D, 1.0D, 1.0D);
	protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0D, 0.775D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected Item item;

	public BlockHiddenDoor(Material mat, String name, CreativeTabs tab, float hardness, float resistance, int harvest, String tool, Item item) {
		super(mat, name, tab, hardness, resistance, harvest, tool);
		this.item = item;
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, Boolean.valueOf(false)).withProperty(HINGE, BlockHiddenDoor.EnumHingePosition.LEFT).withProperty(POWERED, Boolean.valueOf(false)).withProperty(HALF, BlockHiddenDoor.EnumDoorHalf.LOWER));
	}

	@Nullable
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		int c = combineMetadata(worldIn, pos);
		if (!isOpen(c)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(worldIn, pos));
		} else if (state.getValue(HALF) == EnumDoorHalf.LOWER && (getFacing(c) == EnumFacing.NORTH || getFacing(c) == EnumFacing.SOUTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
		} else if (state.getValue(HALF) == EnumDoorHalf.LOWER && (getFacing(c) == EnumFacing.EAST || getFacing(c) == EnumFacing.WEST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
		} else if (state.getValue(HALF) == EnumDoorHalf.UPPER && (getFacing(c) == EnumFacing.NORTH || getFacing(c) == EnumFacing.SOUTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
		} else if (state.getValue(HALF) == EnumDoorHalf.UPPER && (getFacing(c) == EnumFacing.EAST || getFacing(c) == EnumFacing.WEST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
		}
	}

	@Override
	public String getLocalizedName() {
		return I18n.format((this.getUnlocalizedName() + ".name").replaceAll("tile", "item"));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return isOpen(combineMetadata(worldIn, pos));
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	private int getCloseSound() {
		return this.blockMaterial == Material.IRON ? 1011 : 1012;
	}

	private int getOpenSound() {
		return this.blockMaterial == Material.IRON ? 1005 : 1006;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this.blockMaterial == Material.IRON) {
			return false; // Allow items to interact with the door
		} else {
			BlockPos blockpos = state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.LOWER ? pos : pos.down();
			IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

			if (iblockstate.getBlock() != this) {
				return false;
			} else {
				state = iblockstate.cycleProperty(OPEN);
				worldIn.setBlockState(blockpos, state, 10);
				worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
				worldIn.playEvent(playerIn, ((Boolean) state.getValue(OPEN)).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
				return true;
			}
		}
	}

	public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (iblockstate.getBlock() == this) {
			BlockPos blockpos = iblockstate.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.LOWER ? pos : pos.down();
			IBlockState iblockstate1 = pos == blockpos ? iblockstate : worldIn.getBlockState(blockpos);

			if (iblockstate1.getBlock() == this && ((Boolean) iblockstate1.getValue(OPEN)).booleanValue() != open) {
				worldIn.setBlockState(blockpos, iblockstate1.withProperty(OPEN, Boolean.valueOf(open)), 10);
				worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
				worldIn.playEvent((EntityPlayer) null, open ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			}
		}
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.UPPER) {
			BlockPos blockpos = pos.down();
			IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if (iblockstate.getBlock() != this)
				worldIn.setBlockToAir(pos);
			else if (blockIn != this)
				iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
		} else {
			boolean flag1 = false;
			BlockPos blockpos1 = pos.up();
			IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

			if (iblockstate1.getBlock() != this) {
				worldIn.setBlockToAir(pos);
				flag1 = true;
			}

			if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
				worldIn.setBlockToAir(pos);
				flag1 = true;

				if (iblockstate1.getBlock() == this)
					worldIn.setBlockToAir(blockpos1);
			}

			if (flag1) {
				if (!worldIn.isRemote)
					this.dropBlockAsItem(worldIn, pos, state, 0);
			} else {
				boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

				if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != ((Boolean) iblockstate1.getValue(POWERED)).booleanValue()) {
					worldIn.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, Boolean.valueOf(flag)), 2);

					if (flag != ((Boolean) state.getValue(OPEN)).booleanValue()) {
						worldIn.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(flag)), 2);
						worldIn.markBlockRangeForRenderUpdate(pos, pos);
						worldIn.playEvent((EntityPlayer) null, flag ? this.getOpenSound() : this.getCloseSound(), pos, 0);
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return pos.getY() >= worldIn.getHeight() - 1 ? false : worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		int i = iblockstate.getBlock().getMetaFromState(iblockstate);
		boolean flag = isTop(i);
		IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
		int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
		int k = flag ? j : i;
		IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
		int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
		int i1 = flag ? i : l;
		boolean flag1 = (i1 & 1) != 0;
		boolean flag2 = (i1 & 2) != 0;
		return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.getItem());
	}

	private Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		BlockPos blockpos = pos.down();
		BlockPos blockpos1 = pos.up();

		if (player.capabilities.isCreativeMode && state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this)
			worldIn.setBlockToAir(blockpos);

		if (state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this) {
			if (player.capabilities.isCreativeMode)
				worldIn.setBlockToAir(pos);

			worldIn.setBlockToAir(blockpos1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.LOWER) {
			IBlockState iblockstate = worldIn.getBlockState(pos.up());

			if (iblockstate.getBlock() == this)
				state = state.withProperty(HINGE, iblockstate.getValue(HINGE)).withProperty(POWERED, iblockstate.getValue(POWERED));
		} else {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.down());

			if (iblockstate1.getBlock() == this)
				state = state.withProperty(FACING, iblockstate1.getValue(FACING)).withProperty(OPEN, iblockstate1.getValue(OPEN));
		}

		return state;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getValue(HALF) != BlockHiddenDoor.EnumDoorHalf.LOWER ? state : state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING))).cycleProperty(HINGE);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, BlockHiddenDoor.EnumDoorHalf.UPPER).withProperty(HINGE, (meta & 1) > 0 ? BlockHiddenDoor.EnumHingePosition.RIGHT : BlockHiddenDoor.EnumHingePosition.LEFT).withProperty(POWERED, Boolean.valueOf((meta & 2) > 0)) : this.getDefaultState().withProperty(HALF, BlockHiddenDoor.EnumDoorHalf.LOWER).withProperty(FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW()).withProperty(OPEN, Boolean.valueOf((meta & 4) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(HALF) == BlockHiddenDoor.EnumDoorHalf.UPPER) {
			i = i | 8;

			if (state.getValue(HINGE) == BlockHiddenDoor.EnumHingePosition.RIGHT)
				i |= 1;

			if (((Boolean) state.getValue(POWERED)).booleanValue())
				i |= 2;
		} else {
			i = i | ((EnumFacing) state.getValue(FACING)).rotateY().getHorizontalIndex();

			if (((Boolean) state.getValue(OPEN)).booleanValue())
				i |= 4;
		}

		return i;
	}

	protected static int removeHalfBit(int meta) {
		return meta & 7;
	}

	public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
		return isOpen(combineMetadata(worldIn, pos));
	}

	public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
		return getFacing(combineMetadata(worldIn, pos));
	}

	public static EnumFacing getFacing(int combinedMeta) {
		return EnumFacing.getHorizontal(combinedMeta & 3).rotateYCCW();
	}

	protected static boolean isOpen(int combinedMeta) {
		return (combinedMeta & 4) != 0;
	}

	protected static boolean isTop(int meta) {
		return (meta & 8) != 0;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { HALF, FACING, OPEN, HINGE, POWERED });
	}

	public static enum EnumDoorHalf implements IStringSerializable {
		UPPER, LOWER;

		public String toString() {
			return this.getName();
		}

		public String getName() {
			return this == UPPER ? "upper" : "lower";
		}
	}

	public static enum EnumHingePosition implements IStringSerializable {
		LEFT, RIGHT;

		public String toString() {
			return this.getName();
		}

		public String getName() {
			return this == LEFT ? "left" : "right";
		}
	}
}
