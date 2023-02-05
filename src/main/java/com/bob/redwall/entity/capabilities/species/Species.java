package com.bob.redwall.entity.capabilities.species;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Species {
	private final static Map<String, Species> BY_ID = new HashMap<>();
	private String id;
	private int strength;
	private int speed;
	private int vitality;
	private int agility;
	private float hpReqForRegen = 5.0F;
	private float leafWalkSpeedMod = 0.25F;
	private float critDamageMod = 1.0F;
	private float swimSpeedMod = 0.0F;
	private boolean ignoreScoffSkill = false;
	private boolean beserker = false;
	private boolean dodgeDamage = false;
	private float digSpeedMod = 1.0F;
	private float meleeDamageMod = 1.0F;
	
	public Species(String id, int strength, int speed, int vitality, int agility) {
		this.id = id;
		this.strength = strength;
		this.speed = speed;
		this.vitality = vitality;
		this.agility = agility;
		BY_ID.put(id, this);
	}
	
	public String getID() {
		return this.id;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public int getVitality() {
		return this.vitality;
	}
	
	public int getAgility() {
		return this.agility;
	}
	
	public float getHPReqForRegen() {
		return this.hpReqForRegen;
	}
	
	private Species setHPReqForRegen(float hp) {
		this.hpReqForRegen = hp;
		return this;
	}
	
	public float getLeafWalkSpeedMod() {
		return this.leafWalkSpeedMod;
	}
	
	private Species setLeafWalkSpeedMod(float mod) {
		this.leafWalkSpeedMod = mod;
		return this;
	}
	
	public float getCritDamageMod() {
		return this.critDamageMod;
	}
	
	private Species setCritDamageMod(float mod) {
		this.critDamageMod = mod;
		return this;
	}
	
	public float getSwimSpeedMod() {
		return this.swimSpeedMod;
	}
	
	private Species setSwimSpeedMod(float mod) {
		this.swimSpeedMod = mod;
		return this;
	}
	
	public boolean getIgnoreScoffSkill() {
		return this.ignoreScoffSkill;
	}
	
	private Species setIgnoreScoffSkill(boolean b) {
		this.ignoreScoffSkill = b;
		return this;
	}
	
	public boolean getBeserker() {
		return this.beserker;
	}
	
	private Species setBeserker(boolean b) {
		this.beserker = b;
		return this;
	}
	
	public boolean getDodgeDamage() {
		return this.dodgeDamage;
	}
	
	private Species setDodgeDamage(boolean b) {
		this.dodgeDamage = b;
		return this;
	}
	
	public float getDigSpeedMod() {
		return this.digSpeedMod;
	}
	
	private Species setDigSpeedMod(float mod) {
		this.digSpeedMod = mod;
		return this;
	}
	
	public float getMeleeDamageMod() {
		return this.meleeDamageMod;
	}
	
	private Species setMeleeDamageMod(float mod) {
		this.meleeDamageMod = mod;
		return this;
	}
	
	private Species create() {
		BY_ID.put(this.id, this);
		return this;
	}
	
	public static Species getByID(String id) {
		return BY_ID.get(id);
	}
	
	public static Set<String> getIDs() {
		return BY_ID.keySet();
	}
	
	public static class SpeciesList {
		public static final Species MOUSE = new Species("mouse", -5, 0, 5, 0).setHPReqForRegen(2.0F).create();
		public static final Species SQUIRREL = new Species("squirrel", -5, 0, 0, 5).setLeafWalkSpeedMod(-0.25F).create();
		public static final Species SHREW = new Species("shrew", 0, 5, -5, 0).setCritDamageMod(1.25F).create();
		public static final Species OTTER = new Species("otter", 0, 0, -5, 1).setSwimSpeedMod(-0.25F).create();
		public static final Species HARE = new Species("hare", 0, 5, 0, -5).setIgnoreScoffSkill(true).create(); //unique ability does nothing yet :(
		public static final Species BADGER = new Species("badger", 5, -5, 0, 0).setBeserker(true).create();
		public static final Species HEDGEHOG = new Species("hedgehog", 0, -5, 5, 0).setDodgeDamage(true).create();
		public static final Species MOLE = new Species("mole", 5, 0, 0, -5).setDigSpeedMod(1.5F).create();

		public static final Species RAT = new Species("rat", 5, 0, -5, 0).setMeleeDamageMod(1.1F).create();
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
