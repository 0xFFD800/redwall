package com.bob.redwall.items.weapons.throwable;

import java.util.Set;

import javax.annotation.Nullable;

import com.bob.redwall.entity.projectile.throwing_axe.EntityThrowingAxe;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.items.weapons.ModCustomWeapon;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemThrowingAxe extends ModCustomWeapon {
	private final Set<Block> effectiveBlocks = Sets.newHashSet(new Block[] {Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE});
    private final float efficiencyOnProperMaterial;
	
	public ItemThrowingAxe(String name, CreativeTabs tab, float speed, float dmg, float reach, ToolMaterial material) {
		super(name, tab, speed, dmg, reach, material);
        this.efficiencyOnProperMaterial = material.getEfficiency()/2;
		this.addPropertyOverride(new ResourceLocation("throwing"), new IItemPropertyGetter() {
			@Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && !entityIn.isSneaking() ? 1.0F : 0.0F;
            }
        });
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
        return 8;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote && !playerIn.isSneaking() && handIn == EnumHand.MAIN_HAND) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack itemstack, World worldIn, EntityLivingBase playerIn) {
        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            EntityThrowingAxe entitythrowingaxe = new EntityThrowingAxe(worldIn, playerIn, itemstack);
            entitythrowingaxe.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 1.0F);
            worldIn.spawnEntity(entitythrowingaxe);
        }

        if(playerIn instanceof EntityPlayer) ((EntityPlayer)playerIn).addStat(StatList.getObjectUseStats(this));
        return ItemStack.EMPTY;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return ItemHandler.SPEAR_ACTION;
    }

	@Override
	public boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state))
                return efficiencyOnProperMaterial;
        }
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
    }

	@Override
	protected void attackEntity(EntityLivingBase target, EntityLivingBase attacker, ItemStack stack) { }

	@Override
	public boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker) {
		return false;
	}

	@Override
	public boolean isBludgeon(ItemStack stack, EntityLivingBase attacker) {
		return false;
	}

	@Override
	public boolean isSweep(ItemStack weapon, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean isStab(ItemStack weapon, EntityLivingBase player) {
		return false;
	}

	@Override
	public boolean canBlock(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}
}
