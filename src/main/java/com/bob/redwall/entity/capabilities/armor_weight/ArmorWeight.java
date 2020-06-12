package com.bob.redwall.entity.capabilities.armor_weight;

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

public class ArmorWeight implements IArmorWeight {
	public static final float DEFAULT_SPEED = 0;
	private float speed = DEFAULT_SPEED;
	private EntityPlayer player;
	private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.<IAttribute, AttributeModifier>newHashMap();
	
	@Override
	public void set(float points) {
		this.speed = points;
		this.update();
	}
	
	@Override
	public float get() {
		return this.speed;
	}
	
	@Override
	public void update() {
		if(this.player != null) {
			this.removeAttributesModifiersFromEntity(this.player, this.player.getAttributeMap());
			this.applyAttributesModifiersToEntity(this.player, this.player.getAttributeMap(), this.speed);
			if(this.player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageSyncCap(this.speed, MessageSyncCap.Mode.ARMOR_WEIGHT), (EntityPlayerMP)this.player);
		}
	}

	@Override
	public void init(EntityPlayer player) {
		this.player = player;
		this.registerStrengthAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "982DF76F-3DFB-EDC8-9312-2F98C4756ACE", 0.05, 2);
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
	
	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, float speed2) {
		for (Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance((IAttribute)entry.getKey());
			
			if (iattributeinstance != null) {
				AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
				iattributeinstance.removeModifier(attributemodifier);
				iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), "armor weight" + " " + speed2, this.getAttributeModifierAmount(speed2, attributemodifier, entry.getKey()), attributemodifier.getOperation()));
			}
		}
	}
	
	public double getAttributeModifierAmount(float amplifier, AttributeModifier modifier, IAttribute iattribute) {
		return modifier.getOperation() == 0 ? modifier.getAmount() * amplifier : modifier.getAmount() * (double)(amplifier + (amplifier < 0 ? -1 : 1));
	}
}
