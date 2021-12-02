package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;
import com.bob.redwall.factions.Faction.FactionStatus;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FavorConditionDestroyStructure implements IFavorCondition {
	private EntityStructureCenter center;
	private Favor favor;
	private boolean complete;

	public FavorConditionDestroyStructure(EntityStructureCenter center) {
		this.center = center;
		this.complete = false;
	}

	@Override
	public void killNPC(EntityAbstractNPC npc) {
		if (this.favor != null && npc.getFaction().getFactionStatus(this.favor.getGiver().getFaction()) == FactionStatus.ALLIED)
			this.favor.fail();
	}

	@Override
	public ItemStack offerItem(ItemStack item) {
		return item;
	}

	@Override
	public void destroyStructure(EntityStructureCenter center) {
		if (center.equals(this.center))
			this.complete = true;
		this.favor.update();
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

		compound.setString("Type", "DestroyStructure");
		compound.setInteger("StructureID", this.center != null ? this.center.getEntityId() : -1);
		compound.setBoolean("Complete", this.complete);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if (!c.getString("Type").equals("DestroyStructure"))
			throw new IllegalStateException("Created a DestroyStructure favor condition without a DestroyStructure tag!");

		this.center = (EntityStructureCenter) this.favor.getPlayer().getEntityWorld().getEntityByID(c.getInteger("StructureID"));
		this.complete = c.getBoolean("Complete");
	}

	@Override
	public String getText() {
		return I18n.format("favor.condition.destroystructure", this.center.getFaction(), this.center.getPosition().getX(), this.center.getPosition().getZ());
	}
}
