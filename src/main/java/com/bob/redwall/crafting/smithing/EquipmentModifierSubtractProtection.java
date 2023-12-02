package com.bob.redwall.crafting.smithing;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EquipmentModifierSubtractProtection extends EquipmentModifierNegative {
	public EquipmentModifierSubtractProtection(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.ARMOR);
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return -level;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierAddProtection) && !(ench instanceof EquipmentModifierSubtractProtection);
	}

	@Override
	public String getName() {
		return "modifier.protection.subtract.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": " + this.calcModifierDamage(level, DamageSource.GENERIC) + " " + I18n.format("attribute.name." + SharedMonsterAttributes.ARMOR.getName());

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : EquipmentModifier.Rarity.UNCOMMON;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ItemArmor;
	}
}
