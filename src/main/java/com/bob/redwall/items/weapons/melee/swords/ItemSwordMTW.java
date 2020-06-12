package com.bob.redwall.items.weapons.melee.swords;

import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemSwordMTW extends ModCustomWeapon {
	public ItemSwordMTW(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    	this.attackEntity(target, attacker, stack);
        return true;
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
	public boolean isSweep(ItemStack stack, EntityLivingBase attacker) {
		return true;
	}
	
	@Override
	public boolean isStab(ItemStack stack, EntityLivingBase attacker) {
		return true;
	}

	@Override
	public boolean canBlock(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}
	
	@Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
        return this.isBludgeon(stack, attacker);
    }

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.WEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }
	
	@Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return blockIn.getBlock() == Blocks.WEB;
    }
}
