package com.bob.redwall.blocks.plants.treeleaves;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BlockPlumLeaves extends BlockFruitTreeLeaves implements IGrowable {
	public static final PropertyBool DAMSON = PropertyBool.create("damson");
	private String cropID;
	private String crop2ID;

	public BlockPlumLeaves(String name, CreativeTabs tab, ResourceLocation crop, ResourceLocation crop2, ResourceLocation sapling) {
		super(name, tab, crop, sapling);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DAMSON, false).withProperty(GROWTH, EnumGrowth.NONE).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.cropID = crop.toString();
		this.crop2ID = crop2.toString();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CHECK_DECAY, DECAYABLE, ReflectionHelper.getPrivateValue(BlockLeaves.class, this, "WINTER"), GROWTH, DAMSON });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 4) == 0)).withProperty(DAMSON, Boolean.valueOf((meta & 8) > 0)).withProperty(GROWTH, EnumGrowth.fromMeta(meta % 4));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(GROWTH).meta;

		if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue())
			i |= 4;

		if (((Boolean) state.getValue(DAMSON)).booleanValue())
			i |= 8;

		return i;
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : new Random();
		int chance = this.getSaplingDropChance(state);

		if (fortune > 0) {
			chance -= 2 << fortune;
			if (chance < 10)
				chance = 10;
		}

		if (rand.nextInt(chance) == 0) {
			ItemStack drop = new ItemStack(getItemDropped(state, rand, fortune), 1, damageDropped(state));
			if (!drop.isEmpty())
				drops.add(drop);
		}

		chance = 200;
		if (fortune > 0) {
			chance -= 10 << fortune;
			if (chance < 40)
				chance = 40;
		}

		this.captureDrops(true);
		drops.addAll(this.captureDrops(false));
		if (state.getValue(GROWTH) == EnumGrowth.FULL)
			drops.add(new ItemStack(Item.getByNameOrId(state.getValue(DAMSON) ? this.crop2ID : this.cropID), RANDOM.nextInt(1) + 1));
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
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getByNameOrId(state.getValue(DAMSON) ? this.crop2ID : this.cropID), new Random().nextInt(1) + 1)));
				worldIn.setBlockState(pos, state.withProperty(GROWTH, EnumGrowth.NONE), 2);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (state.getValue(GROWTH) != EnumGrowth.FULL)
			return false;
		else if (!worldIn.isRemote) {
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getByNameOrId(state.getValue(DAMSON) ? this.crop2ID : this.cropID), new Random().nextInt(1) + 1)));
			worldIn.setBlockState(pos, state.withProperty(GROWTH, EnumGrowth.NONE), 2);
			return true;
		} else {
			return false;
		}
	}
}
