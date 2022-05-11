package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.factions.Faction.FactionStatus;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FavorConditionDestroyStructure implements IFavorCondition {
	private int chunkX;
	private int chunkZ;
	private Faction faction;
	private Favor favor;
	private boolean complete;

	public FavorConditionDestroyStructure(int cx, int cz, Faction fac) {
		this.chunkX = cx;
		this.chunkZ = cz;
		this.faction = fac;
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
		if (Math.abs(center.getPosition().getX() - (chunkX * 16)) < 32 && (Math.abs(center.getPosition().getZ() - (chunkZ * 16)) < 32))
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
		compound.setInteger("ChunkX", this.chunkX);
		compound.setInteger("ChunkZ", this.chunkZ);
		compound.setString("Faction", this.faction.getID());
		compound.setBoolean("Complete", this.complete);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound c) {
		if (!c.getString("Type").equals("DestroyStructure"))
			throw new IllegalStateException("Created a DestroyStructure favor condition without a DestroyStructure tag!");

		this.chunkX = c.getInteger("ChunkX");
		this.chunkZ = c.getInteger("ChunkZ");
		this.faction = Faction.getFactionByID(c.getString("Faction"));
		this.complete = c.getBoolean("Complete");
	}

	@Override
	public String getText() {
		return I18n.format("favor.condition.destroystructure", this.faction.getLocalizedName(), this.chunkX * 16, this.chunkZ * 16);
	}
}
