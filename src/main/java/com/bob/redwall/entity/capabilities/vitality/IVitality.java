package com.bob.redwall.entity.capabilities.vitality;

import com.bob.redwall.entity.capabilities.strength.IPlayerStats;

import net.minecraft.entity.EntityLivingBase;

public interface IVitality extends IPlayerStats {
	public void subtract(int points);
	public void set(int points);
	public void add(int points);
	public void update();
	public int get();
	public int getActual();
	public void init(EntityLivingBase player);
}
