package com.bob.redwall.entity.capabilities.nutrition;

import java.util.Random;

import com.bob.redwall.entity.statuseffect.StatusEffect;
import com.bob.redwall.items.food.ItemModFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class Nutrition implements INutrition {
	// Static 'cause they're client-side only
	public static float drunkMoveAngle = 0.0F; // If the player is drunk, which direction should their screen move?
	public static NoiseGeneratorPerlin drunkMove = new NoiseGeneratorPerlin(new Random(), 8);
	
	public static final float MAX_NUTRIENT = 40.0F;
	private float protein = 40.0F; // Fish, beans, rice, and the 'savory' modifier; reduced by the 'shallow' modifier
	private float carbs = 40.0F; // Breads, desserts, and the 'sweet' modifier; reduced by the 'sickly' modifier
	private float veggies = 40.0F; // Veggies and the 'healthy' modifier; reduced by the 'unhealthy' modifier
	private float fruits = 40.0F; // Fruits and the 'healthy' modifier; reduced by the 'unhealthy' modifier
	private float bac = 0.0F; // Blood alcohol content. How close to getting drunk is the player?
	private float lastFood = 40.0F; // Used to calculate food drop

	@Override
	public void addProtein(float points) {
		this.protein += Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public float getProtein() {
		return this.protein;
	}

	@Override
	public void setProtein(float points) {
		this.protein = Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public void addCarbs(float points) {
		this.carbs += Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public float getCarbs() {
		return this.carbs;
	}

	@Override
	public void setCarbs(float points) {
		this.carbs = Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public void addVeggies(float points) {
		this.veggies += Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public float getVeggies() {
		return this.veggies;
	}

	@Override
	public void setVeggies(float points) {
		this.veggies = Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public void addFruits(float points) {
		this.fruits += Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public void setFruits(float points) {
		this.fruits = Math.min(Nutrition.MAX_NUTRIENT, points);
	}

	@Override
	public float getFruits() {
		return this.fruits;
	}

	@Override
	public void addBAC(float points) {
		this.bac += points;
		System.out.println(this.bac);
	}

	@Override
	public float getBAC() {
		return this.bac;
	}

	@Override
	public void setBAC(float points) {
		this.bac = points;
	}

	@Override
	public void addAllNutrients(float points) {
		this.addProtein(points);
		this.addCarbs(points);
		this.addVeggies(points);
		this.addFruits(points);
	}

	@Override
	public float getLastFood() {
		return this.lastFood;
	}

	@Override
	public void setLastFood(float points) {
		this.lastFood = points;
	}

	@Override
	public void eatFood(ItemStack food) {
		if (food.getItem() instanceof ItemModFood) {
			ItemModFood f = (ItemModFood) food.getItem();
			this.addProtein(f.getProtein());
			this.addCarbs(f.getCarbs());
			this.addVeggies(f.getVeggies());
			this.addFruits(f.getFruits());
		} else {
			if (food.getItem() == Items.FISH) {
				this.addProtein(8);
				this.addCarbs(1);
				this.addVeggies(2);
				this.addFruits(1);
			} else if (food.getItem() == Items.COOKED_FISH) {
				this.addProtein(16);
				this.addCarbs(5);
				this.addVeggies(4);
				this.addFruits(4);
			} else if (food.getItem() == Items.POTATO) {
				this.addProtein(1);
				this.addCarbs(2);
				this.addVeggies(1);
				this.addFruits(1);
			} else if (food.getItem() == Items.BAKED_POTATO) {
				this.addProtein(3);
				this.addCarbs(10);
				this.addVeggies(4);
				this.addFruits(2);
			} else if (food.getItem() == Items.BREAD) {
				this.addProtein(4);
				this.addCarbs(10);
				this.addVeggies(3);
				this.addFruits(4);
			} else if (food.getItem() == Items.APPLE) {
				this.addProtein(2);
				this.addCarbs(4);
				this.addVeggies(3);
				this.addFruits(10);
			} else if (food.getItem() == Items.CARROT) {
				this.addProtein(1);
				this.addCarbs(2);
				this.addVeggies(6);
				this.addFruits(2);
			} else if (food.getItem() == Items.BEETROOT) {
				this.addProtein(1);
				this.addCarbs(1);
				this.addVeggies(3);
				this.addFruits(2);
			} else if (food.getItem() == Items.BEETROOT_SOUP) {
				this.addProtein(4);
				this.addCarbs(5);
				this.addVeggies(12);
				this.addFruits(6);
			} else if (food.getItem() == Items.COOKIE) {
				this.addProtein(2);
				this.addCarbs(8);
				this.addVeggies(1);
				this.addFruits(2);
			} else if (food.getItem() == Items.MELON) {
				this.addProtein(2);
				this.addCarbs(6);
				this.addVeggies(4);
				this.addFruits(12);
			} else if (food.getItem() == Items.MUSHROOM_STEW) {
				this.addProtein(14);
				this.addCarbs(14);
				this.addVeggies(4);
				this.addFruits(4);
			} else if (food.getItem() == Items.PUMPKIN_PIE) {
				this.addProtein(3);
				this.addCarbs(12);
				this.addVeggies(1);
				this.addFruits(4);
			}
		}
	}

	@Override
	public void update(EntityPlayer player) {
		float dFood = (player.getFoodStats().getFoodLevel() + player.getFoodStats().getSaturationLevel()) - this.lastFood;
		if (dFood < 0)
			this.addAllNutrients(dFood); // Decrement nutrients based on player exhaustion

		float minNutrient = Math.min(Math.min(this.getProtein(), this.getCarbs()), Math.min(this.getVeggies(), this.getFruits()));
		if (minNutrient <= 20) { // Set food level to most deficient nutrient
			player.getFoodStats().setFoodSaturationLevel(0.0F);
			player.getFoodStats().setFoodLevel((int) minNutrient);
		} else {
			player.getFoodStats().setFoodSaturationLevel(minNutrient - 20.0F);
			player.getFoodStats().setFoodLevel(20);
		}

		this.lastFood = player.getFoodStats().getFoodLevel() + player.getFoodStats().getSaturationLevel();

		if (this.bac > 0.08)
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
		
		if (this.bac > 0.16)
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
		
		if (this.bac > 0.20)
			player.addPotionEffect(new PotionEffect(StatusEffect.POISON, 200));
		
		if (this.bac > 0.0)
			this.bac -= 0.00002F;
	}
}
