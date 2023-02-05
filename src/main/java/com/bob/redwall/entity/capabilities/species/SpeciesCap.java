package com.bob.redwall.entity.capabilities.species;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SpeciesCap implements ISpeciesCap {
	private EntityPlayer player;
	private Species species;
	private boolean isInit;
	
	@Override
	public void init(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void set(Species s) {
		this.species = s;
	}

	@Override
	public void updateTick() {
		
	}

	@Nonnull
	@Override
	public Species get() {
		return this.species == null ? Species.SpeciesList.MOUSE : this.species;
	}
	
	@Override
	public void update() {
		if(this.player != null) {
			
		}
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("ID", this.get().getID());
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.species = Species.getByID(tag.getString("ID"));
	}

	@Override
	public void setInit() {
		this.isInit = true;
	}

	@Override
	public boolean isInitialized() {
		return this.isInit;
	}
}
