package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FavorConditionGiveItems implements IFavorCondition {
	private Item item;
	private int number;
	private int numberGiven;
	private Favor favor;
	private boolean complete;
	
	public FavorConditionGiveItems(Item item, int number) {
		this.item = item;
		this.number = number;
		this.numberGiven = 0;
		this.complete = false;
	}

	@Override
	public void killNPC(EntityAbstractNPC npc) {
		
	}

	@Override
	public ItemStack offerItem(ItemStack item) {
		if(item.getItem().equals(this.item)) {
			if(item.getCount() >= this.number - this.numberGiven) {
				this.complete = true;
				item.shrink(this.number - this.numberGiven);
				this.numberGiven = this.number;
				this.favor.update();
				return item;
			} else {
				this.numberGiven += item.getCount();
				this.favor.update();
				return ItemStack.EMPTY;
			}
		}
		
		return item;
	}

	@Override
	public void destroyStructure(EntityStructureCenter center) {
		
	}

	@Override
	public EntityAbstractNPC getGiver() {
		return this.favor != null ? this.favor.getGiver() : null;
	}

	@Override
	public void setFavor(Favor favor) {
		this.favor = favor;
	}

	@Override
	public boolean isComplete() {
		return this.complete;
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setString("Type", "GiveItems");
		compound.setInteger("ItemID", Item.getIdFromItem(this.item));
		compound.setInteger("Number", this.number);
		compound.setInteger("NumberGiven", this.numberGiven);
		compound.setBoolean("Complete", this.complete);
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if(!c.getString("Type").equals("GiveItems")) 
			throw new IllegalStateException("Created a GiveItems favor condition without a GiveItems tag!");
		
		this.item = Item.getItemById(c.getInteger("ItemID"));
		this.number = c.getInteger("Number");
		this.numberGiven = c.getInteger("NumberGiven");
		this.complete = c.getBoolean("Complete");
	}

	@Override
	public String getText() {
		return I18n.format("favor.condition.giveitems", this.number, I18n.format(this.item.getUnlocalizedName() + ".name"), this.getGiver().getName());
	}
}
