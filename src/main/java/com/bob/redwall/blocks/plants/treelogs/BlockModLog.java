package com.bob.redwall.blocks.plants.treelogs;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockModLog extends BlockLog {
	public BlockModLog(String name, CreativeTabs tab) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	BlockLog.EnumAxis enumfacing$axis = BlockLog.EnumAxis.Y;
        int i = meta & 12;

        if (i == 4) {
            enumfacing$axis = BlockLog.EnumAxis.X;
        } else if (i == 8) {
            enumfacing$axis = BlockLog.EnumAxis.Z;
        } else if (i == 12) {
        	enumfacing$axis = BlockLog.EnumAxis.NONE;
        }

        return this.getDefaultState().withProperty(LOG_AXIS, enumfacing$axis);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        BlockLog.EnumAxis enumfacing$axis = (BlockLog.EnumAxis)state.getValue(LOG_AXIS);

        if (enumfacing$axis == BlockLog.EnumAxis.X) {
            i |= 4;
        } else if (enumfacing$axis == BlockLog.EnumAxis.Z) {
            i |= 8;
        } else if (enumfacing$axis == BlockLog.EnumAxis.NONE) {
        	i |= 12;
        }

        return i;
    }
}
