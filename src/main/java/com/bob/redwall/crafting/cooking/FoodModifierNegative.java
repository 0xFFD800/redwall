package com.bob.redwall.crafting.cooking;

public abstract class FoodModifierNegative extends FoodModifier {
	protected FoodModifierNegative(Rarity rarityIn) {
		super(rarityIn);
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
