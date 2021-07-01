package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;
import com.bob.redwall.factions.Faction.FactionStatus;

import net.minecraft.item.ItemStack;

public class FavorConditionDestroyStructure implements IFavorCondition {
	private EntityStructureCenter center;
	private Favor favor;
	private boolean complete;

	public FavorConditionDestroyStructure(EntityStructureCenter center) {
		this.center = center;
		this.complete = false;
	}

	@Override
	public void killNPC(EntityAbstractNPC npc) {
		if (this.favor != null && npc.getFaction().getFactionStatus(this.favor.getGiver().getFaction()) == FactionStatus.ALLIED) this.favor.fail();
	}

	@Override
	public ItemStack offerItem(ItemStack item) {
		return item;
	}

	@Override
	public void destroyStructure(EntityStructureCenter center) {
		if (center.equals(this.center)) this.complete = true;
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
