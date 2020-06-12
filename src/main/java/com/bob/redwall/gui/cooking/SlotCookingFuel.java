package com.bob.redwall.gui.cooking;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotCookingFuel extends Slot {
	public SlotCookingFuel(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
		super(iInventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack);
	}
}
