package com.bob.redwall.crafting.smithing;

import com.bob.redwall.items.weapons.ModCustomWeapon;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EquipmentModifierAddDamage extends EquipmentModifier {
	public EquipmentModifierAddDamage(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.WEAPON);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return level == 1 ? 0.5F : level == 2 ? 1.0F : level == 3 ? 2.0F : level == 4 ? 3.0F : 5.0F;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierAddDamage) && !(ench instanceof EquipmentModifierSubtractDamage);
	}

	@Override
	public String getName() {
		return "modifier.damage.add.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": +" + this.calcDamageByCreature(level, EnumCreatureAttribute.UNDEFINED) + " " + I18n.format("attribute.name." + SharedMonsterAttributes.ATTACK_DAMAGE.getName());

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : level == 2 ? EquipmentModifier.Rarity.UNCOMMON : level == 3 ? EquipmentModifier.Rarity.RARE : level == 4 ? EquipmentModifier.Rarity.EPIC : EquipmentModifier.Rarity.LEGENDARY;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ModCustomWeapon || stack.getItem() instanceof ItemAxe;
	}
}
