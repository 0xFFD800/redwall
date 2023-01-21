package com.bob.redwall.entity.structure_center;

import com.bob.redwall.entity.capabilities.factions.Faction;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityWoodlanderHearth extends EntityStructureCenter {
	public EntityWoodlanderHearth(World worldIn) {
		super(worldIn, new AxisAlignedBB(0, 0, 0, 0, 0, 0));
	}
	
	public EntityWoodlanderHearth(World worldIn, AxisAlignedBB aoe) {
		super(worldIn, aoe);
	}

	@Override
	public Faction getFaction() {
		return Faction.FacList.WOODLANDERS;
	}

	@Override
	public int getXPReward() {
		return 10;
	}

	@Override
	public int getLoyaltyReward() {
		return 20;
	}

	@Override
	public int getFightSkillReward() {
		return 10;
	}
}
