package com.bob.redwall.entity.npc.good.redwall;

import java.util.List;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.SpeechHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMoleRedwall extends EntityAbstractNPC {
	public EntityMoleRedwall(World worldIn) {
		super(worldIn);
        this.setSize(0.6F, 1.45F);
	}
	
	public EntityMoleRedwall(World worldIn, boolean male) {
		super(worldIn, male);
        this.setSize(0.6F, 1.45F);
	}
    
	@Override
    public List<String> getSpeechbank(EnumOpinion opinion) {
    	return opinion == EnumOpinion.FRIENDLY ? SpeechHandler.REDWALL_MOLE_FRIENDLY : opinion == EnumOpinion.UNFRIENDLY ? SpeechHandler.REDWALL_MOLE_UNFRIENDLY : SpeechHandler.REDWALL_MOLE_HOSTILE;
    }
    
	@Override
    public List<String> getNamesBankMale() {
    	return SpeechHandler.NAMES_REDWALL_MOLE_M;
    }
    
	@Override
    public List<String> getNamesBankFemale() {
    	return SpeechHandler.NAMES_REDWALL_MOLE_F;
    }

	@Override
	public Faction getFaction() {
		return Faction.FacList.REDWALL;
	}

	@Override
	public String getSkinPath() {
		return ":textures/entity/mole/redwall/mole_redwall_";
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