package com.bob.redwall.crafting.cooking;

import com.bob.redwall.items.brewing.ItemDrinkVessel;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class FoodModifierAddAlcohol extends FoodModifierNegative {
	public FoodModifierAddAlcohol(FoodModifier.Rarity rarityIn) {
        super(rarityIn);
    }
	
	@Override
	public int getMaxLevel() {
        return 2;
    }
	
	@Override
	public float calcModifierAlcohol(int level) {
		return level == 1 ? 1.5F : 2.0F;
	}
	
	@Override
	public boolean canApplyTogether(FoodModifier ench) {
        return !(ench instanceof FoodModifierSubtractAlcohol) && !(ench instanceof FoodModifierAddAlcohol);
    }
	
	@Override
	public String getName() {
        return "modifier.alcohol.add.";
    }

	@Override
    public String getTranslatedName(int level) {
        String s = super.getTranslatedName(level);
        
        s = s + ": x" + this.calcModifierAlcohol(level) + " " + I18n.format("attribute.name.alcohol");
        
        return s;
    }
	
	@Override
	public FoodModifier.Rarity getRarity(int level) {
        return level == 1 ? FoodModifier.Rarity.COMMON : 
        	FoodModifier.Rarity.UNCOMMON;
    }

	@Override
    public boolean canApplyOnCrafting(ItemStack stack) {
        return stack.getItem() instanceof ItemDrinkVessel && ItemDrinkVessel.getDrink(stack) != null && ItemDrinkVessel.getDrink(stack).getAlcohol() > 0.0F;
    }
}
