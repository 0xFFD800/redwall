package com.bob.redwall.items.tools;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemScythe extends ModCustomWeapon {
	public ItemScythe(String name, CreativeTabs tab, float spd, float dmg, float reach, Item.ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
	}

	@Override
	protected void attackEntity(EntityLivingBase target, EntityLivingBase attacker, ItemStack stack) { }

	@Override
	public boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker) {
		return true;
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

	@Override
	public boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}
	
	@Override
	@Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		stack.addEnchantment(Enchantments.FORTUNE, 1);
		return null;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		Iterator<Entry<Enchantment, Integer>> i = EnchantmentHelper.getEnchantments(stack).entrySet().iterator();
		Entry<Enchantment, Integer> e;
		while(i.hasNext()) {
			e = i.next();
			if(e.getKey() != Enchantments.FORTUNE || e.getValue() > 1) {
				return true;
			}
		}
		
		return false;
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.WEB) {
            return 5.0F * this.getToolMaterial().getEfficiency();
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : this.getToolMaterial().getEfficiency();
        }
    }
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return super.hitEntity(stack, target, attacker);
    }
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        if (!world.isRemote) {
            stack.damageItem(1, entity);
        }

        return true;
    }
}
