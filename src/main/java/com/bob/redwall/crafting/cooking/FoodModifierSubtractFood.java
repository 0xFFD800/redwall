package com.bob.redwall.crafting.cooking;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class FoodModifierSubtractFood extends FoodModifierNegative {
	public FoodModifierSubtractFood(FoodModifier.Rarity rarityIn) {
		super(rarityIn);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int calcModifierFood(int level) {
		return level == 1 ? -1 : level == 2 ? -2 : -3;
	}

	@Override
	public float calcModifierProtein(int level) {
		return level == 1 ? -1 : level == 2 ? -2 : -3;
	}

	@Override
	public float calcModifierCarbs(int level) {
		return level == 1 ? -1 : level == 2 ? -2 : -3;
	}

	@Override
	public float calcModifierFruits(int level) {
		return level == 1 ? -1 : level == 2 ? -2 : -3;
	}

	@Override
	public float calcModifierVeggies(int level) {
		return level == 1 ? -1 : level == 2 ? -2 : -3;
	}

	@Override
	public boolean canApplyTogether(FoodModifier ench) {
		return !(ench instanceof FoodModifierAddFood) && !(ench instanceof FoodModifierSubtractFood);
	}

	@Override
	public String getName() {
		return "modifier.foodall.subtract.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": " + this.calcModifierFood(level) + " " + I18n.format("attribute.name.foodall");

		return s;
	}

	@Override
	public FoodModifier.Rarity getRarity(int level) {
		return level == 1 ? FoodModifier.Rarity.COMMON : level == 2 ? FoodModifier.Rarity.UNCOMMON : FoodModifier.Rarity.RARE;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ItemFood;
	}
}
