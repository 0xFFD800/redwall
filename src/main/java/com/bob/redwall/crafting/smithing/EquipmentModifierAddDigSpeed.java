package com.bob.redwall.crafting.smithing;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class EquipmentModifierAddDigSpeed extends EquipmentModifier {
	public EquipmentModifierAddDigSpeed(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.DIGGER);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public float calcDiggingSpeedModifier(int level, Block block) {
		return level == 1 ? 1.25F : level == 2 ? 1.5F : level == 3 ? 2.0F : level == 4 ? 3.0F : 4.0F;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierAddDigSpeed) && !(ench instanceof EquipmentModifierSubtractDigSpeed);
	}

	@Override
	public String getName() {
		return "modifier.digspeed.add.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": x" + this.calcDiggingSpeedModifier(level, Blocks.STONE) + " " + I18n.format("attribute.digspeed");

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : level == 2 ? EquipmentModifier.Rarity.UNCOMMON : level == 3 ? EquipmentModifier.Rarity.RARE : level == 4 ? EquipmentModifier.Rarity.EPIC : EquipmentModifier.Rarity.LEGENDARY;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ItemTool;
	}
}
