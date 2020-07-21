package com.bob.redwall.crafting.cooking;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class FoodModifierAddSaturation extends FoodModifier {
	public FoodModifierAddSaturation(FoodModifier.Rarity rarityIn) {
        super(rarityIn);
    }
	
	@Override
	public int getMaxLevel() {
        return 3;
    }
	
	@Override
	public float calcModifierSaturation(int level) {
		return level == 1 ? 0.5F : level == 2 ? 1.0F : 2.0F;
	}
	
	@Override
	public boolean canApplyTogether(FoodModifier ench) {
        return !(ench instanceof FoodModifierAddSaturation) && !(ench instanceof FoodModifierSubtractSaturation);
    }
	
	@Override
	public String getName() {
        return "modifier.saturation.add.";
    }

	@Override
    public String getTranslatedName(int level) {
        String s = super.getTranslatedName(level);
        
        s = s + ": +" + this.calcModifierSaturation(level) + " " + I18n.format("attribute.name.saturation");
        
        return s;
    }
	
	@Override
	public FoodModifier.Rarity getRarity(int level) {
        return level == 1 ? FoodModifier.Rarity.COMMON : 
        	level == 2 ? FoodModifier.Rarity.UNCOMMON : 
        		FoodModifier.Rarity.RARE;
    }

	@Override
    public boolean canApplyOnCrafting(ItemStack stack) {
        return stack.getItem() instanceof ItemFood;
    }
}
