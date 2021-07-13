package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;
import com.bob.redwall.factions.Faction.FactionStatus;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FavorConditionKillNPC implements IFavorCondition {
	private EntityAbstractNPC npc;
	private Favor favor;
	private boolean complete;

	public FavorConditionKillNPC(EntityAbstractNPC npc) {
		this.npc = npc;
		this.complete = false;
	}

	@Override
	public void killNPC(EntityAbstractNPC npc) {
		if(npc.getEntityId() == this.npc.getEntityId())
			this.complete = true;
		else if (this.favor != null && npc.getFaction().getFactionStatus(this.favor.getGiver().getFaction()) == FactionStatus.ALLIED) this.favor.fail();
	}

	@Override
	public ItemStack offerItem(ItemStack item) {
		return item;
	}

	@Override
	public void destroyStructure(EntityStructureCenter center) {
		if (this.favor != null && center.getFaction().getFactionStatus(this.favor.getGiver().getFaction()) == FactionStatus.ALLIED) this.favor.fail();
	}

	@Override
	public EntityAbstractNPC getGiver() {
		return this.favor != null ? this.favor.getGiver() : null;
	}

	@Override
	public void setFavor(Favor favor) {
		this.favor = favor;
	}

	@Override
	public boolean isComplete() {
		return this.complete;
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setString("Type", "KillNPC");
		compound.setInteger("EntityID", this.npc != null ? this.npc.getEntityId() : -1);
		compound.setBoolean("Complete", this.complete);
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if(!c.getString("Type").equals("KillNPC")) 
			throw new IllegalStateException("Created a KillNPC favor condition without a KillNPC tag!");
		
		this.npc = (EntityAbstractNPC) this.favor.getPlayer().getEntityWorld().getEntityByID(c.getInteger("EntityID"));
		this.complete = c.getBoolean("Complete");
	}

	@Override
	public String getText() {
		return I18n.format("favor.condition.killnpc", this.npc.getName(), this.npc.getFaction().getLocalizedName(), this.npc.getNPCType().name);
	}
}
