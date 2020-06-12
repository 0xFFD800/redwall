package com.bob.redwall.entity.npc;

import java.util.List;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.SpeechHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMoleWoodlander extends EntityAbstractNPC {
	public EntityMoleWoodlander(World worldIn) {
		super(worldIn);
	}
	
	public EntityMoleWoodlander(World worldIn, boolean male) {
		super(worldIn, male);
	}
    
	@Override
    public List<String> getSpeechbank(EnumOpinion opinion) {
    	return opinion == EnumOpinion.FRIENDLY ? SpeechHandler.WOODLANDER_MOLE_FRIENDLY : opinion == EnumOpinion.UNFRIENDLY ? SpeechHandler.WOODLANDER_MOLE_UNFRIENDLY : SpeechHandler.WOODLANDER_MOLE_HOSTILE;
    }
    
	@Override
    public List<String> getNamesBankMale() {
    	return SpeechHandler.NAMES_WOODLANDER_MOLE_M;
    }
    
	@Override
    public List<String> getNamesBankFemale() {
    	return SpeechHandler.NAMES_WOODLANDER_MOLE_F;
    }

	@Override
	public Faction getFaction() {
		return Faction.FacList.REDWALL;
	}

	@Override
	public String getSkinPath() {
		return ":textures/entity/mole/woodlander/mole_woodlander_";
	}

	@Override
	public int numVariants() {
		return 4;
	}

	@Override
	public EnumOpinion getOpinionOfPlayer(EntityPlayer player) {
		int level = RedwallUtils.getFacStatLevel(player, this.getFaction(), FacStatType.LOYALTY);
		return level > -1 ? EnumOpinion.FRIENDLY : level > -2 ? EnumOpinion.UNFRIENDLY : EnumOpinion.HOSTILE;
	}
	
	@Override
	public EnumNPCType getNPCType() {
		return EnumNPCType.MOLE;
	}

	@Override
	public boolean willFightEntity(EntityLivingBase entity) {
		return false;
	}
	
	@Override
	public boolean willFightEntityRevenge(EntityLivingBase entity) {
		return this.isSuitableTarget(entity) && this.getIsMale();
	}
}