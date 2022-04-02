package com.bob.redwall.crafting.cooking;

import java.util.List;

import com.bob.redwall.entity.capabilities.nutrition.INutrition;
import com.bob.redwall.entity.capabilities.nutrition.NutritionProvider;
import com.bob.redwall.tileentity.TileEntityDrinkVessel;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class FoodModifierUtils {
	public static final String MODIFIER_LIST_KEY = "foodMods";

	public static void onConsumed(EntityLivingBase user, ItemStack stack) {
		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for (FoodModifier mod : equipMods) {
			int lvl = FoodModifierUtils.getFoodModifierLevelOnStack(stack, mod);
			mod.onFoodEaten(user, stack, lvl);
			if (user instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) user;
				player.getFoodStats().addStats(mod.calcModifierFood(lvl), mod.calcModifierSaturation(lvl));
				INutrition n = player.getCapability(NutritionProvider.NUTRITION_CAP, null);
				n.addProtein(mod.calcModifierProtein(lvl));
				n.addCarbs(mod.calcModifierCarbs(lvl));
				n.addFruits(mod.calcModifierFruits(lvl));
				n.addVeggies(mod.calcModifierVeggies(lvl));
			}
		}
	}

	public static void onConsumed(EntityLivingBase user, TileEntityDrinkVessel ted) {
		List<FoodModifier> equipMods = ted.getModifiers();
		for (FoodModifier mod : equipMods) {
			int lvl = ted.getLevels().get(equipMods.indexOf(mod));
			mod.onFoodEaten(user, new ItemStack(ted.getBlockType()), lvl);
			if (user instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) user;
				player.getFoodStats().addStats(mod.calcModifierFood(lvl), mod.calcModifierSaturation(lvl));
				INutrition n = player.getCapability(NutritionProvider.NUTRITION_CAP, null);
				n.addProtein(mod.calcModifierProtein(lvl));
				n.addCarbs(mod.calcModifierCarbs(lvl));
				n.addFruits(mod.calcModifierFruits(lvl));
				n.addVeggies(mod.calcModifierVeggies(lvl));
			}
		}
	}

	public static float getAlcoholMultiplier(EntityLivingBase user, ItemStack stack) {
		float f = 1;

		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for (FoodModifier mod : equipMods) {
			int lvl = FoodModifierUtils.getFoodModifierLevelOnStack(stack, mod);
			f *= mod.calcModifierAlcohol(lvl);
		}

		return f;
	}

	public static float getAlcoholMultiplier(EntityLivingBase entityLiving, TileEntityDrinkVessel ted) {
		float f = 1;

		List<FoodModifier> equipMods = ted.getModifiers();
		for (FoodModifier mod : equipMods) {
			int lvl = ted.getLevels().get(equipMods.indexOf(mod));
			f *= mod.calcModifierAlcohol(lvl);
		}

		return f;
	}

	public static float getEffectDurationMultiplier(EntityLivingBase user, ItemStack stack) {
		float f = 1;

		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for (FoodModifier mod : equipMods) {
			int lvl = FoodModifierUtils.getFoodModifierLevelOnStack(stack, mod);
			f *= mod.calcModifierEffectDuration(lvl);
		}

		return f;
	}

	public static float getEffectDurationMultiplier(EntityLivingBase entityLiving, TileEntityDrinkVessel ted) {
		float f = 1;

		List<FoodModifier> equipMods = ted.getModifiers();
		for (FoodModifier mod : equipMods) {
			int lvl = ted.getLevels().get(equipMods.indexOf(mod));
			f *= mod.calcModifierEffectDuration(lvl);
		}

		return f;
	}

	public static float getQualityMultiplier(ItemStack stack) {
		float f = 1.0F;

		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for (FoodModifier mod : equipMods) {
			int lvl = getFoodModifierLevelOnStack(stack, mod);
			f += mod.calcModifierCarbs(lvl) / 40.0F;
			f += mod.calcModifierProtein(lvl) / 40.0F;
			f += mod.calcModifierFruits(lvl) / 40.0F;
			f += mod.calcModifierVeggies(lvl) / 40.0F;
			f += mod.calcModifierFood(lvl) / 10.0F;
			f += mod.calcModifierSaturation(lvl) / 10.0F;
			f *= mod.calcModifierEffectDuration(lvl);
		}

		return f;
	}

	public static boolean hasFoodModifier(ItemStack stack, FoodModifier mod) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					FoodModifier modifier = FoodModifier.getModifierByID(compound.getInteger("id"));

					if (modifier == mod)
						return true;
				}
			}
		}

		return false;
	}

	public static List<FoodModifier> getFoodModifiersOnStack(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				List<FoodModifier> equipMods = Lists.newArrayList();
				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					FoodModifier modifier = FoodModifier.getModifierByID(compound.getInteger("id"));
					equipMods.add(modifier);
				}

				return equipMods;
			}
		}

		return Lists.newArrayList();
	}

	public static int getFoodModifierLevelOnStack(ItemStack stack, FoodModifier mod) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);

				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					if (compound.getInteger("id") == FoodModifier.getModifierID(mod))
						return compound.getInteger("lvl");
				}
			}
		}

		return 0;
	}
}
