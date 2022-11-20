package com.bob.redwall.blocks.stations;

import com.bob.redwall.Ref;
import com.bob.redwall.blocks.ModBlock;
import com.bob.redwall.common.MessageUIInteractServer;
import com.bob.redwall.common.MessageUIInteractServer.Mode;
import com.bob.redwall.init.GuiHandler;
import com.bob.redwall.tileentity.TileEntityBrewingVerminMossflower;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBrewingVerminMossflower extends ModBlock implements ITileEntityProvider {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockBrewingVerminMossflower(Material mat, String name, CreativeTabs tab, float hardness, float resistance, int harvest, String tool) {
		super(mat, name, tab, hardness, resistance, harvest, tool);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBrewingVerminMossflower();
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		// Only execute on the server
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (!(te instanceof TileEntityBrewingVerminMossflower))
				return false;
			player.openGui(Ref.MODID, GuiHandler.GUI_BREWING_VERMIN_MOSSFLOWER_ID, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		} else {
			TileEntity te = world.getTileEntity(pos);
			if (!(te instanceof TileEntityBrewingVerminMossflower))
				return false;
			// If it's on client we want to send a message to the server instead.
			Ref.NETWORK.sendToServer(new MessageUIInteractServer(Mode.OPEN_GUI_CONTAINER, GuiHandler.GUI_BREWING_VERMIN_MOSSFLOWER_ID, pos.getX(), pos.getY(), pos.getZ()));
			return true;
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();

		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, enumfacing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		return i;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityBrewingVerminMossflower tileentity = (TileEntityBrewingVerminMossflower) world.getTileEntity(pos);

		if (tileentity.getBrewingFinished()) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), tileentity.brewStack);
		InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileentity);
		world.updateComparatorOutputLevel(pos, this);

		super.breakBlock(world, pos, state);
	}
}
