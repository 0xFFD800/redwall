package com.bob.redwall.entity.npc.favors;

import net.minecraft.entity.player.EntityPlayer;

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
}
