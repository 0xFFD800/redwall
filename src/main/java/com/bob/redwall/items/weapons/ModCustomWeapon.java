package com.bob.redwall.items.weapons;

import java.util.UUID;

import javax.annotation.Nullable;

import com.bob.redwall.entity.capabilities.factions.Faction;
import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ModCustomWeapon extends Item {
	protected static final UUID REACH_MODIFIER = UUID.fromString("AF27BE1C-CC20-FF65-181B-87CE97213C51");
	private float attackDamage;
	private Item.ToolMaterial material;
	private float attackSpeed;
	private float reach;
	private Faction faction;

	public ModCustomWeapon(String name, CreativeTabs tab, float spd, float dmg, float reach, Item.ToolMaterial material) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(tab);
		this.setMaxStackSize(1);
		if (material != null) this.setMaxDamage(material.getMaxUses());
		this.attackDamage = dmg;
		this.reach = reach;
		this.material = material;
		this.attackSpeed = spd;
	}

	public ModCustomWeapon(String name, CreativeTabs tab, float spd, float dmg, float reach, Item.ToolMaterial material, Faction faction) {
		this(name, tab, spd, dmg, reach, material);
		this.faction = faction;
	}

	public float getDamageVsEntity() {
		return this.attackDamage;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) this.attackSpeed, 0));
			multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_MODIFIER, "Weapon modifier", (double) this.reach, 0));
		}

		return multimap;
	}

	protected abstract void attackEntity(EntityLivingBase target, EntityLivingBase attacker, ItemStack stack);

	/**
	 * Whether an item is two handed, and hence can only be swung when the off hand
	 * is free.
	 * 
	 * @param stack
	 *            The item stack to decide on.
	 * @param attacker
	 *            The entity attacking with it.
	 * @return Whether the item requires two hands to wield.
	 */
	public abstract boolean isTwoHanded(ItemStack stack, EntityLivingBase attacker);

	/**
	 * Decides the type of weapon this will be.
	 * 
	 * @return Whether or not this weapon is treated as a bludgeon. If this method
	 *         returns false, then this weapon will be treated as a pierce weapon.
	 */
	public abstract boolean isBludgeon(ItemStack stack, EntityLivingBase attacker);

	/**
	 * Returns true if the item can perform sweep attacks. If both this and
	 * {@code isStab} return false, this weapon will switch to being a bludgeon.
	 * 
	 * @return Whether or not this weapon can perform sweep attacks. If this method
	 *         returns false, then this weapon will only deal stab attacks.
	 */
	public abstract boolean isSweep(ItemStack weapon, EntityLivingBase player);

	/**
	 * Returns true if the item can perform stab attacks. If both this and
	 * {@code isSweep} return false, this weapon will switch to being a bludgeon.
	 * 
	 * @return Whether or not this weapon can perform stab attacks. If this method
	 *         returns false, then this weapon will only deal sweep attacks.
	 */
	public abstract boolean isStab(ItemStack weapon, EntityLivingBase player);

	/**
	 * Returns true if the item can block, like a sword.
	 * 
	 * @return Whether or not this weapon can block. If this method returns false,
	 *         then sneaking and holding a mouse button will instead trigger the
	 *         shield.
	 */
	public abstract boolean canBlock(ItemStack itemstack, EntityLivingBase player);

	/**
	 * Returns true if the item has an ability activated when it is right clicked.
	 * If this and isSweep are true, the weapon will not be able to conduct a
	 * sideways sweep.
	 * 
	 * @return Whether or not this weapon has a right click ability.
	 */
	public abstract boolean hasRightClickAbility(ItemStack itemstack, EntityLivingBase player);

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		this.attackEntity(target, attacker, stack);
		stack.damageItem(1, attacker);
		return true;
	}

	public ToolMaterial getToolMaterial() {
		return this.material;
	}
	
	@Nullable
	public Faction getFaction() {
		return this.faction;
	}

	public float getReach() {
		return this.reach;
	}
}
