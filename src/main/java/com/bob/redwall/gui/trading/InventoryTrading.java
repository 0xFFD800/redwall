package com.bob.redwall.gui.trading;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class InventoryTrading extends InventoryBasic {
	private final EntityAbstractNPC npc;
	private ItemStack[] stacks = new ItemStack[6];
	
	public InventoryTrading(String title, boolean customName, int slotCount, EntityAbstractNPC npc) {
		super(title, customName, slotCount);
		this.npc = npc;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        return index < 6 ? this.npc.getBuyingItems()[index] : stacks[index - 6];
    }
	
	@Override
    public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 6)
			npc.getBuyingItems()[index] = stack;
		else
			stacks[index - 6] = stack;
	}
}
