package com.bob.redwall.entity.capabilities.agility;

import com.bob.redwall.entity.capabilities.strength.IPlayerStats;

import net.minecraft.entity.EntityLivingBase;

public interface IAgility extends IPlayerStats {
	public void subtract(int points);
	public void set(int points);
	public void add(int points);
	public void update();
	public int get();
	public void init(EntityLivingBase player);
	public void setInit();
	public boolean isInitialized();
}
