package com.bob.redwall.entity.npc.favors;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setString("Type", "Item");
		compound.setInteger("ItemID", Item.getIdFromItem(this.item));
		compound.setInteger("LowAmount", this.lowAmount);
		compound.setInteger("HighAmount", this.highAmount);
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if(!c.getString("Type").equals("Item")) 
			throw new IllegalStateException("Created an Item favor reward without an Item tag!");

		this.item = Item.getItemById(c.getInteger("ItemID"));
		this.lowAmount = c.getInteger("LowAmount");
		this.highAmount = c.getInteger("HighAmount");
	}

	@Override
	public String getText() {
		return I18n.format("favor.reward.item", this.lowAmount, this.highAmount, I18n.format(this.item.getUnlocalizedName()));
	}
}
