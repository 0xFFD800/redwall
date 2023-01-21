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
}
