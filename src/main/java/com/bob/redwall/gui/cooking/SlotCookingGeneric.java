package com.bob.redwall.gui.cooking;

import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.CraftingHandler;
import com.bob.redwall.tileentity.TileEntityCookingGeneric;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SlotCookingGeneric extends Slot {
	private final InventoryCrafting craftMatrix;
	private final EntityPlayer player;
	private int amountCrafted;
	private TileEntityCookingGeneric te;
	public CraftingHandler.LeveledRecipe recipe;

	public SlotCookingGeneric(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition, TileEntityCookingGeneric te) {
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
			this.te.setCookingFinished(false);
			this.te.cookStack = ItemStack.EMPTY;
			stack.onCrafting(this.player.world, this.player, this.amountCrafted);
			net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
			IFactionCap cap = this.player.getCapability(FactionCapProvider.FACTION_CAP, null);
			cap.set(Faction.FacList.GENERIC, FactionCap.FacStatType.LOYALTY, cap.get(Faction.FacList.GENERIC, FactionCap.FacStatType.LOYALTY) + 5.0F, true);
			cap.set(Faction.FacList.GENERIC, FactionCap.FacStatType.SMITH, cap.get(Faction.FacList.GENERIC, FactionCap.FacStatType.COOK) + 5.0F, true);
			// RedwallUtils.applyFoodModifiers(this.player, stack,
			// cap.get(Faction.FacList.GENERIC, FactionCap.FacStatType.COOK));
		}

		this.amountCrafted = 0;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		if (this.te.getCookingFinished()) return true;
		if (this.te.getFuel() > 0 && this.te.getCookingTime() == -1) {
			if (this.recipe != null) {
				if (this.shouldBurn()) {
					this.te.setCookingTime(TileEntityCookingGeneric.COOKING_TIME);
				} else {
					this.te.setCookingFinished(true);
				}
			}
			this.te.cookStack = this.getStack();
			this.te.useFuel();
			net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
			NonNullList<ItemStack> nonnulllist = CraftingHandler.Cooking.getInstance().getRemainingItems(this.craftMatrix, player.world);
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

		if (this.te.getCookingFinished()) return true;
		else return false;
	}

	public boolean shouldBurn() {
		return CraftingHandler.Cooking.getInstance().getCookList().get(CraftingHandler.Cooking.getInstance().getRecipeList().indexOf(this.recipe));
	}

	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		return stack;
	}
}