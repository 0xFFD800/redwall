package com.bob.redwall.crafting.cooking;

import com.bob.redwall.items.brewing.ItemDrinkVessel;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class FoodModifierAddNutrient extends FoodModifier {
	private String nutrient;

	public FoodModifierAddNutrient(FoodModifier.Rarity rarityIn, String nutrient) {
		super(rarityIn);
		this.nutrient = nutrient;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public float calcModifierProtein(int level) {
		return nutrient != "protein" ? 0 : level == 1 ? 1.0F : 2.0F;
	}

	@Override
	public float calcModifierCarbs(int level) {
		return nutrient != "carbs" ? 0 : level == 1 ? 1.0F : 2.0F;
	}

	@Override
	public float calcModifierFruits(int level) {
		return nutrient != "fruits" ? 0 : level == 1 ? 1.0F : 2.0F;
	}

	@Override
	public float calcModifierVeggies(int level) {
		return nutrient != "veggies" ? 0 : level == 1 ? 1.0F : 2.0F;
	}

	@Override
	public boolean canApplyTogether(FoodModifier ench) {
		return !(ench instanceof FoodModifierAddNutrient) && !(ench instanceof FoodModifierSubtractFood);
	}

	@Override
	public String getName() {
		return "modifier." + this.nutrient + ".add.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": +" + level + " " + I18n.format("attribute.name." + this.nutrient);

		return s;
	}

	@Override
	public FoodModifier.Rarity getRarity(int level) {
		return level == 1 ? FoodModifier.Rarity.UNCOMMON : FoodModifier.Rarity.EPIC;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemDrinkVessel;
	}
}
