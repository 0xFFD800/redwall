package com.bob.redwall.entity.npc.favors;

import java.util.List;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.entity.player.EntityPlayer;

public class Favor {
	private EntityPlayer player;
	private EntityAbstractNPC giver;
	private List<IFavorCondition> conditions;
	private List<IFavorReward> success;
	private List<IFavorReward> failure;
	private long timeLimit;

	public Favor(EntityPlayer player, EntityAbstractNPC giver, List<IFavorCondition> conditions, List<IFavorReward> success, List<IFavorReward> failure, long timeLimit) {
		this.player = player;
		this.giver = giver;
		this.conditions = conditions;
		for (IFavorCondition c : this.conditions)
			c.setFavor(this);
		this.timeLimit = timeLimit;
		this.success = success;
		this.failure = failure;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}

	public EntityAbstractNPC getGiver() {
		return this.giver;
	}

	public List<IFavorCondition> getConditions() {
		return this.conditions;
	}

	public boolean isComplete() {
		for (IFavorCondition c : this.conditions)
			if (!c.isComplete()) return false;
		return true;
	}

	public void update() {
		if (--this.timeLimit <= 0) this.fail();
	}

	public void fail() {
		for(IFavorReward r : this.failure)
			r.reward(this.player);
	}

	public void succeed() {
		for(IFavorReward r : this.success)
			r.reward(this.player);
	}
}
