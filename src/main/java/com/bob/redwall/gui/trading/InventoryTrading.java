package com.bob.redwall.gui.trading;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class InventoryTrading extends InventoryBasic {
	private final EntityAbstractNPC npc;
	
	public InventoryTrading(String title, boolean customName, int slotCount, EntityAbstractNPC npc) {
		super(title, customName, slotCount);
		this.npc = npc;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        return index < 6 ? this.npc.getBuyingItems()[index] : super.getStackInSlot(index);
    }
	
	@Override
    public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 6)
			npc.getBuyingItems()[index] = stack;
		else
			super.setInventorySlotContents(index, stack);
	}
}
