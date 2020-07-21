package com.bob.redwall.items.brewing;

import javax.annotation.Nullable;

import com.bob.redwall.items.ModItem;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrinkVessel extends ModItem {
	public ItemDrinkVessel(String name, CreativeTabs tab) {
		super(name, tab);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		ItemDrinkVessel.setDrink(stack, Drink.DrinkList.WATER);
        return stack;
    }
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for(Drink drink : Drink.BY_ID.values()) {
            	items.add(ItemDrinkVessel.setDrink(this.getDefaultInstance().copy(), drink));
            }
        }
    }
	
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        if (entityplayer instanceof EntityPlayerMP) {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
        }

        if (!worldIn.isRemote) {
            if(ItemDrinkVessel.getDrink(stack) != null) ItemDrinkVessel.getDrink(stack).onConsumed(stack, worldIn, entityLiving);
        }

        if (entityplayer != null) {
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(this.getContainerItem());
            }

            if (entityplayer != null) {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(this.getContainerItem()));
            }
        }

        return stack;
    }
	
	public static ItemStack setDrink(ItemStack stack, Drink drink) {
		ItemDrinkVessel.getTagCompoundSafe(stack).setString("drink", drink.getID().toString());
		return stack;
	}
	
	@Nullable
	public static Drink getDrink(ItemStack stack) {
		return Drink.BY_ID.get(new ResourceLocation(ItemDrinkVessel.getTagCompoundSafe(stack).getString("drink")));
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

	@Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

	@Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(this.getUnlocalizedName(stack) + ".name").trim() + " " + I18n.format("drink.".concat(ItemDrinkVessel.getDrink(stack) == null ? "empty" : ItemDrinkVessel.getDrink(stack).getID().getResourcePath()));
    }

    private static NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }
	
	public boolean hasStatusEffects(ItemStack stack) {
		return ItemDrinkVessel.getDrink(stack) != null && ItemDrinkVessel.getDrink(stack).hasStatusEffects();
	}
}
