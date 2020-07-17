package com.bob.redwall.items.weapons.throwable;

import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.entity.projectile.dagger.EntityDagger;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDagger extends ModCustomWeapon {
	public ItemDagger(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
		this.addPropertyOverride(new ResourceLocation("throwing"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && !entityIn.isSneaking() ? 1.0F : 0.0F;
            }
        });
	}
	
	public ItemDagger(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material, Faction faction) {
		super(name, tab, spd, dmg, reach, material, faction);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
		this.addPropertyOverride(new ResourceLocation("throwing"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && !entityIn.isSneaking() ? 1.0F : 0.0F;
            }
        });
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return ItemHandler.SPEAR_ACTION;
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
            EntityDagger entitydagger = new EntityDagger(worldIn, playerIn, itemstack);
            entitydagger.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 1.0F);
            worldIn.spawnEntity(entitydagger);
        }

        if(playerIn instanceof EntityPlayer) ((EntityPlayer)playerIn).addStat(StatList.getObjectUseStats(this));
        return ItemStack.EMPTY;
    }

	@Override
	protected void attackEntity(EntityLivingBase entity, EntityLivingBase attacker, ItemStack stack) {
        for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
            entity.addPotionEffect(new PotionEffect(potioneffect));
        }
        PotionUtils.addPotionToItemStack(stack, PotionTypes.EMPTY);
	}

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
		return false;
	}

	@Override
	public boolean isStab(ItemStack weapon, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canBlock(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}

	@Override
	public boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
