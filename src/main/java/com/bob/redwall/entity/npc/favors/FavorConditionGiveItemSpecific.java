package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FavorConditionGiveItemSpecific implements IFavorCondition {
	private ItemStack item;
	private Favor favor;
	private boolean complete;

	public FavorConditionGiveItemSpecific(ItemStack item) {
		this.item = item;
		this.complete = false;
	}

	@Override
	public void killNPC(EntityAbstractNPC npc) {

	}

	@Override
	public ItemStack offerItem(ItemStack item) {
		if (item.equals(this.item)) {
			this.complete = true;
			this.favor.update();
			return ItemStack.EMPTY;
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

		compound.setString("Type", "GiveItemSpecific");
		compound.setTag("Item", this.item.writeToNBT(new NBTTagCompound()));
		compound.setBoolean("Complete", this.complete);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if (!c.getString("Type").equals("GiveItemSpecific"))
			throw new IllegalStateException("Created a GiveItemSpecific favor condition without a GiveItemSpecific tag!");

		this.item = new ItemStack(c.getCompoundTag("Item"));
		this.complete = c.getBoolean("Complete");
	}

	@Override
	public String getText() {
		return I18n.format("favor.condition.giveitemspecific", this.item.getDisplayName(), this.getGiver().getName());
	}
}
