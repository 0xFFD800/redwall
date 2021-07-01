package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.factions.Faction;

import net.minecraft.entity.player.EntityPlayer;

public class FavorRewardSkill implements IFavorReward {
	private Faction fac;
	private FactionCap.FacStatType type;
	private int lowAmount;
	private int highAmount;
	
	public FavorRewardSkill(Faction fac, FactionCap.FacStatType type, int lowAmount, int highAmount) {
		this.fac = fac;
		this.type = type;
		this.lowAmount = lowAmount;
		this.highAmount = highAmount;
	}

	@Override
	public void reward(EntityPlayer player) {
		player.getCapability(FactionCapProvider.FACTION_CAP, null).set(this.fac, this.type, player.getCapability(FactionCapProvider.FACTION_CAP, null).get(this.fac, this.type) + player.getRNG().nextInt(this.highAmount - this.lowAmount) + this.lowAmount, true);
	}
}
