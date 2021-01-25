package com.bob.redwall.entity.capabilities.armor_weight;

import net.minecraft.entity.EntityLivingBase;

public interface IArmorWeight {
	public void set(float points);
	public void update();
	public void updateTick();
	public float get();
	public void init(EntityLivingBase player);
}
