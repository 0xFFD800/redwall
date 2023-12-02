package com.bob.redwall.crafting.cooking;

import com.bob.redwall.items.brewing.ItemDrinkVessel;
import com.bob.redwall.items.food.ItemModFood;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class FoodModifierSubtractEffectDuration extends FoodModifierNegative {
	public FoodModifierSubtractEffectDuration(FoodModifier.Rarity rarityIn) {
		super(rarityIn);
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public float calcModifierEffectDuration(int level) {
		return 0.5F;
	}

	@Override
	public boolean canApplyTogether(FoodModifier ench) {
		return !(ench instanceof FoodModifierAddEffectDuration) && !(ench instanceof FoodModifierSubtractEffectDuration);
	}

	@Override
	public String getName() {
		return "modifier.effectduration.add.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": x" + this.calcModifierEffectDuration(level) + " " + I18n.format("attribute.name.effectduration");

		return s;
	}

	@Override
	public FoodModifier.Rarity getRarity(int level) {
		return FoodModifier.Rarity.UNCOMMON;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return (stack.getItem() instanceof ItemModFood && ((ItemModFood) stack.getItem()).hasStatusEffects()) || stack.getItem() instanceof ItemDrinkVessel && ((ItemDrinkVessel) stack.getItem()).hasStatusEffects(stack);
	}
}
