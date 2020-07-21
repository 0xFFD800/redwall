package com.bob.redwall.entity.capabilities.nutrition;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface INutrition {
	public void addProtein(float points);
	public float getProtein();
	public void setProtein(float points);
	public void addCarbs(float points);
	public float getCarbs();
	public void setCarbs(float points);
	public void addVeggies(float points);
	public float getVeggies();
	public void setVeggies(float points);
	public void addFruits(float points);
	public float getFruits();
	public void setFruits(float points);
	public void addBAC(float points);
	public float getBAC();
	public void setBAC(float points);
	public void addAllNutrients(float points);
	public float getLastFood();
	public void setLastFood(float points);
	public void eatFood(ItemStack itemStack);
	public void update(EntityPlayer player);
}
