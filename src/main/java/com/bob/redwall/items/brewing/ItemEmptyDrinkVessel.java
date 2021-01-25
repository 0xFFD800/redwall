package com.bob.redwall.items.brewing;

import com.bob.redwall.items.ModItem;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemEmptyDrinkVessel extends ModItem {
	private ResourceLocation filledId;
	private ResourceLocation blockId;

	public ItemEmptyDrinkVessel(String name, CreativeTabs tab, ResourceLocation filledId, ResourceLocation blockId) {
		super(name, tab);
		this.filledId = filledId;
		this.blockId = blockId;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

		if (raytraceresult == null) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		} else {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos blockpos = raytraceresult.getBlockPos();
	            Vec3d vec = raytraceresult.hitVec;
	            EnumFacing facing = raytraceresult.sideHit;

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
				}

				if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.turnVesselIntoItem(itemstack, playerIn, ItemDrinkVessel.setDrink(new ItemStack(Item.getByNameOrId(this.filledId.toString())), Drink.DrinkList.WATER)));
				} else if(worldIn.getTileEntity(blockpos) instanceof TileEntityDrinkVessel) {
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					TileEntityDrinkVessel te = (TileEntityDrinkVessel)worldIn.getTileEntity(blockpos);
					if(te.getDrink() != null) {
						te.setDrink(null);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.turnVesselIntoItem(itemstack, playerIn, ItemDrinkVessel.setDrink(new ItemStack(Item.getByNameOrId(this.filledId.toString())), te.getDrink())));
					} else {
						return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
					}
				} else {
					if (!worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos)) {
						blockpos = blockpos.offset(facing);
			        }
					if (!itemstack.isEmpty() && playerIn.canPlayerEdit(blockpos, facing, itemstack) && worldIn.mayPlace(this.makeBlock(), blockpos, false, facing, (Entity)null)) {
			            int i = this.getMetadata(itemstack.getMetadata());
			            IBlockState iblockstate1 = this.makeBlock().getStateForPlacement(worldIn, blockpos, facing, (float)vec.x, (float)vec.y, (float)vec.z, i, playerIn, handIn);

			            if (this.placeBlockAt(itemstack, playerIn, worldIn, blockpos, facing, (float)vec.x, (float)vec.y, (float)vec.z, iblockstate1)) {
			                iblockstate1 = worldIn.getBlockState(blockpos);
			                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, blockpos, playerIn);
			                worldIn.playSound(playerIn, blockpos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			                itemstack.shrink(1);
			            }

			            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
			        }
				}
			}

			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}
	}

	protected ItemStack turnVesselIntoItem(ItemStack stackToChange, EntityPlayer player, ItemStack stack) {
		stackToChange.shrink(1);
		player.addStat(StatList.getObjectUseStats(this));

		if (stackToChange.isEmpty()) {
			return stack;
		} else {
			if (!player.inventory.addItemStackToInventory(stack)) {
				player.dropItem(stack, false);
			}

			return stackToChange;
		}
	}
	
	protected Block makeBlock() {
		return Block.getBlockFromName(this.blockId.toString());
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.makeBlock()) {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            this.makeBlock().onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP) CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;
    }
}