package com.bob.redwall.gui.smelting;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSmelteryFuel extends Slot {
	public SlotSmelteryFuel(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
		super(iInventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return isValidSmeltingFuel(stack);
	}

	public static boolean isValidSmeltingFuel(ItemStack itemStackIn) {
		return itemStackIn.getItem() == Items.COAL;
	}
}
