package com.bob.redwall.entity.ai;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAINPCLookAtTradingPlayer extends EntityAIWatchClosest {
	private final EntityAbstractNPC npc;

    public EntityAINPCLookAtTradingPlayer(EntityAbstractNPC npc) {
        super(npc, EntityPlayer.class, 8.0F);
        this.npc = npc;
    }

    @Override
    public boolean shouldExecute() {
        if (this.npc.isTrading()) {
            this.closestEntity = this.npc.getCustomer();
            return true;
        } else {
            return false;
        }
    }
}
