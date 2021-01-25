package com.bob.redwall.tileentity;

import java.util.List;

import com.bob.redwall.crafting.cooking.FoodModifier;
import com.bob.redwall.crafting.cooking.FoodModifierUtils;
import com.bob.redwall.items.brewing.Drink;
import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

public class TileEntityDrinkVessel extends TileEntity {
	private Drink drink = null;
	private List<FoodModifier> modifiers = Lists.newArrayList();
	private List<Integer> levels = Lists.newArrayList();

	public Drink getDrink() {
		return drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
		this.markDirty();
		if (this.world != null) {
			IBlockState state = world.getBlockState(this.getPos());
			world.notifyBlockUpdate(this.getPos(), state, state, 3);
		}
	}
	
	public List<FoodModifier> getModifiers() {
		return this.modifiers;
	}
	
	public List<Integer> getLevels() {
		return this.levels;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(this.getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("drink")) {
			this.drink = Drink.BY_ID.get(new ResourceLocation(compound.getString("drink")));
		} else {
			this.drink = null;
		}
		
		if(compound.hasKey(FoodModifierUtils.MODIFIER_LIST_KEY)) {
			for(NBTBase modnbt : compound.getTagList(FoodModifierUtils.MODIFIER_LIST_KEY, Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound mnbt = (NBTTagCompound)modnbt;
				this.modifiers.add(FoodModifier.getModifierByID(mnbt.getInteger("id")));
				this.levels.add(mnbt.getInteger("lvl"));
			}
		} else {
			this.modifiers.clear();
			this.levels.clear();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.drink != null) {
			compound.setString("drink", this.drink.getID().toString());
		}
		
		if(!this.modifiers.isEmpty()) {
			NBTTagList mlist = new NBTTagList();
			for(FoodModifier mod : this.modifiers) {
				NBTTagCompound mnbt = new NBTTagCompound();
				mnbt.setInteger("id", FoodModifier.getModifierID(mod));
				mnbt.setInteger("lvl", this.levels.get(this.modifiers.indexOf(mod)));
				mlist.appendTag(mnbt);
			}
			compound.setTag(FoodModifierUtils.MODIFIER_LIST_KEY, mlist);
		}
		return compound;
	}
}