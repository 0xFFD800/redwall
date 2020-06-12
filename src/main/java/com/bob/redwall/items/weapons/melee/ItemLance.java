package com.bob.redwall.items.weapons.melee;

import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemLance extends ModCustomWeapon {
	public ItemLance(String name, CreativeTabs tab, float spd, float dmg, float reach, Item.ToolMaterial material) {
		super(name, tab, spd, dmg, reach, material);
	}

	@Override
	protected void attackEntity(EntityLivingBase entity, EntityLivingBase attacker, ItemStack stack) { }

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
		return false;
	}
}
