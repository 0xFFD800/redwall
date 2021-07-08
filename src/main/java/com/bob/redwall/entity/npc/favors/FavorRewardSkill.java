package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.factions.Faction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setString("Type", "Skill");
		compound.setString("Faction", this.fac.getID());
		compound.setInteger("StatType", this.type.getId());
		compound.setInteger("LowAmount", this.lowAmount);
		compound.setInteger("HighAmount", this.highAmount);
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if(!c.getString("Type").equals("Skill")) 
			throw new IllegalStateException("Created a Skill favor reward without a Skill tag!");

		this.fac = Faction.getFactionByID(c.getString("Faction"));
		this.type = FactionCap.FacStatType.byID(c.getInteger("StatType"));
		this.lowAmount = c.getInteger("LowAmount");
		this.highAmount = c.getInteger("HighAmount");
	}
}
