package com.bob.redwall.entity.capabilities.nutrition;

import com.bob.redwall.items.food.ItemModFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Nutrition implements INutrition {
	public static final float MAX_NUTRIENT = 40.0F;
	private float protein = 40.0F; //Fish, beans, rice, and the 'savory' modifier; reduced by the 'shallow' modifier
	private float carbs = 40.0F; //Breads, desserts, and the 'sweet' modifier; reduced by the 'sickly' modifier
	private float veggies = 40.0F; //Veggies and the 'healthy' modifier; reduced by the 'unhealthy' modifier
	private float fruits = 40.0F; //Fruits and the 'healthy' modifier; reduced by the 'unhealthy' modifier
	private float lastFood = 40.0F; //Used to calculate food drop
	
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
		if(food.getItem() instanceof ItemModFood) {
			ItemModFood f = (ItemModFood)food.getItem();
			this.addProtein(f.getProtein());
			this.addCarbs(f.getCarbs());
			this.addVeggies(f.getVeggies());
			this.addFruits(f.getFruits());
		} else {
			if(food.getItem() == Items.FISH) {
				this.addProtein(8);
			} else if(food.getItem() == Items.COOKED_FISH) {
				this.addProtein(16);
			} else if(food.getItem() == Items.POTATO) {
				this.addCarbs(2);
			} else if(food.getItem() == Items.BAKED_POTATO) {
				this.addCarbs(10);
				this.addVeggies(2);
			} else if(food.getItem() == Items.BREAD) {
				this.addCarbs(10);
			} else if(food.getItem() == Items.APPLE) {
				this.addFruits(10);
				this.addCarbs(4);
			} else if(food.getItem() == Items.CARROT) {
				this.addVeggies(6);
				this.addCarbs(2);
			} else if(food.getItem() == Items.BEETROOT) {
				this.addVeggies(2);
				this.addCarbs(1);
			} else if(food.getItem() == Items.BEETROOT_SOUP) {
				this.addVeggies(12);
				this.addCarbs(4);
			} else if(food.getItem() == Items.COOKIE) {
				this.addCarbs(6);
			} else if(food.getItem() == Items.MELON) {
				this.addFruits(10);
				this.addCarbs(4);
			} else if(food.getItem() == Items.MUSHROOM_STEW) {
				this.addProtein(10);
				this.addCarbs(10);
			} else if(food.getItem() == Items.PUMPKIN_PIE) {
				this.addCarbs(8);
				this.addFruits(4);
			}
		}
	}
	
	@Override
	public void update(EntityPlayer player) {
		float dFood = this.lastFood - (player.getFoodStats().getFoodLevel() + player.getFoodStats().getSaturationLevel());
		if(dFood < 0) this.addAllNutrients(dFood); //Decrement nutrients based on player exhaustion
		
		float minNutrient = Math.min(Math.min(this.getProtein(), this.getCarbs()), Math.min(this.getVeggies(), this.getFruits()));
		if(minNutrient <= 20) { //Set food level to most deficient nutrient
			player.getFoodStats().setFoodSaturationLevel(0.0F);
			player.getFoodStats().setFoodLevel((int)minNutrient);
		} else {
			player.getFoodStats().setFoodSaturationLevel(minNutrient - 20.0F);
			player.getFoodStats().setFoodLevel(20);
		}
		
		this.lastFood = player.getFoodStats().getFoodLevel() + player.getFoodStats().getSaturationLevel();
	}
}
