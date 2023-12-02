package com.bob.redwall.crafting.smithing;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EquipmentModifierSubtractDurability extends EquipmentModifierNegative {
	public EquipmentModifierSubtractDurability(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.DIGGER);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public float calcDurabilityMultiplier(int level) {
		return level == 1 ? 0.8F : level == 2 ? 0.65F : 0.5F;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierAddDurability) && !(ench instanceof EquipmentModifierSubtractDurability);
	}

	@Override
	public String getName() {
		return "modifier.durability.subtract.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": x" + this.calcDurabilityMultiplier(level) + " " + I18n.format("attribute.durability");

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : level == 2 ? EquipmentModifier.Rarity.COMMON : EquipmentModifier.Rarity.UNCOMMON;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.isItemStackDamageable();
	}
}
