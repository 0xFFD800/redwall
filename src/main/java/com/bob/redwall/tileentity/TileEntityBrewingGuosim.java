package com.bob.redwall.tileentity;

import com.bob.redwall.Ref;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBrewingGuosim extends TileEntityLockable implements ITickable {
	public static final int SIZE = 9; // Size of the inventory: 9 crafting slots and 1 fuel slot
	public static final int BREWING_TIME = 200; // Time required to brew an item, in ticks; 200 is about 10 seconds
	public ItemStack brewStack = ItemStack.EMPTY;
	private int brewTime = -1;
	private boolean brewingFinished = false;
	private EntityPlayer openPlayer;
	private String customName;
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityBrewingGuosim.this.markDirty();
		}
	};

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "tile.brewing_guosim.name";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	public void setName(String name) {
		this.customName = name;
	}

	@Override
	public int getSizeInventory() {
		return this.itemStackHandler.getSlots();
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
			if (!this.itemStackHandler.getStackInSlot(i).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void update() {
		if (this.brewTime > 0) this.brewTime--;

		if (this.brewTime == 0) {
			this.brewTime = -1;
			this.brewingFinished = true;
		}

		if (this.openPlayer != null && this.openPlayer instanceof EntityPlayerMP) ((EntityPlayerMP) this.openPlayer).connection.sendPacket(this.getUpdatePacket());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("CustomName", 8)) this.customName = compound.getString("CustomName");
		if (compound.hasKey("items")) this.itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		if (compound.hasKey("brewTime")) this.brewTime = compound.getInteger("brewTime");
		if (compound.hasKey("brewDone")) this.brewingFinished = compound.getBoolean("brewDone");
		if (compound.hasKey("brewItem")) this.brewStack = new ItemStack(compound.getCompoundTag("brewItem"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", this.itemStackHandler.serializeNBT());
		compound.setInteger("brewTime", this.brewTime);
		compound.setBoolean("brewDone", this.brewingFinished);
		compound.setTag("brewItem", this.brewStack.serializeNBT());

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= 0 && index < this.itemStackHandler.getSlots() ? (ItemStack) this.itemStackHandler.getStackInSlot(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return this.itemStackHandler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.itemStackHandler.getStackInSlot(index);
		this.itemStackHandler.setStackInSlot(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.itemStackHandler.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		this.openPlayer = player;
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		this.openPlayer = null;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public String getGuiID() {
		return Ref.MODID + ":brewing_guosim";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
		if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemStackHandler);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
			this.itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
		}
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
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	public int getBrewingTime() {
		return this.brewTime;
	}

	public void setBrewingTime(int i) {
		this.brewTime = i;
	}

	public boolean getBrewingFinished() {
		return this.brewingFinished;
	}

	public void setBrewingFinished(boolean b) {
		this.brewingFinished = b;
	}
}