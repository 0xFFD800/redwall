package com.bob.redwall.entity.npc.favors;

import java.util.ArrayList;
import java.util.List;

import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.init.ItemHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

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
	
	private static final Item[] metals = new Item[] { Items.IRON_INGOT, Items.GOLD_INGOT, ItemHandler.bronze_ingot, ItemHandler.copper_ingot, ItemHandler.tin_ingot };
	
	public static Favor createFavorCollectMetals(EntityPlayer player, EntityAbstractNPC giver, int typesL, int typesH, int numL, int numH, long timeLimitLow, long timeLimitHigh) {
		int types = player.getRNG().nextInt(typesH - typesL) + typesL;
		IFavorCondition[] conditions = new IFavorCondition[types];
		List<Item> usedItems = new ArrayList<>();
		
		for(int i = 0; i < types; i++) {
			Item item = metals[player.getRNG().nextInt(metals.length)];
			while(usedItems.contains(item)) 
				item = metals[player.getRNG().nextInt(metals.length)];
			usedItems.add(item);
			
			int num = player.getRNG().nextInt(numH - numL) + numL;
			conditions[i] = new FavorConditionGiveItems(item, num);
		}
		
		IFavorReward[] success = new IFavorReward[3];
		success[0] = new FavorRewardItem(Items.GOLD_NUGGET, numL / 2, numH / 2);
		success[1] = new FavorRewardSkill(giver.getFaction(), FactionCap.FacStatType.LOYALTY, numL, numH);
		success[2] = new FavorRewardXP(numL / 5, numH / 5);

		IFavorReward[] failure = new IFavorReward[1];
		failure[0] = new FavorRewardSkill(giver.getFaction(), FactionCap.FacStatType.LOYALTY, -numL / 5, -numH / 5);
		
		long timeLimit = player.getRNG().nextInt((int) (timeLimitHigh - timeLimitLow)) + timeLimitLow;
		
		List<IFavorCondition> c = new ArrayList<>();
		for(IFavorCondition ifc : conditions)
			c.add(ifc);
		List<IFavorReward> s = new ArrayList<>();
		for(IFavorReward ifc : success)
			s.add(ifc);
		List<IFavorReward> f = new ArrayList<>();
		for(IFavorReward ifc : failure)
			f.add(ifc);
		
		return new Favor(player, giver, c, s, f, timeLimit);
	}
}
