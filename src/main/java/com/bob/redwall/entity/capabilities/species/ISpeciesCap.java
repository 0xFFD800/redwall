package com.bob.redwall.entity.capabilities.species;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface ISpeciesCap {
	public void set(Species s);
	public Species get();
	public void update();
	public void updateTick();
	public void init(EntityPlayer player);
	public NBTTagCompound writeToNBT();
	public void readFromNBT(NBTTagCompound tag);
	public void setInit();
	public boolean isInitialized();
}
