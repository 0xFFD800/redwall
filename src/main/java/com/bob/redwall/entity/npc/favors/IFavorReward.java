package com.bob.redwall.entity.npc.favors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IFavorReward {
	public void reward(EntityPlayer player);

	public NBTTagCompound writeToNBT();

	public void readFromNBT(NBTTagCompound c);

	public String getText();
}
