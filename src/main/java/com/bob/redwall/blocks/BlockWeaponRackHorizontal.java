package com.bob.redwall.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.bob.redwall.items.weapons.ranged.ItemModBow;
import com.bob.redwall.tileentity.TileEntityWeaponRack;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWeaponRackHorizontal extends ModBlock implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", (@Nullable EnumFacing p_apply_1_) -> p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP);
	protected static final AxisAlignedBB RACK_NORTH_AABB = new AxisAlignedBB(0.0625D, 0.375D, 0.8125D, 0.9375D, 0.625D, 1.0D);
	protected static final AxisAlignedBB RACK_SOUTH_AABB = new AxisAlignedBB(0.0625D, 0.375D, 0.0D, 0.9375D, 0.625D, 0.1875D);
	protected static final AxisAlignedBB RACK_WEST_AABB = new AxisAlignedBB(0.8125D, 0.375D, 0.0625D, 1.0D, 0.625D, 0.9375D);
	protected static final AxisAlignedBB RACK_EAST_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.0625D, 0.1875D, 0.625D, 0.9375D);

	public BlockWeaponRackHorizontal(Material mat, String name) {
		super(mat, name, null, 0, 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return this.createTileEntity(worldIn, this.getStateFromMeta(meta));
	}

	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state) {
		return new TileEntityWeaponRack();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((EnumFacing) state.getValue(FACING)) {
		case EAST:
			return RACK_EAST_AABB;
		case WEST:
			return RACK_WEST_AABB;
		case SOUTH:
			return RACK_SOUTH_AABB;
		case NORTH:
		default:
			return RACK_NORTH_AABB;
		}
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : FACING.getAllowedValues())
			if (this.canPlaceAt(worldIn, pos, enumfacing))
				return true;

		return false;
	}

	private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing.getOpposite());
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

		if (facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
		else return false;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (this.canPlaceAt(worldIn, pos, facing)) {
			return this.getDefaultState().withProperty(FACING, facing);
		} else {
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
				if (this.canPlaceAt(worldIn, pos, enumfacing))
					return this.getDefaultState().withProperty(FACING, enumfacing);

			return this.getDefaultState();
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.checkForDrop(worldIn, pos, state);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.onNeighborChangeInternal(worldIn, pos, state);
	}

	protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.checkForDrop(worldIn, pos, state)) {
			return true;
		} else {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
			EnumFacing enumfacing1 = enumfacing.getOpposite();
			BlockPos blockpos = pos.offset(enumfacing1);

			if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
				return true;
			} else {
				return false;
			}
		}
	}

	protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing) state.getValue(FACING))) {
			return true;
		} else {
			if (worldIn.getBlockState(pos).getBlock() == this) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}

			return false;
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();

		switch (meta) {
		case 1:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
			break;
		case 2:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
			break;
		case 3:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
			break;
		case 4:
		default:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
		}

		return iblockstate;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		switch ((EnumFacing) state.getValue(FACING)) {
		case EAST:
			i = i | 1;
			break;
		case WEST:
			i = i | 2;
			break;
		case SOUTH:
			i = i | 3;
			break;
		case NORTH:
		default:
			i = i | 4;
		}

		return i;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	private TileEntityWeaponRack getTE(World world, BlockPos pos) {
		return (TileEntityWeaponRack) world.getTileEntity(pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityWeaponRack te = this.getTE(world, pos);
			ItemStack stack = te.getStack();
			if (te.getStack().isEmpty() && (player.getHeldItem(hand).getItem() instanceof ModCustomWeapon || player.getHeldItem(hand).getItem() instanceof ItemBow || player.getHeldItem(hand).getItem() instanceof ItemModBow || player.getHeldItem(hand).getItem() instanceof ItemFishingRod || player.getHeldItem(hand).getItem() instanceof ItemTool || player.getHeldItem(hand).getItem() instanceof ItemHoe)) {
				te.setStack(player.getHeldItem(hand).copy());
				player.getHeldItem(hand).shrink(1);

				return true;
			} else if (!stack.isEmpty() && player.getHeldItem(hand).isEmpty()) {
				if (!player.addItemStackToInventory(stack.copy())) {
					EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack.copy());
					world.spawnEntity(entityItem);
				}
				te.setStack(ItemStack.EMPTY);

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		TileEntityWeaponRack te = this.getTE(world, pos);
		if (te == null)
			new ItemStack(ItemHandler.weapon_rack);
		ItemStack stack = te.getStack();
		if (!stack.isEmpty())
			return te.getStack();
		else return new ItemStack(ItemHandler.weapon_rack);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

		if (!world.isRemote) {
			TileEntityWeaponRack te = this.getTE(world, pos);
			if (te == null)
				return;
			ItemStack stack = te.getStack();
			if (!stack.isEmpty()) {
				EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack.copy());
				world.spawnEntity(entityItem);
				te.setStack(ItemStack.EMPTY);
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemHandler.weapon_rack;
	}
}
