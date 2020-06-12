package com.bob.redwall.items.blocks;

import com.bob.redwall.blocks.BlockWeaponRackHorizontal;
import com.bob.redwall.blocks.BlockWeaponRackVertical;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.items.ModItem;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWeaponRack extends ModItem {
    public ItemWeaponRack(String name, CreativeTabs tab, int stacksize) {
    	super(name, tab, stacksize);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        boolean flag = iblockstate.getBlock().isReplaceable(worldIn, pos);

        if (facing != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP)) {
            pos = pos.offset(facing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && ((BlockHandler.weapon_rack_vertical.canPlaceBlockAt(worldIn, pos) || BlockHandler.weapon_rack_horizontal.canPlaceBlockAt(worldIn, pos)))) {
                if (worldIn.isRemote) {
                    return EnumActionResult.SUCCESS;
                } else {
                    pos = flag ? pos.down() : pos;

                    if (facing == EnumFacing.UP) {
                        worldIn.setBlockState(pos, BlockHandler.weapon_rack_vertical.getDefaultState().withProperty(BlockWeaponRackVertical.FACING, player.getHorizontalFacing()), 11);
                    } else {
                        worldIn.setBlockState(pos, BlockHandler.weapon_rack_horizontal.getDefaultState().withProperty(BlockWeaponRackHorizontal.FACING, facing), 11);
                    }
                    
                    if (player instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        } else {
            return EnumActionResult.FAIL;
        }
    }
}