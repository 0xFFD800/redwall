package com.bob.redwall.entity.npc.good.woodlander;

import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.init.SpeechHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySquirrelWoodlander extends EntityAbstractNPC {
	public EntitySquirrelWoodlander(World worldIn) {
		super(worldIn);
	}
	
	public EntitySquirrelWoodlander(World worldIn, boolean male) {
		super(worldIn, male);
	}
    
	@Override
    public List<String> getSpeechbank(EnumOpinion opinion) {
    	return opinion == EnumOpinion.FRIENDLY ? SpeechHandler.WOODLANDER_SQUIRREL_FRIENDLY : opinion == EnumOpinion.UNFRIENDLY ? SpeechHandler.WOODLANDER_SQUIRREL_UNFRIENDLY : SpeechHandler.WOODLANDER_SQUIRREL_HOSTILE;
    }
    
	@Override
    public List<String> getNamesBankMale() {
    	return SpeechHandler.NAMES_WOODLANDER_SQUIRREL_M;
    }
    
	@Override
    public List<String> getNamesBankFemale() {
    	return SpeechHandler.NAMES_WOODLANDER_SQUIRREL_F;
    }

	@Override
	public Faction getFaction() {
		return Faction.FacList.WOODLANDERS;
	}

	@Override
	public String getSkinPath() {
		return ":textures/entity/squirrel/woodlander/squirrel_woodlander_";
	}

	@Override
	public int numVariants() {
		return 4;
	}

	@Override
	public EnumOpinion getOpinionOfPlayer(EntityPlayer player) {
		int level = RedwallUtils.getFacStatLevel(player, this.getFaction(), FacStatType.LOYALTY);
		return level > 0 ? EnumOpinion.FRIENDLY : level > -1 ? EnumOpinion.UNFRIENDLY : EnumOpinion.HOSTILE;
	}
	
	@Override
	public EnumNPCType getNPCType() {
		return EnumNPCType.SQUIRREL;
	}

	@Override
	public boolean willFightEntity(EntityLivingBase entity) {
		return this.isSuitableTarget(entity);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		ChunkPos cp = new ChunkPos(this.getPosition());
        return super.getCanSpawnHere() && RedwallUtils.isInMossflower(this.world.getBiome(this.getPosition()), cp.x, cp.z);
    }
    
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    	this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(WeightedRandom.getRandomItem(this.getRNG(), EntityAbstractNPC.EQUIPMENT_LIST_WOODLANDERS).getItem()));
    	return super.onInitialSpawn(difficulty, livingdata);
    }

	@Override
	public void createFavor() {
		this.setFavor(Favor.createFavorCollectItems(this.getRNG().nextBoolean() ? EntityAbstractNPC.FAVOR_COLLECT_METALS : EntityAbstractNPC.FAVOR_COLLECT_PRODUCE, SpeechHandler.COLLECT_METALS_GOOD, null, this, 1, 3, 2, 5, 6000, 18000));
	}

	@Override
	protected List<EquipmentChance> getPossibleTrades() {
		return EntityAbstractNPC.TRADE_LIST_WOODLANDERS;
	}
}