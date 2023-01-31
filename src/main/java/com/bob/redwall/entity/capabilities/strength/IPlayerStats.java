package com.bob.redwall.entity.capabilities.strength;

public interface IPlayerStats {
	public void subtract(int points);
	public void set(int points);
	public void add(int points);
	public int get();
	public int getActual();
}
