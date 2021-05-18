package com.bob.redwall.entity.npc.favors;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import net.minecraft.item.ItemStack;

/**
 * A class to represent a "favor" done for an NPC. 
 * @author Luke
 */
public interface IFavor {
	
	/**
	 * Triggered when a player with this current favor kills an NPC.
	 * @param npc The NPC that was killed.
	 */
	public void killNPC(EntityAbstractNPC npc);
	
	/**
	 * 
	 * @param item The offered item stack. 
	 * @return The item in the player's hand after the item is examined.
	 */
	public ItemStack offerItem(ItemStack item);
}
