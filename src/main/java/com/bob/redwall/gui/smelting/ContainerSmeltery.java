package com.bob.redwall.gui.smelting;

import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.CraftingHandler;
import com.bob.redwall.tileentity.TileEntitySmeltery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSmeltery extends Container {
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private final World worldObj;
	/** Position of the workbench */
	private final BlockPos pos;
	private final InventoryPlayer inventoryPlayer;
	private final TileEntitySmeltery te;

	public ContainerSmeltery(InventoryPlayer playerInventory, World world, BlockPos pos, TileEntitySmeltery te) {
		this.worldObj = world;
		this.pos = pos;
		this.inventoryPlayer = playerInventory;
		this.te = te;
		this.addSlotToContainer(new SlotSmelteryFuel(te, TileEntitySmeltery.FUEL_INDEX, 8, 35));
		this.addSlotToContainer(new SlotSmeltery(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35, te));
		this.craftResult.setInventorySlotContents(0, te.smeltStack);

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.craftMatrix.setInventorySlotContents(i, te.getStackInSlot(i));
		}

		for (int k = 0; k < 3; ++k) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}

		for (int l = 0; l < 9; ++l) {
			this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public Slot getSlot(int slotId) {
		return slotId < this.inventorySlots.size() ? (Slot) this.inventorySlots.get(slotId) : new Slot(this.inventoryPlayer, slotId, slotId, slotId);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if (this.te.getSmeltingTime() == -1 && !this.te.getSmeltingFinished()) this.craftResult.setInventorySlotContents(0, CraftingHandler.Smeltery.getInstance().findMatchingRecipe(this.inventoryPlayer.player, this.craftMatrix, this.worldObj));
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		InventoryPlayer inventoryplayer = player.inventory;

		if (!inventoryplayer.getItemStack().isEmpty()) {
			if (!player.addItemStackToInventory(inventoryplayer.getItemStack())) player.dropItem(inventoryplayer.getItemStack(), false);
			inventoryplayer.setItemStack(ItemStack.EMPTY);
		}

		if (!this.worldObj.isRemote) {
			for (int i = 0; i < 9; ++i) {
				ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

				if (!itemstack.isEmpty()) {
					te.setInventorySlotContents(i, itemstack);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlockState(this.pos).getBlock() != BlockHandler.smeltery ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0) {
				itemstack1.getItem().onCreated(itemstack1, this.worldObj, player);

				if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (SlotSmelteryFuel.isValidSmeltingFuel(itemstack)) {
				if (!this.mergeItemStack(itemstack1, 9, 10, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 10 && index < 37) {
				if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 37 && index < 46) {
				if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			ItemStack itemstack2 = slot.onTake(player, itemstack1);

			if (index == 0) {
				player.dropItem(itemstack2, false);
			}
		}

		return itemstack;
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
	}

	public void openInventory(EntityPlayer player) {
		this.te.openInventory(player);
	}
}