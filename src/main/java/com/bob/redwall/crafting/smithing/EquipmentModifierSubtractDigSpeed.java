package com.bob.redwall.crafting.smithing;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class EquipmentModifierSubtractDigSpeed extends EquipmentModifierNegative {
	public EquipmentModifierSubtractDigSpeed(EquipmentModifier.Rarity rarityIn) {
        super(rarityIn, EnumEnchantmentType.DIGGER);
    }
	
	@Override
	public int getMaxLevel() {
        return 3;
    }
	
	@Override
	public float calcDiggingSpeedModifier(int level, Block block) {
		return level == 1 ? 0.75F : level == 2 ? 0.5F : 0.25F;
	}
	
	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
        return !(ench instanceof EquipmentModifierAddDigSpeed) && !(ench instanceof EquipmentModifierSubtractDigSpeed);
    }
	
	@Override
	public String getName() {
        return "modifier.digspeed.subtract.";
    }

	@Override
    public String getTranslatedName(int level) {
        String s = super.getTranslatedName(level);
        
        s = s + ": x" + this.calcDiggingSpeedModifier(level, Blocks.STONE) + " " + I18n.format("attribute.digspeed");
        
        return s;
    }
	
	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
        return level == 1 ? EquipmentModifier.Rarity.COMMON : 
        	level == 2 ? EquipmentModifier.Rarity.UNCOMMON : 
        		EquipmentModifier.Rarity.RARE;
    }

	@Override
    public boolean canApplyOnCrafting(ItemStack stack) {
        return stack.getItem() instanceof ItemTool;
    }
}
