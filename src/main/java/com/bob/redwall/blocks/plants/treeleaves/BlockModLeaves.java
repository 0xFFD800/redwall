package com.bob.redwall.blocks.plants.treeleaves;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModLeaves extends BlockLeaves {
	private String saplingID;
	
	public BlockModLeaves(String name, CreativeTabs tab, ResourceLocation sapling) {
		super();
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.saplingID = sapling.toString();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE, ReflectionHelper.getPrivateValue(BlockLeaves.class, this, "WINTER")});
    }
	
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue()) {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue()) {
            i |= 8;
        }

        return i;
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		List<ItemStack> list = Lists.newArrayList();
		list.add(new ItemStack(this));
		return list;
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) { //Change this in all these classes
        return Item.getItemFromBlock(Block.getBlockFromName(this.saplingID));
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
                if (axisalignedbb.minY > 0.0D) return true;
                break;
            case UP:
                if (axisalignedbb.maxY < 1.0D) return true;
                break;
            case NORTH:
                if (axisalignedbb.minZ > 0.0D) return true; 
                break;
            case SOUTH: 
            	if (axisalignedbb.minZ > 0.0D) return true;
                break;
            case WEST:
                if (axisalignedbb.minX > 0.0D) return true;
                break;
            case EAST:
                if (axisalignedbb.maxX < 1.0D) return true;
        }

        return !blockAccess.getBlockState(pos.offset(side)).doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
    }
}
