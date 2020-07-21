package com.bob.redwall.items.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.bob.redwall.crafting.cooking.FoodModifierUtils;
import com.google.common.collect.Lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemModFood extends ItemFood {
	private final Map<String, PotionEffect> effects = new HashMap<String, PotionEffect>();
	private final Map<String, Float> probabilities = new HashMap<String, Float>();
	private final List<String> ids = Lists.newArrayList();
	private float protein;
	private float carbs;
	private float veggies;
	private float fruits;

	public ItemModFood(String name, CreativeTabs tab, int amount, float saturation, float prot, float carb, float veg, float frut) {
		super(amount, saturation, false);
		this.setRegistryName(name);
		this.setCreativeTab(tab);
		this.setUnlocalizedName(name);
		this.protein = prot;
		this.carbs = carb;
		this.veggies = veg;
		this.fruits = frut;
	}

	public Item addPotionEffect(PotionEffect effect, float probability) {
		String string = String.valueOf(new UUID(new Random().nextLong(), new Random().nextLong()).hashCode());
		this.effects.put(string, effect);
		this.probabilities.put(string, probability);
		this.ids.add(string);
		return this;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote) {
			for (String id : this.ids) {
				PotionEffect potionId = this.effects.get(id);
				float potionEffectProbability = this.probabilities.get(id);
				if (potionId != null && player.getRNG().nextFloat() < potionEffectProbability) {
					if (potionId.getPotion().isInstant()) {
						new PotionEffect(potionId).getPotion().affectEntity(player, player, player, potionId.getAmplifier(), 1.0D);
	                } else {
	                	player.addPotionEffect(new PotionEffect(potionId.getPotion(), (int)((float)potionId.getDuration() * FoodModifierUtils.getEffectDurationMultiplier(player, stack)), potionId.getAmplifier(), potionId.getIsAmbient(), potionId.doesShowParticles()));
	                }
				}
			}
		}
		super.onFoodEaten(stack, worldIn, player);
	}

	public float getProtein() {
		return this.protein;
	}

	public float getCarbs() {
		return this.carbs;
	}

	public float getVeggies() {
		return this.veggies;
	}

	public float getFruits() {
		return this.fruits;
	}
	
	public boolean hasStatusEffects() {
		return !this.ids.isEmpty();
	}
}
