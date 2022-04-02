package com.bob.redwall.crafting.smithing;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EquipmentModifierAddDurability extends EquipmentModifier {
	public EquipmentModifierAddDurability(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.DIGGER);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public float calcDurabilityMultiplier(int level) {
		return level == 1 ? 1.25F : level == 2 ? 1.5F : level == 3 ? 2.0F : level == 4 ? 3.0F : 4.0F;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierAddDurability) && !(ench instanceof EquipmentModifierSubtractDurability);
	}

	@Override
	public String getName() {
		return "modifier.durability.add.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": x" + this.calcDurabilityMultiplier(level) + " " + I18n.format("attribute.durability");

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : level == 2 ? EquipmentModifier.Rarity.COMMON : level == 3 ? EquipmentModifier.Rarity.UNCOMMON : level == 4 ? EquipmentModifier.Rarity.RARE : EquipmentModifier.Rarity.EPIC;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.isItemStackDamageable();
	}
}
