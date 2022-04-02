package com.bob.redwall.crafting.smithing;

import net.minecraft.enchantment.EnumEnchantmentType;

public abstract class EquipmentModifierNegative extends EquipmentModifier {
	protected EquipmentModifierNegative(Rarity rarityIn, EnumEnchantmentType typeIn) {
		super(rarityIn, typeIn);
	}

	@Override
	public boolean isNegative() {
		return true;
	}

	@Override
	public int getQuality(int level) {
		return -((111 - this.getRarity(level).getWeight()) / 2);
	}
}
