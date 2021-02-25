package com.bob.redwall.entity.capabilities.strength;

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

public class Strength implements IStrength {
	public static final int DEFAULT_STRENGTH = 0;
	private int strength = DEFAULT_STRENGTH;
	private EntityLivingBase player;
	private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.<IAttribute, AttributeModifier>newHashMap();

	@Override
	public void subtract(int points) {
		this.strength -= points;
		this.update();
	}

	@Override
	public void add(int points) {
		this.strength += points;
		this.update();
	}

	@Override
	public void set(int points) {
		this.strength = points;
		this.update();
	}

	@Override
	public int get() {
		return this.player instanceof EntityPlayer && ((EntityPlayer) player).isCreative() ? 0 : this.strength;
	}

	@Override
	public void update() {
		if (this.player != null) {
			this.removeAttributesModifiersFromEntity(this.player, this.player.getAttributeMap());
			this.applyAttributesModifiersToEntity(this.player, this.player.getAttributeMap(), this.get() > 30 ? 30 : this.get() < 0 ? 0 : this.get());
			if (this.player instanceof EntityPlayerMP) Ref.NETWORK.sendTo(new MessageSyncCap(MessageSyncCap.Mode.STRENGTH, this.get(), this.player.getEntityId()), (EntityPlayerMP) this.player);
		}
	}

	@Override
	public void init(EntityLivingBase player) {
		this.player = player;
		this.registerStrengthAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "9998FA56-323B-4433-935B-2FC3FAC87635", 0.03, 2);
		this.registerStrengthAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "18287456-143B-4C6A-935B-ABC3AF097609", 0.05, 2);
		this.update();
	}

	public void registerStrengthAttributeModifier(IAttribute attribute, String uniqueId, double ammount, int operation) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uniqueId), "strength", ammount, operation);
		this.attributeModifierMap.put(attribute, attributemodifier);
	}

	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn) {
		for (Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance((IAttribute) entry.getKey());

			if (iattributeinstance != null) {
				iattributeinstance.removeModifier((AttributeModifier) entry.getValue());
			}
		}
	}

	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
		for (Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance((IAttribute) entry.getKey());

			if (iattributeinstance != null) {
				AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
				iattributeinstance.removeModifier(attributemodifier);
				iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), "strength" + " " + amplifier, this.getAttributeModifierAmount(entityLivingBaseIn.world, amplifier, attributemodifier, entry.getKey()), attributemodifier.getOperation()));
			}
		}
	}

	public double getAttributeModifierAmount(World world, int amplifier, AttributeModifier modifier, IAttribute iattribute) {
		if (iattribute == SharedMonsterAttributes.ATTACK_SPEED) {
			if (amplifier > 10) amplifier = -11;
			else amplifier -= 21;
		}
		if (iattribute == SharedMonsterAttributes.ATTACK_DAMAGE && amplifier > 30) amplifier = 30;
		return modifier.getOperation() == 0 ? modifier.getAmount() * amplifier : modifier.getAmount() * (double) (amplifier + (amplifier < 0 ? -1 : 1));
	}
}
