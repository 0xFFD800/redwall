package com.bob.redwall.crafting.smithing;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants;

public class EquipmentModifierUtils {
	public static final String MODIFIER_LIST_KEY = "equipMods";
	
	public static float getQualityMultiplier(ItemStack stack) {
		float i = 1.0F;
		
		List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
		for(EquipmentModifier mod : equipMods) {
			int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
			i += mod.calcDamageByCreature(lvl, EnumCreatureAttribute.UNDEFINED) / 10.0F;
			i += mod.calcModifierDamage(lvl, DamageSource.GENERIC) / 10.0F;
			i *= mod.calcDiggingSpeedModifier(lvl, Blocks.AIR);
			i *= mod.calcDurabilityMultiplier(lvl);
		}
		
		return i;
	}
	
	public static float getAdditionalAttack(EntityLivingBase attacker, EntityLivingBase defender) {
		float i = 0;
		ItemStack stack = attacker.getHeldItemMainhand();
		
		List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
		for(EquipmentModifier mod : equipMods) {
			int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
			i += mod.calcDamageByCreature(lvl, defender.getCreatureAttribute()) / 10.0F;
		}
		
		return i;
	}
	
	public static void doAttack(EntityLivingBase attacker, EntityLivingBase defender) {
		ItemStack stack = attacker.getHeldItemMainhand();
		
		List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
		for(EquipmentModifier mod : equipMods) {
			int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
			mod.onEntityDamaged(attacker, defender, lvl);
		}
	}
	
	public static int getDamageModifierDefense(EntityLivingBase defender, DamageSource source) {
		int i = 0;
		for(ItemStack stack : defender.getArmorInventoryList()) {
			List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
			for(EquipmentModifier mod : equipMods) {
				int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
				i += mod.calcModifierDamage(lvl, source);
			}
		}
		
		return i;
	}
	
	public static void doDefense(EntityLivingBase defender, EntityLivingBase attacker) {
		for(ItemStack stack : defender.getArmorInventoryList()) {
			List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
			for(EquipmentModifier mod : equipMods) {
				int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
				mod.onUserHurt(defender, attacker, lvl);
			}
		}
	}
	
	public static float getDiggingSpeedMultiplier(EntityLivingBase digger, Block block) {
		float i = 1;
		ItemStack stack = digger.getHeldItemMainhand();
		
		List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
		for(EquipmentModifier mod : equipMods) {
			int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
			i += mod.calcDiggingSpeedModifier(lvl, block);
		}
		
		return i;
	}

	/**
	 * Gets the multiplier to durability from equipment modifiers. 
	 * Note that this isn't called from a class, it's called direct from RedwallClassTransformer.
	 * @param stack The item stack that needs modified durability
	 * @return The number to multiply the durability by
	 */
	public static float getDurabilityMultiplier(ItemStack stack) {
		float i = 1;
		
		List<EquipmentModifier> equipMods = EquipmentModifierUtils.getEquipmentModifiersOnStack(stack);
		for(EquipmentModifier mod : equipMods) {
			int lvl = EquipmentModifierUtils.getEquipmentModifierLevelOnStack(stack, mod);
			i += mod.calcDurabilityMultiplier(lvl);
		}
		
		return i;
	}
	
	public static boolean hasEquipmentModifier(ItemStack stack, EquipmentModifier mod) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					EquipmentModifier modifier = EquipmentModifier.getModifierByID(compound.getInteger("id"));
					
					if(modifier == mod) return true;
				}
			}
		}
		
		return false;
	}
	
	public static List<EquipmentModifier> getEquipmentModifiersOnStack(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				List<EquipmentModifier> equipMods = Lists.newArrayList();
				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					EquipmentModifier modifier = EquipmentModifier.getModifierByID(compound.getInteger("id"));
					equipMods.add(modifier);
				}
				
				return equipMods;
			}
		}
		
		return Lists.newArrayList();
	}
	
	public static int getEquipmentModifierLevelOnStack(ItemStack stack, EquipmentModifier mod) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);

				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					if(compound.getInteger("id") == EquipmentModifier.getModifierID(mod)) {
						return compound.getInteger("lvl");
					}
				}
			}
		}
		
		return 0;
	}
}
