package com.bob.redwall.gui.trading;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerTrading extends Container {
	private EntityAbstractNPC npc;
	private IInventory npcInventory;

	public ContainerTrading(EntityPlayer player, EntityAbstractNPC trader) {
		this.npc = trader;
		
		this.npcInventory = new InventoryTrading("trading", false, 12, this.npc);
		
		for(int i = 0; i < 6; i++) {
			Slot s = new SlotBuy(this.npcInventory, i, 62 + i*18, 19);
			s.putStack(this.npc.getBuyingItems()[i]);
			this.addSlotToContainer(s);
			Slot s1 = new SlotSell(this.npcInventory, i + 6, 62 + i*18, 54);
			s1.putStack(ItemStack.EMPTY);
			this.addSlotToContainer(s1);
		}

		for (int k = 0; k < 3; ++k)
			for (int i1 = 0; i1 < 9; ++i1)
				this.addSlotToContainer(new Slot(player.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));

		for (int l = 0; l < 9; ++l) {
			this.addSlotToContainer(new Slot(player.inventory, l, 8 + l * 18, 142));
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		this.npc.setIsTrading(false);
		
		for (int i = 1; i < 12; i+=2) {
			if (!playerIn.addItemStackToInventory(this.getSlot(i).getStack()))
				playerIn.dropItem(this.getSlot(i).getStack(), false);
		}

		InventoryPlayer inventoryplayer = playerIn.inventory;

		if (!inventoryplayer.getItemStack().isEmpty()) {
			if (!playerIn.addItemStackToInventory(inventoryplayer.getItemStack()))
				playerIn.dropItem(inventoryplayer.getItemStack(), false);
			inventoryplayer.setItemStack(ItemStack.EMPTY);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}

	public EntityAbstractNPC getNPC() {
		return this.npc;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistance(this.npc) < 4.5F;
	}

	class SlotBuy extends Slot { // Buying from the npc
		public SlotBuy(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
			for (int i = 1; i < 12; i+=2)
				getSlot(i).putStack(ItemStack.EMPTY);
	        this.onSlotChanged();
			return stack;
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn) {
			float value = 0;
			for (int i = 1; i < 12; i+=2)
				value += npc.getStackValue(getSlot(i).getStack());
			return value > npc.getStackValue(this.getStack()) && playerIn.inventory.getItemStack().isEmpty();
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return false;
		}

		@Override
		public ItemStack decrStackSize(int amount) {
			return this.getStack();
		}

		@Override
		public ItemStack getStack() {
			return ContainerTrading.this.npc.getBuyingItems()[this.getSlotIndex()];
		}
		
		@Override
	    @SideOnly(Side.CLIENT)
	    public boolean isEnabled() {
			float value = 0;
			for (int i = 1; i < 12; i+=2)
				value += npc.getStackValue(getSlot(i).getStack());
	        return value > npc.getStackValue(this.getStack());
	    }
	}

	class SlotSell extends Slot { // Selling to the npc
		public SlotSell(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
	}
}
