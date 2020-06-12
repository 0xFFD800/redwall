package com.bob.redwall.entity.capabilities.booleancap.defending;

public class Defending implements IDefending {
	private boolean attacking;
	private int mode = 0;

	@Override
	public void set(boolean value) {
		this.attacking = value;
	}
	
	@Override
	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public int getMode() {
		return this.mode;
	}

	@Override
	public boolean get() {
		return attacking;
	}

}
