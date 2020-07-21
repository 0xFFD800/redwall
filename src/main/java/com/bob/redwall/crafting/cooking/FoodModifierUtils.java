package com.bob.redwall.crafting.cooking;

import java.util.List;

import com.bob.redwall.entity.capabilities.nutrition.INutrition;
import com.bob.redwall.entity.capabilities.nutrition.NutritionProvider;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class FoodModifierUtils {
	public static final String MODIFIER_LIST_KEY = "foodMods";
	
	public static void onConsumed(EntityLivingBase user, ItemStack foodordrink) {
		ItemStack stack = user.getHeldItem(user.getActiveHand());
		
		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for(FoodModifier mod : equipMods) {
			int lvl = FoodModifierUtils.getFoodModifierLevelOnStack(stack, mod);
			mod.onFoodEaten(user, foodordrink, lvl);
            if(user instanceof EntityPlayer) {
            	EntityPlayer player = (EntityPlayer)user;
            	player.getFoodStats().addStats(mod.calcModifierFood(lvl), mod.calcModifierSaturation(lvl));
            	INutrition n = player.getCapability(NutritionProvider.NUTRITION_CAP, null);
            	n.addProtein(mod.calcModifierProtein(lvl));
            	n.addCarbs(mod.calcModifierCarbs(lvl));
            	n.addFruits(mod.calcModifierFruits(lvl));
            	n.addVeggies(mod.calcModifierVeggies(lvl));
            }
		}
	}
	
	public static float getAlcoholMultiplier(EntityLivingBase user, ItemStack drink) {
		ItemStack stack = user.getHeldItem(user.getActiveHand());
		float f = 1;
		
		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for(FoodModifier mod : equipMods) {
			int lvl = FoodModifierUtils.getFoodModifierLevelOnStack(stack, mod);
			f *= mod.calcModifierAlcohol(lvl);
		}
		
		return f;
	}
	
	public static float getEffectDurationMultiplier(EntityLivingBase user, ItemStack foodordrink) {
		ItemStack stack = user.getHeldItem(user.getActiveHand());
		float f = 1;
		
		List<FoodModifier> equipMods = FoodModifierUtils.getFoodModifiersOnStack(stack);
		for(FoodModifier mod : equipMods) {
			int lvl = FoodModifierUtils.getFoodModifierLevelOnStack(stack, mod);
			f *= mod.calcModifierEffectDuration(lvl);
		}
		
		return f;
	}
	
	public static boolean hasFoodModifier(ItemStack stack, FoodModifier mod) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					FoodModifier modifier = FoodModifier.getModifierByID(compound.getInteger("id"));
					
					if(modifier == mod) return true;
				}
			}
		}
		
		return false;
	}
	
	public static List<FoodModifier> getFoodModifiersOnStack(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);
				List<FoodModifier> equipMods = Lists.newArrayList();
				for(int i = 0; i < list.tagCount(); i++) {
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
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null && nbt.hasKey(MODIFIER_LIST_KEY)) {
				NBTTagList list = nbt.getTagList(MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND);

				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound compound = list.getCompoundTagAt(i);
					if(compound.getInteger("id") == FoodModifier.getModifierID(mod)) {
						return compound.getInteger("lvl");
					}
				}
			}
		}
		
		return 0;
	}
}
