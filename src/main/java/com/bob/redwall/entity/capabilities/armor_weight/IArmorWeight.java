package com.bob.redwall.entity.capabilities.armor_weight;

import net.minecraft.entity.player.EntityPlayer;

public interface IArmorWeight {
	public void set(float points);
	public void update();
	public float get();
	public void init(EntityPlayer player);
}
