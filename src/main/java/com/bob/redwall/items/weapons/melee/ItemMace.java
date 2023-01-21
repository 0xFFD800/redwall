package com.bob.redwall.items.weapons.melee;

import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemMace extends ModCustomWeapon {
	public ItemMace(String name, CreativeTabs tab, float damage, float speed, float reach, ToolMaterial material) {
		super(name, tab, damage, speed, reach, material);
	}

	public ItemMace(String name, CreativeTabs tab, float spd, float dmg, float reach, ToolMaterial material, Faction faction) {
		super(name, tab, spd, dmg, reach, material, faction);
	}

	@Override
	protected void attackEntity(EntityLivingBase entity, EntityLivingBase attacker, ItemStack stack) { }

	@Override
	public boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker) {
		return true;
	}

	@Override
	public boolean isBludgeon(ItemStack stack, EntityLivingBase attacker) {
		return true;
	}

	@Override
	public boolean isSweep(ItemStack weapon, EntityLivingBase player) {
		return false;
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
}
