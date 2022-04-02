package com.bob.redwall.common;

import com.bob.redwall.entity.capabilities.agility.AgilityProvider;
import com.bob.redwall.entity.capabilities.agility.IAgility;
import com.bob.redwall.entity.capabilities.armor_weight.ArmorWeightProvider;
import com.bob.redwall.entity.capabilities.armor_weight.IArmorWeight;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.bob.redwall.entity.capabilities.speed.ISpeed;
import com.bob.redwall.entity.capabilities.speed.SpeedProvider;
import com.bob.redwall.entity.capabilities.strength.IStrength;
import com.bob.redwall.entity.capabilities.strength.StrengthProvider;
import com.bob.redwall.entity.capabilities.vitality.IVitality;
import com.bob.redwall.entity.capabilities.vitality.VitalityProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncCap implements IMessage {
	private static int i = 0;

	public boolean value;
	private int type;
	private float f;
	private NBTTagCompound tag;
	private Mode mode;
	private int entityId;

	public MessageSyncCap() {}

	/**
	 * Constructor for capabilities required by different types of entities.
	 * 
	 * @param mode
	 *            The capability being synced, as defined in the enum.
	 * @param value
	 *            The value of the capability being synced.
	 * @param entityId
	 *            The entityId of the entity the capability is being synced to.
	 */
	public MessageSyncCap(Mode mode, float f, int entityId) {
		this.mode = mode;
		this.f = f;
		this.entityId = entityId;
	}

	/**
	 * Constructor for capabilities only required for players.
	 * 
	 * @param mode
	 *            The capability being synced, as defined in the enum.
	 * @param value
	 *            The boolean value of the capability being synced.
	 */
	public MessageSyncCap(boolean value, int type, Mode mode) {
		this.value = value;
		this.type = type;
		this.mode = mode;
		this.f = 0;
		this.tag = new NBTTagCompound();
	}

	/**
	 * Constructor for capabilities only required for players.
	 * 
	 * @param code
	 *            The capability being synced, as defined in the enum.
	 * @param value
	 *            The float value of the capability being synced.
	 */
	public MessageSyncCap(float f, Mode mode) {
		this.f = f;
		this.mode = mode;
		this.value = false;
		this.type = 0;
		this.tag = new NBTTagCompound();
	}

	/**
	 * Constructor for capabilities only required for players.
	 * 
	 * @param code
	 *            The capability being synced, as defined in the enum.
	 * @param value
	 *            The NBT value of the capability being synced.
	 */
	public MessageSyncCap(NBTTagCompound tag, Mode mode) {
		this.tag = tag;
		this.mode = mode;
		this.value = false;
		this.type = 0;
		this.f = 0;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.value ? 1 : 0, 5);
		ByteBufUtils.writeVarInt(buf, this.type, 5);
		ByteBufUtils.writeVarInt(buf, this.entityId, 5);
		ByteBufUtils.writeVarInt(buf, (int) (this.f * 100.0F), 5);
		ByteBufUtils.writeTag(buf, this.tag);
		ByteBufUtils.writeVarInt(buf, this.mode.getId(), 5);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.value = ByteBufUtils.readVarInt(buf, 5) == 1;
		this.type = ByteBufUtils.readVarInt(buf, 5);
		this.entityId = ByteBufUtils.readVarInt(buf, 5);
		this.f = ((float) ByteBufUtils.readVarInt(buf, 5)) / 100.0F;
		this.tag = ByteBufUtils.readTag(buf);
		this.mode = Mode.getById(ByteBufUtils.readVarInt(buf, 5));
	}

	public static class Handler implements IMessageHandler<MessageSyncCap, IMessage> {
		@Override
		public IMessage onMessage(final MessageSyncCap message, MessageContext ctx) {
			final EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
			if (clientPlayer == null)
				return null;
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					if (message.mode == Mode.ARMOR_WEIGHT) {
						IArmorWeight weight = clientPlayer.getCapability(ArmorWeightProvider.ARMOR_WEIGHT_CAP, null);
						weight.set(message.f);
					} else if (message.mode == Mode.FACTION_STATS) {
						IFactionCap cap = clientPlayer.getCapability(FactionCapProvider.FACTION_CAP, null);
						cap.readFromNBT(message.tag);
					} else if (message.mode == Mode.MOB_ATTACK_MODE) {
						Entity entity = clientPlayer.world.getEntityByID(message.entityId);

						if (!(entity instanceof EntityLivingBase))
							return;

						IAttacking attacking = entity.getCapability(AttackingProvider.ATTACKING_CAP, null);
						attacking.setMode((int) message.f);
					} else if (message.mode == Mode.MOB_DEFENDING) {
						Entity entity = clientPlayer.world.getEntityByID(message.entityId);

						if (!(entity instanceof EntityLivingBase))
							return;

						IDefending defending = entity.getCapability(DefendingProvider.DEFENDING_CAP, null);
						if (message.f > 8) {
							defending.setMode((int) message.f - 1);
							defending.set(true);
						} else {
							defending.set(false);
						}
					} else if (message.mode == Mode.SPEED) {
						ISpeed speed = clientPlayer.getCapability(SpeedProvider.SPEED_CAP, null);
						speed.set((int) message.f);
					} else if (message.mode == Mode.STRENGTH) {
						IStrength strength = clientPlayer.getCapability(StrengthProvider.STRENGTH_CAP, null);
						strength.set((int) message.f);
					} else if (message.mode == Mode.VITALITY) {
						IVitality vitality = clientPlayer.getCapability(VitalityProvider.VITALITY_CAP, null);
						vitality.set((int) message.f);
					} else if (message.mode == Mode.AGILITY) {
						IAgility agility = clientPlayer.getCapability(AgilityProvider.AGILITY_CAP, null);
						agility.set((int) message.f);
					}
				}
			});
			return null;
		}
	}

	public static enum Mode {
		ARMOR_WEIGHT(i++), FACTION_STATS(i++), MOB_ATTACK_MODE(i++), MOB_DEFENDING(i++), SPEED(i++), STRENGTH(i++), VITALITY(i++), AGILITY(i++);

		private final int id;

		private Mode(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public static Mode getById(int id) {
			for (int a = 0; a < Mode.values().length; a++)
				if (Mode.values()[a].id == id)
					return Mode.values()[a];

			return null;
		}
	}
}
