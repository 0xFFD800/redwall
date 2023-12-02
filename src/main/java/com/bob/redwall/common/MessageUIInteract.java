package com.bob.redwall.common;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.npc.EntityAbstractNPC;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUIInteract implements IMessage {
	private static int i = 0;
	private int modeId;
	private int value;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;

	public MessageUIInteract() {}

	public MessageUIInteract(Mode mode, int value) {
		this.modeId = mode.id;
		this.value = value;
	}

	public MessageUIInteract(Mode mode, int value, double posX, double posY, double posZ) {
		this.modeId = mode.id;
		this.value = value;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.modeId, 5);
		ByteBufUtils.writeVarInt(buf, this.value, 5);
		ByteBufUtils.writeVarInt(buf, (int) (this.posX * 100.0D), 5);
		ByteBufUtils.writeVarInt(buf, (int) (this.posY * 100.0D), 5);
		ByteBufUtils.writeVarInt(buf, (int) (this.posZ * 100.0D), 5);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.modeId = ByteBufUtils.readVarInt(buf, 5);
		this.value = ByteBufUtils.readVarInt(buf, 5);
		this.posX = (double) ByteBufUtils.readVarInt(buf, 5) / 100.0D;
		this.posY = (double) ByteBufUtils.readVarInt(buf, 5) / 100.0D;
		this.posZ = (double) ByteBufUtils.readVarInt(buf, 5) / 100.0D;
	}

	public static class Handler implements IMessageHandler<MessageUIInteract, IMessage> {
		@Override
		public IMessage onMessage(final MessageUIInteract message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					World world = Minecraft.getMinecraft().world;
					EntityPlayer player = Minecraft.getMinecraft().player;

					if (message.modeId == Mode.NPC_TALK.id) {
						EntityAbstractNPC npc = (EntityAbstractNPC) world.getEntityByID(message.value);
						npc.talk(Minecraft.getMinecraft().player);
					} else if (message.modeId == Mode.OPEN_GUI.id)
						player.openGui(Ref.MODID, message.value, world, (int) message.posX, (int) message.posY, (int) message.posZ);
				}
			});
			return null;
		}
	}

	public static enum Mode {
		NPC_TALK(i++), OPEN_GUI(i++);

		private int id;

		private Mode(int id) {
			this.id = id;
		}
	}
}
