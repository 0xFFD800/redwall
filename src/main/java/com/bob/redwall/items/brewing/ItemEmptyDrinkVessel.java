package com.bob.redwall.items.brewing;

import com.bob.redwall.items.ModItem;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemEmptyDrinkVessel extends ModItem {
	private ResourceLocation filledId;

	public ItemEmptyDrinkVessel(String name, CreativeTabs tab, ResourceLocation filledId) {
		super(name, tab);
		this.filledId = filledId;
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

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
				}

				if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.turnVesselIntoItem(itemstack, playerIn, ItemDrinkVessel.setDrink(new ItemStack(Item.getByNameOrId(this.filledId.toString())), Drink.DrinkList.WATER)));
				}
			}

			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}
	}

	protected ItemStack turnVesselIntoItem(ItemStack p_185061_1_, EntityPlayer player, ItemStack stack) {
		p_185061_1_.shrink(1);
		player.addStat(StatList.getObjectUseStats(this));

		if (p_185061_1_.isEmpty()) {
			return stack;
		} else {
			if (!player.inventory.addItemStackToInventory(stack)) {
				player.dropItem(stack, false);
			}

			return p_185061_1_;
		}
	}
}