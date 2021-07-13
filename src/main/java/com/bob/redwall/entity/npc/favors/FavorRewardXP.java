package com.bob.redwall.entity.npc.favors;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class FavorRewardXP implements IFavorReward {
	private int lowAmount;
	private int highAmount;
	
	public FavorRewardXP(int lowAmount, int highAmount) {
		this.lowAmount = lowAmount;
		this.highAmount = highAmount;
	}

	@Override
	public void reward(EntityPlayer player) {
		player.addExperience(player.getRNG().nextInt(this.highAmount - this.lowAmount) + this.lowAmount);
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setString("Type", "XP");
		compound.setInteger("LowAmount", this.lowAmount);
		compound.setInteger("HighAmount", this.highAmount);
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if(!c.getString("Type").equals("Item")) 
			throw new IllegalStateException("Created an XP favor reward without an XP tag!");

		this.lowAmount = c.getInteger("LowAmount");
		this.highAmount = c.getInteger("HighAmount");
	}

	@Override
	public String getText() {
		return I18n.format("favor.reward.xp", this.lowAmount, this.highAmount);
	}
}
