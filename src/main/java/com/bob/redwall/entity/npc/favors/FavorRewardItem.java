package com.bob.redwall.entity.npc.favors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FavorRewardItem implements IFavorReward {
	private Item item;
	private int lowAmount;
	private int highAmount;
	
	public FavorRewardItem(Item item, int lowAmount, int highAmount) {
		this.item = item;
		this.lowAmount = lowAmount;
		this.highAmount = highAmount;
	}
	
	@Override
	public void reward(EntityPlayer player) {
		ItemStack stack = new ItemStack(this.item, player.getRNG().nextInt(this.highAmount - this.lowAmount) + this.lowAmount);
		if(!player.addItemStackToInventory(stack)) {
			player.dropItem(stack, false);
		}
	}
}
