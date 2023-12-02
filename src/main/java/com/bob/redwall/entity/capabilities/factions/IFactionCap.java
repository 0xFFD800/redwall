package com.bob.redwall.entity.capabilities.factions;

import java.util.List;
import java.util.Map;

import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStats;
import com.bob.redwall.entity.npc.favors.Favor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IFactionCap {
	public void set(Faction fac, FacStatType type, float points, boolean update);
	public float get(Faction fac, FacStatType type);
	public Map<Faction, FacStats> getStats();
	public void setPlayerContacted(Faction fac, boolean value);
	public boolean getPlayerContacted(Faction fac);
	public void addFavor(Favor favor);
	public void removeFavor(Favor favor);
	public void updateFavors();
	public List<Favor> getFavors();
	public void init(EntityPlayer player);
	public NBTTagCompound writeToNBT();
	public void readFromNBT(NBTTagCompound tag);
	public void setInit();
	public boolean isInitialized();
}
