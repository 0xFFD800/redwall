package com.bob.redwall.entity.structure_center;

import com.bob.redwall.entity.capabilities.factions.Faction;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityMossflowerOtterHearth extends EntityStructureCenter {
	public EntityMossflowerOtterHearth(World worldIn) {
		super(worldIn, new AxisAlignedBB(0, 0, 0, 0, 0, 0));
	}
	
	public EntityMossflowerOtterHearth(World worldIn, AxisAlignedBB aoe) {
		super(worldIn, aoe);
	}

	@Override
	public Faction getFaction() {
		return Faction.FacList.OTTERS_MOSSFLOWER;
	}

	@Override
	public int getXPReward() {
		return 50;
	}

	@Override
	public int getLoyaltyReward() {
		return 80;
	}

	@Override
	public int getFightSkillReward() {
		return 30;
	}
}
