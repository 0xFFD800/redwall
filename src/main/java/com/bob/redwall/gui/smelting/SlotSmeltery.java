package com.bob.redwall.gui.smelting;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.init.CraftingHandler;
import com.bob.redwall.tileentity.TileEntitySmeltery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SlotSmeltery extends Slot {
	private final InventoryCrafting craftMatrix;
	private final EntityPlayer player;
	private int amountCrafted;
	private TileEntitySmeltery te;

	public SlotSmeltery(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition, TileEntitySmeltery te) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
		this.player = player;
		this.craftMatrix = craftingInventory;
		this.te = te;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.amountCrafted += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		this.amountCrafted += amount;
		this.onCrafting(stack);
	}

	@Override
	protected void onSwapCraft(int amount) {
		this.amountCrafted += amount;
	}

	@Override
	protected void onCrafting(ItemStack stack) {
		if (this.amountCrafted > 0) {
			this.te.setSmeltingFinished(false);
			this.te.smeltStack = ItemStack.EMPTY;
			this.te.markDirty();
			stack.onCrafting(this.player.world, this.player, this.amountCrafted);
			net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
			IFactionCap cap = this.player.getCapability(FactionCapProvider.FACTION_CAP, null);
			RedwallUtils.applyEquipmentModifiers(this.player, stack, cap.get(Faction.FacList.GENERIC, FactionCap.FacStatType.SMITH));
		}

		this.amountCrafted = 0;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		if (this.te.getSmeltingFinished()) return true;
		if (this.te.getFuel() > 0 && this.te.getSmeltingTime() == -1) {
			this.te.setSmeltingTime(TileEntitySmeltery.SMELTING_TIME);
			this.te.smeltStack = this.getStack();
			this.te.useFuel();
			this.te.markDirty();
			net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
			NonNullList<ItemStack> nonnulllist = CraftingHandler.Smeltery.getInstance().getRemainingItems(this.craftMatrix, player.world);
			net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

			for (int i = 0; i < nonnulllist.size(); i++) {
				ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
				ItemStack itemstack1 = (ItemStack) nonnulllist.get(i);

				if (!itemstack.isEmpty()) {
					this.craftMatrix.decrStackSize(i, 1);
					itemstack = this.craftMatrix.getStackInSlot(i);
				}

				if (!itemstack1.isEmpty()) {
					if (itemstack.isEmpty()) {
						this.craftMatrix.setInventorySlotContents(i, itemstack1);
					} else if (ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
						itemstack1.grow(itemstack.getCount());
						this.craftMatrix.setInventorySlotContents(i, itemstack1);
					} else if (!this.player.inventory.addItemStackToInventory(itemstack1)) {
						this.player.dropItem(itemstack1, false);
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		return stack;
	}
}