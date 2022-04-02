package com.bob.redwall.crafting.smithing;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EquipmentModifierProjectileProtection extends EquipmentModifier {
	public EquipmentModifierProjectileProtection(EquipmentModifier.Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.ARMOR);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.isProjectile() ? level * 2 : 0;
	}

	@Override
	public boolean canApplyTogether(EquipmentModifier ench) {
		return !(ench instanceof EquipmentModifierProjectileProtection);
	}

	@Override
	public String getName() {
		return "modifier.projectile_protection.";
	}

	@Override
	public String getTranslatedName(int level) {
		String s = super.getTranslatedName(level);

		s = s + ": +" + this.calcModifierDamage(level, DamageSource.causeArrowDamage(null, null)) + " " + I18n.format("protection.projectile");

		return s;
	}

	@Override
	public EquipmentModifier.Rarity getRarity(int level) {
		return level == 1 ? EquipmentModifier.Rarity.COMMON : level == 2 ? EquipmentModifier.Rarity.UNCOMMON : EquipmentModifier.Rarity.RARE;
	}

	@Override
	public boolean canApplyOnCrafting(ItemStack stack) {
		return stack.getItem() instanceof ItemArmor;
	}
}
