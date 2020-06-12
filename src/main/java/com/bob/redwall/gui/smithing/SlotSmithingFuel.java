package com.bob.redwall.gui.smithing;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSmithingFuel extends Slot {
	public SlotSmithingFuel(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
		super(iInventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return isValidSmithingFuel(stack);
	}

	public static boolean isValidSmithingFuel(ItemStack itemStackIn) {
		return itemStackIn.getItem() == Items.COAL;
	}
}
