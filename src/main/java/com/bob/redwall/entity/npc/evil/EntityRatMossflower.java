package com.bob.redwall.entity.npc.evil;

import java.util.List;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.SpeechHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityRatMossflower extends EntityAbstractNPC {
	public EntityRatMossflower(World worldIn) {
		super(worldIn);
	}
	
	public EntityRatMossflower(World worldIn, boolean male) {
		super(worldIn, male);
	}
    
	@Override
    public List<String> getSpeechbank(EnumOpinion opinion) {
    	return opinion == EnumOpinion.FRIENDLY ? SpeechHandler.MOSSFLOWER_RAT_FRIENDLY : opinion == EnumOpinion.UNFRIENDLY ? SpeechHandler.MOSSFLOWER_RAT_UNFRIENDLY : SpeechHandler.MOSSFLOWER_RAT_HOSTILE;
    }
    
	@Override
    public List<String> getNamesBankMale() {
    	return SpeechHandler.NAMES_MOSSFLOWER_RAT_M;
    }
    
	@Override
    public List<String> getNamesBankFemale() {
    	return SpeechHandler.NAMES_MOSSFLOWER_RAT_F;
    }

	@Override
	public Faction getFaction() {
		return Faction.FacList.VERMIN_MOSSFLOWER;
	}

	@Override
	public String getSkinPath() {
		return ":textures/entity/rat/mossflower_vermin/rat_mossflower_";
	}

	@Override
	public int numVariants() {
		return 1;
	}

	@Override
	public EnumOpinion getOpinionOfPlayer(EntityPlayer player) {
		int level = RedwallUtils.getFacStatLevel(player, this.getFaction(), FacStatType.LOYALTY);
		return level > 1 ? EnumOpinion.FRIENDLY : level > -1 ? EnumOpinion.UNFRIENDLY : EnumOpinion.HOSTILE;
	}
	
	@Override
	public EnumNPCType getNPCType() {
		return EnumNPCType.RAT;
	}

	@Override
	public boolean willFightEntity(EntityLivingBase entity) {
		return this.isSuitableTarget(entity);
	}
}