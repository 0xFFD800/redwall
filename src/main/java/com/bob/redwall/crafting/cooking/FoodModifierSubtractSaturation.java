package com.bob.redwall.crafting.cooking;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class FoodModifierSubtractSaturation extends FoodModifierNegative {
	public FoodModifierSubtractSaturation(FoodModifier.Rarity rarityIn) {
		super(rarityIn);
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public float calcModifierSaturation(int level) {
		return level == 1 ? -0.5F : -1.0F;
	}

	@Override
	public boolean canApplyTogether(FoodModifier ench) {
		return !(ench instanceof FoodModifierAddSaturation) && !(ench instanceof FoodModifierSubtractSaturation);
	}

	@Override
	public String getName() {
		return "modifier.saturation.subtract.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": " + this.calcModifierSaturation(level) + " " + I18n.format("attribute.name.saturation");

		return s;
	}

	@Override
	public FoodModifier.Rarity getRarity(int level) {
		return level == 1 ? FoodModifier.Rarity.COMMON : FoodModifier.Rarity.UNCOMMON;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ItemFood;
	}
}
