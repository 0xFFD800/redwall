package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;

import net.minecraft.item.ItemStack;

public class FavorConditionGiveItemSpecific implements IFavorCondition {
	private ItemStack item;
	private Favor favor;
	private boolean complete;
	
	public FavorConditionGiveItemSpecific(ItemStack item, int number) {
		this.item = item;
		this.complete = false;
	}

	@Override
	public void killNPC(EntityAbstractNPC npc) {
		
	}

	@Override
	public ItemStack offerItem(ItemStack item) {
		if(item.equals(this.item)){
			this.complete = true;
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
}
