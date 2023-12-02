package com.bob.redwall.entity.capabilities.speed;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSyncCap;
import com.google.common.collect.Maps;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Speed implements ISpeed {
	public static final int DEFAULT_SPEED = -10;
	private int speed = DEFAULT_SPEED;
	private EntityLivingBase player;
	private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.<IAttribute, AttributeModifier>newHashMap();
	
	@Override
	public void subtract(int points) {
		this.speed -= points;
		this.update();
	}
	
	@Override
	public void add(int points) {
		this.speed += points;	
		this.update();
	}
	
	@Override
	public void set(int points) {
		this.speed = points;
		this.update();
	}
	
	@Override
	public int get() {
		return player instanceof EntityPlayer && ((EntityPlayer)this.player).isCreative() ? 0 : this.speed;
	}
	
	@Override
	public int getActual() {
		return this.get() + RedwallUtils.getSpecies(player).getSpeed() + RedwallUtils.getHealthStatModifier(this.player);
	}
	
	@Override
	public void update() {
		if(this.player != null) {
			this.removeAttributesModifiersFromEntity(this.player, this.player.getAttributeMap());
			this.applyAttributesModifiersToEntity(this.player, this.player.getAttributeMap(), this.getActual() > 20 ? 20 : this.getActual() < -10 ? -10 : this.getActual());
			if(this.player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageSyncCap(MessageSyncCap.Mode.SPEED, this.get(), this.player.getEntityId()), (EntityPlayerMP)this.player);
		}
	}

	@Override
	public void init(EntityLivingBase player) {
		this.player = player;
		this.registerStrengthAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "198DFA56-3DFB-4FF8-935B-2F98CA070F75", 0.01, 2);
		this.update();
	}
	 
	public void registerStrengthAttributeModifier(IAttribute attribute, String uniqueId, double ammount, int operation) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uniqueId), "speed", ammount, operation);
		this.attributeModifierMap.put(attribute, attributemodifier);
	}
	
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn) {
		for (Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance((IAttribute)entry.getKey());
			
			if (iattributeinstance != null) {
				iattributeinstance.removeModifier((AttributeModifier)entry.getValue());	
			}
		}
	}
	
	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
		for (Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance((IAttribute)entry.getKey());
			
			if (iattributeinstance != null) {
				AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
				iattributeinstance.removeModifier(attributemodifier);
				iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), "speed" + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier, entry.getKey()), attributemodifier.getOperation()));
			}
		}
	}
	
	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier, IAttribute iattribute) {
		if(iattribute == SharedMonsterAttributes.MOVEMENT_SPEED && amplifier > 20) amplifier = 20;
		return modifier.getOperation() == 0 ? modifier.getAmount() * amplifier : modifier.getAmount() * (double)(amplifier + (amplifier < 0 ? -1 : 1));
	}
}
