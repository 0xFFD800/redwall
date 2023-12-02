package com.bob.redwall.entity.capabilities.factions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.entity.npc.favors.IFavorCondition;
import com.bob.redwall.entity.npc.favors.IFavorReward;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;

public class FactionCap implements IFactionCap {
	private Map<Faction, FacStats> map = new HashMap<>();
	private Map<Faction, Boolean> contactMap = new HashMap<>();
	private List<Favor> activeFavors = new ArrayList<>();
	private List<Favor> toRemove = new ArrayList<>();
	private EntityPlayer player;
	private boolean isInit;

	public FactionCap() {
		for (Faction fac : Faction.getAllFactions()) {
			this.map.put(fac, new FacStats());
			this.contactMap.put(fac, false);
		}
	}

	@Override
	public void set(Faction fac, FacStatType type, float points, boolean update) {
		if (this.player != null && update) {
			if (type != FacStatType.LOYALTY && points > this.get(fac, FacStatType.LOYALTY)) {
				this.set(fac, type, this.get(fac, FacStatType.LOYALTY), update);
				return;
			}

			RedwallUtils.checkAndHandleLevelChange(points, this.player, fac, type);
		}
		this.map.get(fac).setStat(type, points);

		if (type == FacStatType.LOYALTY)
			for (FacStatType t : FacStatType.values()) {
				if (t == FacStatType.LOYALTY)
					continue;
				else if (this.get(fac, t) > points)
					this.set(fac, t, points, update);
			}
	}

	@Override
	public float get(Faction fac, FacStatType type) {
		return this.map.get(fac).getStat(type);
	}

	@Override
	public Map<Faction, FacStats> getStats() {
		return this.map;
	}

	@Override
	public void setPlayerContacted(Faction fac, boolean val) {
		this.contactMap.put(fac, val);
	}

	@Override
	public boolean getPlayerContacted(Faction fac) {
		return this.contactMap.get(fac);
	}

	@Override
	public void addFavor(Favor favor) {
		this.activeFavors.add(favor);
	}
	
	@Override
	public void removeFavor(Favor favor) {
		this.toRemove.add(favor);
	}
	
	@Override
	public void updateFavors() {
		this.activeFavors.removeAll(this.toRemove);
	}

	@Override
	public List<Favor> getFavors() {
		return this.activeFavors;
	}

	@Override
	public void init(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		Map<Faction, FacStats> map = this.getStats();
		for (Faction fac : map.keySet()) {
			NBTTagCompound ntc = new NBTTagCompound();
			ntc.setBoolean("hasContact", this.getPlayerContacted(fac));
			NBTTagList list = new NBTTagList();
			for (int i = 0; i < FacStatType.numStats(); i++)
				list.appendTag(new NBTTagFloat(this.get(fac, FacStatType.byID(i))));

			ntc.setTag("stats", list);
			tag.setTag(fac.getID(), ntc);
		}

		NBTTagList favors = new NBTTagList();
		for (Favor favor : this.activeFavors)
			favors.appendTag(favor.writeToNBT());
		tag.setTag("Favors", favors);

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		for (String facID : tag.getKeySet()) {
			NBTTagCompound ntc = tag.getCompoundTag(facID);
			Faction fac = Faction.getFactionByID(facID);
			this.setPlayerContacted(fac, ntc.getBoolean("hasContact"));
			NBTTagList list = ntc.getTagList("stats", Constants.NBT.TAG_FLOAT);
			for (int i = 0; i < list.tagCount(); i++)
				this.set(fac, FacStatType.byID(i), list.getFloatAt(i), false);
		}

		this.activeFavors.clear();
		for (NBTBase favor : tag.getTagList("Favors", NBT.TAG_COMPOUND)) {
			Favor f = new Favor(this.player, null, "", new ArrayList<IFavorCondition>(), new ArrayList<IFavorReward>(), new ArrayList<IFavorReward>(), 0);
			f.readFromNBT(this.player, (NBTTagCompound) favor);
			this.activeFavors.add(f);
		}
	}

	private static int i = 0;

	public static enum FacStatType {
		LOYALTY(i++, "loyalty"), FIGHT(i++, "fight"), SMITH(i++, "smith"), FARM(i++, "farm"), BREW(i++, "brew"), COOK(i++, "cook"), SCOFF(i++, "scoff");

		private int id;
		private String name;

		private FacStatType(int i, String name) {
			this.id = i;
			this.name = name;
		}

		public String getLocalizedName() {
			return I18n.format("facstat." + this.name + ".name");
		}

		public int getId() {
			return this.id;
		}

		protected static int numStats() {
			return i;
		}

		public static FacStatType byID(int ID) {
			for (FacStatType type : FacStatType.values())
				if (ID == type.id)
					return type;

			Ref.LOGGER.warn("WARNING: Tried to get unknown faction stat type " + ID + "!");
			return null;
		}
	}

	protected static class FacStats {
		private float[] stats = new float[7];

		private void setStat(FacStatType type, float val) {
			this.stats[type.id] = val;
		}

		private float getStat(FacStatType type) {
			return this.stats[type.id];
		}
	}

	@Override
	public void setInit() {
		this.isInit = true;
	}

	@Override
	public boolean isInitialized() {
		return this.isInit;
	}
}
