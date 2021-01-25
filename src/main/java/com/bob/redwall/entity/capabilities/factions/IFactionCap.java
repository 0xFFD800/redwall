package com.bob.redwall.entity.capabilities.factions;

import java.util.Map;

import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStats;
import com.bob.redwall.factions.Faction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IFactionCap {
	public void set(Faction fac, FacStatType type, float points, boolean update);
	public float get(Faction fac, FacStatType type);
	public Map<Faction, FacStats> getStats();
	public void setPlayerContacted(Faction fac, boolean value);
	public boolean getPlayerContacted(Faction fac);
	public void init(EntityPlayer player);
	public NBTTagCompound writeToNBT();
	public void readFromNBT(NBTTagCompound tag);
	public void setInit();
	public boolean isInitialized();
}
