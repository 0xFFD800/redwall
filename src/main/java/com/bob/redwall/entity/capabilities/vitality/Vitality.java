package com.bob.redwall.entity.capabilities.vitality;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

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
import net.minecraft.world.World;

public class Vitality implements IVitality {
	public static final int DEFAULT_VITALITY = -10;
	private int vitality = DEFAULT_VITALITY;
	private EntityLivingBase player;
	private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.<IAttribute, AttributeModifier>newHashMap();
	
	@Override
	public void subtract(int points) {
		this.vitality -= points;
		this.update();
	}
	
	@Override
	public void add(int points) {
		this.vitality += points;	
		this.update();
		this.player.heal(points);
	}
	
	@Override
	public void set(int points) {
		int i = points - vitality;
		this.vitality = points;
		this.update();
		if(this.player != null) {
			if(i > 0) {
				this.player.heal(i);
			}
		}
	}
	
	@Override
	public int get() {
		return this.player instanceof EntityPlayer && ((EntityPlayer)player).isCreative() ? 0 : this.vitality;
	}
	
	@Override
	public void update() {
		if(this.player != null) {
			this.removeAttributesModifiersFromEntity(this.player, this.player.getAttributeMap());
			this.applyAttributesModifiersToEntity(this.player, this.player.getAttributeMap(), this.get() > 20 ? 20 : this.get() < -10 ? -10 : this.get());
			if(this.player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageSyncCap(MessageSyncCap.Mode.VITALITY, this.get(), this.player.getEntityId()), (EntityPlayerMP)this.player);
		}
	}

	@Override
	public void init(EntityLivingBase player) {
		this.player = player;
		this.registerStrengthAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "992FFA56-376B-4AA8-935B-2FC368070635", 1, 0);
		this.update();
	}
	 
	public void registerStrengthAttributeModifier(IAttribute attribute, String uniqueId, double ammount, int operation) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uniqueId), "strength", ammount, operation);
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
				iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), "strength" + " " + amplifier, this.getAttributeModifierAmount(entityLivingBaseIn.world, amplifier, attributemodifier, entry.getKey()), attributemodifier.getOperation()));
			}
		}
	}
	
	public double getAttributeModifierAmount(World world, int amplifier, AttributeModifier modifier, IAttribute iattribute) {
		if(iattribute == SharedMonsterAttributes.MAX_HEALTH && amplifier > 20) amplifier = 20;
		return modifier.getOperation() == 0 ? modifier.getAmount() * amplifier : modifier.getAmount() * (double)(amplifier + (amplifier < 0 ? -1 : 1));
	}
}
