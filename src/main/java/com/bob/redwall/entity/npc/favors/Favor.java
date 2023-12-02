package com.bob.redwall.entity.npc.favors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.dimensions.redwall.WorldTypeRedwall;
import com.bob.redwall.dimensions.redwall.structures.MapGenScatteredFeatureRedwall;
import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

public class Favor {
	private EntityPlayer player;
	private EntityAbstractNPC giver;
	private List<IFavorCondition> conditions;
	private List<IFavorReward> success;
	private List<IFavorReward> failure;
	private long timeLimit;
	private String story;
	private UUID giverID = new UUID(0, 0);

	public Favor(EntityPlayer player, EntityAbstractNPC giver, String story, List<IFavorCondition> conditions, List<IFavorReward> success, List<IFavorReward> failure, long timeLimit) {
		this.player = player;
		this.giver = giver;
		if (this.giver != null)
			this.giverID = this.giver.getUniqueID();
		this.conditions = conditions;
		for (IFavorCondition c : this.conditions)
			c.setFavor(this);
		this.timeLimit = timeLimit;
		this.success = success;
		this.failure = failure;
		this.story = story;
	}

	public EntityPlayer getPlayer() {
		return this.player;
	}

	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}

	public EntityAbstractNPC getGiver() {
		if (this.player != null)
			this.giver = (EntityAbstractNPC) RedwallUtils.getEntityByUUID(this.player.world, this.giverID);
		return this.giver;
	}

	public void setGiver(UUID uuid) {
		this.giverID = uuid;
		if (this.player != null)
			this.giver = (EntityAbstractNPC) RedwallUtils.getEntityByUUID(this.player.world, this.giverID);
	}

	public UUID getGiverID() {
		return this.giverID;
	}

	public String getStory() {
		return String.format(this.story, this.player != null ? this.player.getDisplayNameString() : "traveler");
	}

	public long getTimeLimit() {
		return this.timeLimit;
	}

	public List<IFavorCondition> getConditions() {
		return this.conditions;
	}

	public List<IFavorReward> getFailureRewards() {
		return this.failure;
	}

	public List<IFavorReward> getSuccessRewards() {
		return this.success;
	}

	public boolean isComplete() {
		for (IFavorCondition c : this.conditions)
			if (!c.isComplete())
				return false;
		return true;
	}

	public void update() {
		if (this.isComplete())
			this.succeed();
		if (--this.timeLimit <= 0)
			this.fail();
	}

	public void fail() {
		for (IFavorReward r : this.failure)
			r.reward(this.player);
		if (this.player != null)
			this.player.getCapability(FactionCapProvider.FACTION_CAP, null).removeFavor(this);
	}

	public void succeed() {
		for (IFavorReward r : this.success)
			r.reward(this.player);
		if (this.player != null)
			this.player.getCapability(FactionCapProvider.FACTION_CAP, null).removeFavor(this);
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setLong("GiverMost", this.giverID.getMostSignificantBits());
		compound.setLong("GiverLeast", this.giverID.getLeastSignificantBits());
		compound.setString("Story", this.story);

		NBTTagList conditions = new NBTTagList();
		for (IFavorCondition c : this.conditions)
			conditions.appendTag(c.writeToNBT());
		compound.setTag("Conditions", conditions);

		NBTTagList success = new NBTTagList();
		for (IFavorReward r : this.success)
			success.appendTag(r.writeToNBT());
		compound.setTag("Success", success);

		NBTTagList failure = new NBTTagList();
		for (IFavorReward r : this.failure)
			failure.appendTag(r.writeToNBT());
		compound.setTag("Failure", failure);

		compound.setLong("TimeLimit", this.timeLimit);

		return compound;
	}

	public void readFromNBT(EntityPlayer player, NBTTagCompound c) {
		this.player = player;
		this.giverID = new UUID(c.getLong("GiverMost"), c.getLong("GiverLeast"));
		if (this.player != null)
			this.giver = (EntityAbstractNPC) RedwallUtils.getEntityByUUID(this.player.world, this.giverID);

		this.story = c.getString("Story");

		NBTTagList conditions = c.getTagList("Conditions", Constants.NBT.TAG_COMPOUND);
		for (NBTBase con : conditions) {
			NBTTagCompound co = (NBTTagCompound) con;
			switch (co.getString("Type")) {
			case "DestroyStructure":
				IFavorCondition ifc = new FavorConditionDestroyStructure(0, 0, null);
				ifc.readFromNBT(co);
				ifc.setFavor(this);
				this.conditions.add(ifc);
				break;
			case "GiveItems":
				IFavorCondition ifc2 = new FavorConditionGiveItems(null, 0);
				ifc2.readFromNBT(co);
				ifc2.setFavor(this);
				this.conditions.add(ifc2);
				break;
			case "GiveItemSpecific":
				IFavorCondition ifc3 = new FavorConditionGiveItemSpecific(null);
				ifc3.readFromNBT(co);
				ifc3.setFavor(this);
				this.conditions.add(ifc3);
				break;
			case "KillNPC":
				IFavorCondition ifc4 = new FavorConditionKillNPC(null);
				ifc4.readFromNBT(co);
				ifc4.setFavor(this);
				this.conditions.add(ifc4);
				break;
			}
		}

		NBTTagList success = c.getTagList("Success", Constants.NBT.TAG_COMPOUND);
		for (NBTBase con : success) {
			NBTTagCompound co = (NBTTagCompound) con;
			switch (co.getString("Type")) {
			case "Item":
				IFavorReward ifc = new FavorRewardItem(null, 0, 0);
				ifc.readFromNBT(co);
				this.success.add(ifc);
				break;
			case "Skill":
				IFavorReward ifc2 = new FavorRewardSkill(null, null, 0, 0);
				ifc2.readFromNBT(co);
				this.success.add(ifc2);
				break;
			case "XP":
				IFavorReward ifc3 = new FavorRewardXP(0, 0);
				ifc3.readFromNBT(co);
				this.success.add(ifc3);
				break;
			}
		}

		NBTTagList failure = c.getTagList("Failure", Constants.NBT.TAG_COMPOUND);
		for (NBTBase con : failure) {
			NBTTagCompound co = (NBTTagCompound) con;
			switch (co.getString("Type")) {
			case "Item":
				IFavorReward ifc = new FavorRewardItem(null, 0, 0);
				ifc.readFromNBT(co);
				this.failure.add(ifc);
				break;
			case "Skill":
				IFavorReward ifc2 = new FavorRewardSkill(null, null, 0, 0);
				ifc2.readFromNBT(co);
				this.failure.add(ifc2);
				break;
			case "XP":
				IFavorReward ifc3 = new FavorRewardXP(0, 0);
				ifc3.readFromNBT(co);
				this.failure.add(ifc3);
				break;
			}
		}

		this.timeLimit = c.getLong("TimeLimit");
	}

	public static Favor createFavorCollectItems(List<EntityAbstractNPC.EquipmentChance> items, List<String> stories, EntityPlayer player, EntityAbstractNPC giver, int typesL, int typesH, int numL, int numH, long timeLimitLow, long timeLimitHigh) {
		int types = giver.getRNG().nextInt(typesH - typesL) + typesL;
		IFavorCondition[] conditions = new IFavorCondition[types];
		List<Item> usedItems = new ArrayList<>();

		for (int i = 0; i < types; i++) {
			Item item = WeightedRandom.getRandomItem(giver.getRNG(), items).getItem();
			while (usedItems.contains(item))
				item = WeightedRandom.getRandomItem(giver.getRNG(), items).getItem();
			usedItems.add(item);

			int num = giver.getRNG().nextInt(numH - numL) + numL;
			conditions[i] = new FavorConditionGiveItems(item, num);
		}

		IFavorReward[] success = new IFavorReward[3];
		success[0] = new FavorRewardItem(Items.GOLD_NUGGET, numL * 2, numH * 2);
		success[1] = new FavorRewardSkill(giver.getFaction(), FactionCap.FacStatType.LOYALTY, numL * 2, numH * 2);
		success[2] = new FavorRewardXP(numL / 2, numH / 2);

		IFavorReward[] failure = new IFavorReward[1];
		failure[0] = new FavorRewardSkill(giver.getFaction(), FactionCap.FacStatType.LOYALTY, -numL / 2, -numH / 2);

		long timeLimit = giver.getRNG().nextInt((int) (timeLimitHigh - timeLimitLow)) + timeLimitLow;

		List<IFavorCondition> c = new ArrayList<>();
		for (IFavorCondition ifc : conditions)
			c.add(ifc);
		List<IFavorReward> s = new ArrayList<>();
		for (IFavorReward ifc : success)
			s.add(ifc);
		List<IFavorReward> f = new ArrayList<>();
		for (IFavorReward ifc : failure)
			f.add(ifc);

		return new Favor(player, giver, stories.get(giver.getRNG().nextInt(stories.size())), c, s, f, timeLimit);
	}
	
	public static Favor createFavorDestroyStructure(List<String> stories, EntityPlayer player, EntityAbstractNPC giver, int rewardLow, int rewardHigh, long timeLimitLow, long timeLimitHigh) {
		long timeLimit = giver.getRNG().nextInt((int) (timeLimitHigh - timeLimitLow)) + timeLimitLow;

		BlockPos pos = null;
		Faction fac = null;
		int counter = 1;
		int xDirection = giver.getRNG().nextInt(2) - 1;
		int zDirection = giver.getRNG().nextInt(2) - 1;
		if (xDirection == 0 && zDirection == 0)
			xDirection = 1;
		do {
			pos = WorldTypeRedwall.chunkProvider.getNearestStructurePos(giver.world, "RedwallSmall", giver.getPosition().add(counter * xDirection * 256, 0, counter * zDirection * 256), true);
			fac = MapGenScatteredFeatureRedwall.getStructureFactionAt(pos.getX() / 16, pos.getZ() / 16, giver.world);
			if (fac.getFactionStatus(giver.getFaction()) != Faction.FactionStatus.HOSTILE) {
				pos = null;
				counter++;
			}
		} while (pos == null && counter < 100);
		
		if (pos == null)
			return null;

		IFavorCondition condition = new FavorConditionDestroyStructure(pos.getX() / 16, pos.getZ() / 16, fac);

		IFavorReward[] success = new IFavorReward[3];
		success[0] = new FavorRewardItem(Items.GOLD_NUGGET, rewardLow * 2, rewardHigh * 2);
		success[1] = new FavorRewardSkill(giver.getFaction(), FactionCap.FacStatType.LOYALTY, rewardLow * 2, rewardHigh * 2);
		success[2] = new FavorRewardXP(rewardLow / 2, rewardHigh / 2);

		IFavorReward[] failure = new IFavorReward[1];
		failure[0] = new FavorRewardSkill(giver.getFaction(), FactionCap.FacStatType.LOYALTY, -rewardLow / 2, -rewardHigh / 2);

		List<IFavorCondition> c = new ArrayList<>();
		c.add(condition);
		List<IFavorReward> s = new ArrayList<>();
		for (IFavorReward ifc : success)
			s.add(ifc);
		List<IFavorReward> f = new ArrayList<>();
		for (IFavorReward ifc : failure)
			f.add(ifc);
		
		return new Favor(player, giver, stories.get(giver.getRNG().nextInt(stories.size())), c, s, f, timeLimit);
	}
}
