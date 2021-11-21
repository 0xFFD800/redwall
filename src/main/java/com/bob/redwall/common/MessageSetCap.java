package com.bob.redwall.common;

import java.util.ArrayList;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.agility.AgilityProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.speed.SpeedProvider;
import com.bob.redwall.entity.capabilities.strength.StrengthProvider;
import com.bob.redwall.entity.capabilities.vitality.VitalityProvider;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.entity.npc.favors.IFavorCondition;
import com.bob.redwall.entity.npc.favors.IFavorReward;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetCap implements IMessage {
	private static int i = 0;

	public boolean value;
	private int type;
	private float f;
	private Mode mode;
	private NBTTagCompound tag;

	public MessageSetCap() {}

	public MessageSetCap(boolean value, int type, Mode mode) {
		this.value = value;
		this.type = type;
		this.mode = mode;
		this.f = 0;
		this.tag = new NBTTagCompound();
	}

	public MessageSetCap(float f, int type, Mode mode) {
		this.f = f;
		this.type = type;
		this.mode = mode;
		this.value = false;
		this.tag = new NBTTagCompound();
	}

	public MessageSetCap(NBTTagCompound tag, int type, Mode mode) {
		this.mode = mode;
		this.f = 0;
		this.type = type;
		this.value = false;
		this.tag = tag;
	}

	public MessageSetCap(Mode mode) {
		this.mode = mode;
		this.f = 0;
		this.type = 0;
		this.value = false;
		this.tag = new NBTTagCompound();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.value ? 1 : 0, 5);
		ByteBufUtils.writeVarInt(buf, this.type, 5);
		ByteBufUtils.writeVarInt(buf, (int) (this.f * 100.0F), 5);
		ByteBufUtils.writeVarInt(buf, this.mode.getId(), 5);
		ByteBufUtils.writeTag(buf, this.tag);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.value = ByteBufUtils.readVarInt(buf, 5) == 1;
		this.type = ByteBufUtils.readVarInt(buf, 5);
		this.f = ((float) ByteBufUtils.readVarInt(buf, 5)) / 100.0F;
		this.mode = Mode.getById(ByteBufUtils.readVarInt(buf, 5));
		this.tag = ByteBufUtils.readTag(buf);
	}

	public static class Handler implements IMessageHandler<MessageSetCap, IMessage> {
		@Override
		public IMessage onMessage(final MessageSetCap message, MessageContext ctx) {
			final EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			serverPlayer.getServer().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					if (message.mode == Mode.ATTACKING) {
						IAttacking attacking = serverPlayer.getCapability(AttackingProvider.ATTACKING_CAP, null);
						attacking.set(message.value);
						if(message.type != -1) attacking.setMode(message.type);
					} else if (message.mode == Mode.DEFENDING) {
						IDefending defending = serverPlayer.getCapability(DefendingProvider.DEFENDING_CAP, null);
						defending.set(message.value);
						if(message.type != -1) defending.setMode(message.type);
						if (message.type == 2) serverPlayer.setActiveHand(EnumHand.OFF_HAND);
						if (!message.value) serverPlayer.resetActiveHand();
					} else if (message.mode == Mode.REQUEST_FACTION_UPDATE) {
						RedwallUtils.updatePlayerFactionStats(serverPlayer);
					} else if (message.mode == Mode.STRENGTH) {
						if (serverPlayer.experienceLevel < serverPlayer.getCapability(SpeedProvider.SPEED_CAP, null).get() + serverPlayer.getCapability(VitalityProvider.VITALITY_CAP, null).get() + serverPlayer.getCapability(AgilityProvider.AGILITY_CAP, null).get() + message.f) return;
						serverPlayer.getCapability(StrengthProvider.STRENGTH_CAP, null).set((int) message.f);
					} else if (message.mode == Mode.SPEED) {
						if (serverPlayer.experienceLevel < serverPlayer.getCapability(StrengthProvider.STRENGTH_CAP, null).get() + serverPlayer.getCapability(VitalityProvider.VITALITY_CAP, null).get() + serverPlayer.getCapability(AgilityProvider.AGILITY_CAP, null).get() + message.f) return;
						serverPlayer.getCapability(SpeedProvider.SPEED_CAP, null).set((int) message.f);
					} else if (message.mode == Mode.VITALITY) {
						if (serverPlayer.experienceLevel < serverPlayer.getCapability(SpeedProvider.SPEED_CAP, null).get() + serverPlayer.getCapability(StrengthProvider.STRENGTH_CAP, null).get() + serverPlayer.getCapability(AgilityProvider.AGILITY_CAP, null).get() + message.f) return;
						serverPlayer.getCapability(VitalityProvider.VITALITY_CAP, null).set((int) message.f);
					} else if (message.mode == Mode.AGILITY) {
						if (serverPlayer.experienceLevel < serverPlayer.getCapability(SpeedProvider.SPEED_CAP, null).get() + serverPlayer.getCapability(VitalityProvider.VITALITY_CAP, null).get() + serverPlayer.getCapability(StrengthProvider.STRENGTH_CAP, null).get() + message.f) return;
						serverPlayer.getCapability(AgilityProvider.AGILITY_CAP, null).set((int) message.f);
					} else if (message.mode == Mode.REQUEST_SKILLS_UPDATE) {
						RedwallUtils.updatePlayerSkillsStats(serverPlayer);
					} else if (message.mode == Mode.ACCEPT_FAVOR) {
						EntityAbstractNPC giver = (EntityAbstractNPC) serverPlayer.world.getEntityByID(message.type);
						Favor favor = new Favor(serverPlayer, giver, "", new ArrayList<IFavorCondition>(), new ArrayList<IFavorReward>(), new ArrayList<IFavorReward>(), 0);
						favor.readFromNBT(serverPlayer, message.tag);
						serverPlayer.getCapability(FactionCapProvider.FACTION_CAP, null).getFavors().add(favor);
						giver.setFavor(null);
					}
				}
			});
			return null;
		}
	}

	public static enum Mode {
		ATTACKING(i++), 
		DEFENDING(i++), 
		REQUEST_FACTION_UPDATE(i++), 
		STRENGTH(i++), 
		SPEED(i++), 
		AGILITY(i++), 
		VITALITY(i++), 
		REQUEST_SKILLS_UPDATE(i++), 
		ACCEPT_FAVOR(i++);

		private final int id;

		private Mode(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public static Mode getById(int id) {
			for (int a = 0; a < Mode.values().length; a++) {
				if (Mode.values()[a].id == id) {
					return Mode.values()[a];
				}
			}

			return null;
		}
	}
}
