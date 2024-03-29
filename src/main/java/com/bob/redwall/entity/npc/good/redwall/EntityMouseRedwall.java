package com.bob.redwall.entity.npc.good.redwall;

import java.util.List;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.entity.capabilities.factions.FactionCap.FacStatType;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.init.SpeechHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMouseRedwall extends EntityAbstractNPC {
	public EntityMouseRedwall(World worldIn) {
		super(worldIn);
	}
	
	public EntityMouseRedwall(World worldIn, boolean male) {
		super(worldIn, male);
	}
    
	@Override
    public List<String> getSpeechbank(EnumOpinion opinion) {
    	return opinion == EnumOpinion.FRIENDLY ? SpeechHandler.REDWALL_MOUSE_FRIENDLY : opinion == EnumOpinion.UNFRIENDLY ? SpeechHandler.REDWALL_MOUSE_UNFRIENDLY : SpeechHandler.REDWALL_MOUSE_HOSTILE;
    }
    
	@Override
    public List<String> getNamesBankMale() {
    	return SpeechHandler.NAMES_REDWALL_MOUSE_M;
    }
    
	@Override
    public List<String> getNamesBankFemale() {
    	return SpeechHandler.NAMES_REDWALL_MOUSE_F;
    }

	@Override
	public Faction getFaction() {
		return Faction.FacList.REDWALL;
	}

	@Override
	public String getSkinPath() {
		return ":textures/entity/mouse/redwall/mouse_redwall_";
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
		return EnumNPCType.MOUSE;
	}

	@Override
	public boolean willFightEntity(EntityLivingBase entity) {
		return false;
	}

	@Override
	public void createFavor() {
		this.setFavor(Favor.createFavorCollectItems(this.getRNG().nextBoolean() ? EntityAbstractNPC.FAVOR_COLLECT_METALS : EntityAbstractNPC.FAVOR_COLLECT_PRODUCE, SpeechHandler.COLLECT_METALS_GOOD, null, this, 1, 3, 2, 5, 6000, 18000));
	}

	@Override
	protected List<EquipmentChance> getPossibleTrades() {
		return EntityAbstractNPC.TRADE_LIST_REDWALL;
	}
}