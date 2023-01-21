package com.bob.redwall.entity.capabilities.species;

import java.util.HashMap;
import java.util.Map;

public class Species {
	private final static Map<String, Species> BY_ID = new HashMap<>();
	private String id;
	
	public Species(String id) {
		this.id = id;
		BY_ID.put(id, this);
	}
	
	public String getID() {
		return this.id;
	}
	
	public static Species getByID(String id) {
		return BY_ID.get(id);
	}
	
	/*
	 * ===== GOOD CREATURES =====
	 * Mouse: 
	 * Buffed - Vitality
	 * Nerfed - Strength
	 * Unique - HP required for regen is 2 instead of 5
	 * 
	 * Squirrel:
	 * Buffed - Agility
	 * Nerfed - Strength
	 * Unique - 25% speed boost while walking on leaves
	 * 
	 * Shrew:
	 * Buffed - Speed
	 * Nerfed - Vitality
	 * Unique - Critical hits deal 25% more damage
	 * 
	 * Otter:
	 * Buffed - Agility
	 * Nerfed - Vitality
	 * Unique - 25% speed boost while swimming
	 * 
	 * Hare:
	 * Buffed - Speed
	 * Nerfed - Agility
	 * Unique - Scoffing skill always at maximum
	 * 
	 * Badger:
	 * Buffed - Strength
	 * Nerfed - Speed
	 * Unique - No speed or strength reduction from low HP
	 * 
	 * Hedgehog:
	 * Buffed - Vitality
	 * Nerfed - Speed
	 * Unique - Blocking damage also applies to dodging
	 * 
	 * Mole:
	 * Buffed - Strength
	 * Nerfed - Agility
	 * Unique - 50% digging speed boost
	 * 
	 * 
	 * ===== EVIL CREATURES =====
	 * Rat: 
	 * Buffed - Strength
	 * Nerfed - Vitality
	 * Unique - 10% damage boost to melee attacks
	 * 
	 * Ferret:
	 * Buffed - Agility
	 * Nerfed - Strength
	 * Unique - 10% damage boost to ranged attacks
	 * 
	 * Weasel:
	 * Buffed - Speed
	 * Nerfed - Strength
	 * Unique - 10% extra dodge chance
	 * 
	 * Stoat:
	 * Buffed - Vitality
	 * Nerfed - Speed
	 * Unique - 50% less negative effects from drunkenness
	 * 
	 * Cat:
	 * Buffed - Strength
	 * Nerfed - Speed
	 * Unique - Melee attacks ignore 50% of all armor points
	 * 
	 * Fox:
	 * Buffed - Vitality
	 * Nerfed - Agility
	 * Unique - Does not passively lose loyalty
	 * 
	 * Ermine:
	 * Buffed - Speed
	 * Nerfed - Agility
	 * Unique - 25% slower hunger accumulation
	 * 
	 * Sable:
	 * Buffed - Agility
	 * Nerfed - Vitality
	 * Unique - Enemies only detect at half the range
	 */
}
