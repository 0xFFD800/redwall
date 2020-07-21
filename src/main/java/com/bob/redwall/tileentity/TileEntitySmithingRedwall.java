package com.bob.redwall.tileentity;

import com.bob.redwall.Ref;
import com.bob.redwall.gui.smithing.SlotSmithingFuel;

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

public class TileEntitySmithingRedwall extends TileEntityLockable implements ITickable {
	public static final int SIZE = 10; // Size of the inventory: 9 crafting slots and 1 fuel slot
	public static final int FUEL_INDEX = 9; // Index of the fuel slot
	public static final int SMITHING_TIME = 180; // Time required to smith an item, in ticks; 200 is about 10 seconds
	public ItemStack smithStack = ItemStack.EMPTY;
	private int fuel = 0;
	private int smithTime = -1;
	private boolean smithingFinished = false;
	private boolean containerOpen = false;
	private EntityPlayer openPlayer;
	private String customName;
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntitySmithingRedwall.this.markDirty();
		}
	};

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "tile.smithing_redwall.name";
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
		if (this.containerOpen) {
			if (this.smithTime > 0) this.smithTime--;

			if (this.smithTime == 0) {
				this.smithTime = -1;
				this.smithingFinished = true;
			}

			if (this.openPlayer != null && this.openPlayer instanceof EntityPlayerMP) ((EntityPlayerMP) this.openPlayer).connection.sendPacket(this.getUpdatePacket());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("CustomName", 8)) this.customName = compound.getString("CustomName");
		if (compound.hasKey("items")) this.itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		if (compound.hasKey("fuel")) this.fuel = compound.getInteger("fuel");
		if (compound.hasKey("smithTime")) this.smithTime = compound.getInteger("smithTime");
		if (compound.hasKey("smithDone")) this.smithingFinished = compound.getBoolean("smithDone");
		if (compound.hasKey("smithItem")) this.smithStack = new ItemStack(compound.getCompoundTag("smithItem"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", this.itemStackHandler.serializeNBT());
		compound.setInteger("fuel", this.fuel);
		compound.setInteger("smithTime", this.smithTime);
		compound.setBoolean("smithDone", this.smithingFinished);
		compound.setTag("smithItem", this.smithStack.serializeNBT());

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
		if (index == TileEntitySmithingRedwall.FUEL_INDEX && !stack.isEmpty() && this.fuel == 0) {
			this.fuel = 8;
			stack.setCount(stack.getCount() - 1);
		}

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
		this.containerOpen = true;
		this.openPlayer = player;
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		this.containerOpen = false;
		this.openPlayer = null;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == TileEntitySmithingRedwall.FUEL_INDEX ? SlotSmithingFuel.isValidSmithingFuel(stack) : true;
	}

	@Override
	public String getGuiID() {
		return Ref.MODID + ":smithing_generic";
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

	public int getFuel() {
		return this.fuel;
	}

	public void useFuel() {
		this.fuel--;
		if (!this.itemStackHandler.getStackInSlot(TileEntitySmithingRedwall.FUEL_INDEX).isEmpty() && this.fuel == 0) {
			this.fuel = 8;
			this.decrStackSize(TileEntitySmithingRedwall.FUEL_INDEX, 1);
		}
	}

	public int getSmithingTime() {
		return this.smithTime;
	}

	public void setSmithingTime(int i) {
		this.smithTime = i;
	}

	public boolean getSmithingFinished() {
		return this.smithingFinished;
	}

	public void setSmithingFinished(boolean b) {
		this.smithingFinished = b;
	}
}