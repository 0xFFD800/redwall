package com.bob.redwall.entity.capabilities.agility;

import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSyncCap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Agility implements IAgility {
	public static final int DEFAULT_AGILITY = 0;
	private int agility = DEFAULT_AGILITY;
	private EntityLivingBase player;
	private boolean isInit = false;
	
	@Override
	public void subtract(int points) {
		this.agility -= points;
		this.update();
	}
	
	@Override
	public void add(int points) {
		this.agility += points;	
		this.update();
	}
	
	@Override
	public void set(int points) {
		this.agility = points;
		this.update();
	}
	
	@Override
	public int get() {
		return this.player instanceof EntityPlayer && ((EntityPlayer)player).isCreative() ? 0 : this.agility;
	}
	
	@Override
	public void update() {
		if(this.player != null) {
			if(this.player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageSyncCap(MessageSyncCap.Mode.AGILITY, this.get(), this.player.getEntityId()), (EntityPlayerMP)this.player);
		}
	}

	@Override
	public void init(EntityLivingBase player) {
		this.player = player;
		this.update();
	}
	
	@Override
	public void setInit() {
		this.isInit = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInit;
	}
}
