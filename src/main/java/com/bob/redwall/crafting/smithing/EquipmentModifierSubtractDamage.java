package com.bob.redwall.crafting.smithing;

import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EquipmentModifierSubtractDamage extends EquipmentModifierNegative {
	public EquipmentModifierSubtractDamage(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.WEAPON);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return level == 1 ? -0.5F : level == 2 ? -1.0F : -2.0F;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierAddDamage) && !(ench instanceof EquipmentModifierSubtractDamage);
	}

	@Override
	public String getName() {
		return "modifier.damage.subtract.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": " + this.calcDamageByCreature(level, EnumCreatureAttribute.UNDEFINED) + " " + I18n.format("attribute.name." + SharedMonsterAttributes.ATTACK_DAMAGE.getName());

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : level == 2 ? EquipmentModifier.Rarity.UNCOMMON : EquipmentModifier.Rarity.RARE;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ModCustomWeapon || stack.getItem() instanceof ItemAxe;
	}
}
