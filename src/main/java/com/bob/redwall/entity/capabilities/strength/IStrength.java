package com.bob.redwall.entity.capabilities.strength;

import net.minecraft.entity.EntityLivingBase;

public interface IStrength extends IPlayerStats {
	public void subtract(int points);
	public void set(int points);
	public void add(int points);
	public void update();
	public int get();
	public int getActual();
	public void init(EntityLivingBase player);
}
