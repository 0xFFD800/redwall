package com.bob.redwall.items.weapons.melee;

import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemPike extends ModCustomWeapon {
	public ItemPike(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
	}

	@Override
	protected void attackEntity(EntityLivingBase entity, EntityLivingBase attacker, ItemStack stack) { }

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
		return false;
	}
}
