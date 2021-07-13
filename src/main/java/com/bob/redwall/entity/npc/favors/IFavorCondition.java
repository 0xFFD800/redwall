package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.structure_center.EntityStructureCenter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A class to represent a "favor" done for an NPC.
 * 
 * @author Luke
 */
public interface IFavorCondition {
	/**
	 * Triggered when a player with this current favor kills an NPC.
	 * 
	 * @param npc
	 *            The NPC that was killed.
	 */
	public void killNPC(EntityAbstractNPC npc);

	/**
	 * Triggered when a player with this current favor offers an item to this NPC.
	 * 
	 * @param item
	 *            The offered item stack.
	 * @return The item in the player's hand after the item is examined.
	 */
	public ItemStack offerItem(ItemStack item);

	/**
	 * Triggered when a player with this current favor destroys a structure.
	 * 
	 * @param center
	 *            The entity representing the center of the destroyed structure.
	 */
	public void destroyStructure(EntityStructureCenter center);

	/**
	 * Gets the NPC that gave out this favor.
	 * 
	 * @return The NPC that asked for this favor.
	 */
	public EntityAbstractNPC getGiver();

	/**
	 * Sets the favor that this condition belongs to.
	 * 
	 * @param favor
	 *            The favor this condition belongs to.
	 */
	public void setFavor(Favor favor);

	/**
	 * Has this condition been fulfilled?
	 * 
	 * @return Whether or not this condition has been fulfilled for this favor.
	 */
	public boolean isComplete();

	/**
	 * Writes this condition to NBT.
	 * 
	 * @return An NBTTagCompound containing the data for this condition, which can
	 *         be read by {@link IFavorCondition#readFromNBT(NBTTagCompound c)}.
	 */
	public NBTTagCompound writeToNBT();

	/**
	 * Reads this condition from NBT.
	 * 
	 * @param c
	 *            The NBTTagCompound containing the data for this condition.
	 */
	public void readFromNBT(NBTTagCompound c);

	/**
	 * Gets the text of this condition, that is, what should be shown on the gui
	 * screen.
	 * 
	 * @return The text that should appear on the gui screen for this condition.
	 */
	public String getText();
}
