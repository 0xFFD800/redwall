package com.bob.redwall.blocks.plants.treeleaves;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFruitTreeLeaves extends BlockLeaves implements IGrowable {
	public static final PropertyEnum<EnumGrowth> GROWTH = PropertyEnum.<EnumGrowth>create("growth", EnumGrowth.class);

	private String cropID;
	private String saplingID;

	public BlockFruitTreeLeaves(String name, CreativeTabs tab, ResourceLocation crop, ResourceLocation sapling) {
		super();
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, EnumGrowth.NONE).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.cropID = crop.toString();
		this.saplingID = sapling.toString();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CHECK_DECAY, DECAYABLE, ReflectionHelper.getPrivateValue(BlockLeaves.class, this, "WINTER"), GROWTH });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0)).withProperty(GROWTH, EnumGrowth.fromMeta(meta % 4));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(GROWTH).meta;

		if (!((Boolean) state.getValue(DECAYABLE)).booleanValue())
			i |= 4;

		if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue())
			i |= 8;

		return i;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		List<ItemStack> list = Lists.newArrayList();
		list.add(new ItemStack(this));
		return list;
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return null;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) { // Change this in all these classes
		return Item.getItemFromBlock(Block.getBlockFromName(this.saplingID));
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		if (state.getValue(GROWTH) == EnumGrowth.FULL)
			drops.add(new ItemStack(Item.getByNameOrId(this.cropID), RANDOM.nextInt(1) + 1));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);

		switch (side) {
		case DOWN:
			if (axisalignedbb.minY > 0.0D)
				return true;
			break;
		case UP:
			if (axisalignedbb.maxY < 1.0D)
				return true;
			break;
		case NORTH:
			if (axisalignedbb.minZ > 0.0D)
				return true;
			break;
		case SOUTH:
			if (axisalignedbb.minZ > 0.0D)
				return true;
			break;
		case WEST:
			if (axisalignedbb.minX > 0.0D)
				return true;
			break;
		case EAST:
			if (axisalignedbb.maxX < 1.0D)
				return true;
		}

		return !blockAccess.getBlockState(pos.offset(side)).doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
			if (this.canGrow(worldIn, pos, state, worldIn.isRemote)) {
				float f = this.getGrowthChance(this, worldIn, pos);

				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0)) {
					worldIn.setBlockState(pos, state.withProperty(GROWTH, EnumGrowth.fromMeta(state.getValue(GROWTH).meta + 1)), 2);
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
				}
			} else if (state.getValue(GROWTH) == EnumGrowth.FULL && !worldIn.isRemote) {
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getByNameOrId(this.cropID), new Random().nextInt(1) + 1)));
				worldIn.setBlockState(pos, state.withProperty(GROWTH, EnumGrowth.NONE), 2);
			}
		}
	}

	protected float getGrowthChance(BlockFruitTreeLeaves blockAppleLeaves, World worldIn, BlockPos pos) {
		return 0.3F;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return state.getValue(GROWTH) != EnumGrowth.FULL;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return this.canGrow(worldIn, pos, state, worldIn.isRemote) && (double) worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (this.canGrow(worldIn, pos, state, worldIn.isRemote)) {
			worldIn.setBlockState(pos, state.withProperty(GROWTH, EnumGrowth.fromMeta(state.getValue(GROWTH).meta + 1)), 2);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (state.getValue(GROWTH) != EnumGrowth.FULL)
			return false;
		else if (!worldIn.isRemote) {
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getByNameOrId(this.cropID), new Random().nextInt(1) + 1)));
			worldIn.setBlockState(pos, state.withProperty(GROWTH, EnumGrowth.NONE), 2);
			return true;
		} else {
			return false;
		}
	}

	public static enum EnumGrowth implements IStringSerializable {
		NONE(0, "none"), EARLY(1, "early"), LATE(2, "late"), FULL(3, "full");

		protected int meta;
		private String name;

		private EnumGrowth(int i, String name) {
			this.meta = i;
			this.name = name;
		}

		protected static EnumGrowth fromMeta(int meta) {
			for (EnumGrowth e : EnumGrowth.values()) {
				if (e.meta == meta)
					return e;
			}

			return null;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}
}
