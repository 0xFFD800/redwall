package com.bob.redwall.common;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.factions.Faction;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUIInteractServer implements IMessage {
	private static int i = 0;
	private int modeId;
	private int value;
	private String string;
	private String string2;
	private String string3;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;
	
	public MessageUIInteractServer()  { }
	
	public MessageUIInteractServer(Mode mode, int value, String string)  {
		this.modeId = mode.id;
		this.value = value;
		this.string = string;
		this.string2 = "";
		this.string3 = "";
    }
	
	public MessageUIInteractServer(Mode mode, int value, String string, String string2, String string3)  {
		this.modeId = mode.id;
		this.string = string;
		this.string2 = string2;
		this.string3 = string3;
		this.value = value;
    }
	
	public MessageUIInteractServer(Mode mode, int value)  {
		this.modeId = mode.id;
		this.value = value;
		this.string = "";
		this.string2 = "";
		this.string3 = "";
    }
	
	public MessageUIInteractServer(Mode mode, int value, double posX, double posY, double posZ)  {
		this.modeId = mode.id;
		this.value = value;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.string = "";
		this.string2 = "";
		this.string3 = "";
    }

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.modeId, 5);
		ByteBufUtils.writeVarInt(buf, this.value, 4);
		ByteBufUtils.writeUTF8String(buf, this.string);
		ByteBufUtils.writeUTF8String(buf, this.string2);
		ByteBufUtils.writeUTF8String(buf, this.string3);
		ByteBufUtils.writeVarInt(buf, (int)(this.posX * 100.0D), 5);
		ByteBufUtils.writeVarInt(buf, (int)(this.posY * 100.0D), 5);
		ByteBufUtils.writeVarInt(buf, (int)(this.posZ * 100.0D), 5);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.modeId = ByteBufUtils.readVarInt(buf, 5);
		this.value = ByteBufUtils.readVarInt(buf, 4);
		this.string = ByteBufUtils.readUTF8String(buf);
		this.string2 = ByteBufUtils.readUTF8String(buf);
		this.string3 = ByteBufUtils.readUTF8String(buf);
		this.posX = (double)ByteBufUtils.readVarInt(buf, 5) / 100.0D;
		this.posY = (double)ByteBufUtils.readVarInt(buf, 5) / 100.0D;
		this.posZ = (double)ByteBufUtils.readVarInt(buf, 5) / 100.0D;
	}

	public static class Handler implements IMessageHandler<MessageUIInteractServer, IMessage> {
		@Override
		public IMessage onMessage(final MessageUIInteractServer message, MessageContext ctx) {
			final EntityPlayerMP thePlayer = (EntityPlayerMP) (ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player);
			thePlayer.getServer().addScheduledTask(new Runnable() {
                @Override
                public void run() {
			    	if(message.modeId == Mode.NPC_TALK.id) {
	                    EntityAbstractNPC npc = (EntityAbstractNPC)thePlayer.world.getEntityByID(message.value);
						if(!npc.getTalkingActive()) {
							if(npc.hasCustomMessage()) {
								npc.setTalking(new TextComponentString(npc.getCustomMessage()));
							} else {
								npc.setTalking(new TextComponentString(message.string));
							}
							npc.setTimeSinceLastTalk(0);
							npc.setTalkingActive(true);
						}
			    	} else if(message.modeId == Mode.OPEN_GUI.id) {
					    EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
					    serverPlayer.openGui(Ref.MODID, message.value, serverPlayer.world, (int)serverPlayer.posX, (int)serverPlayer.posY, (int)serverPlayer.posZ);
				    } else if(message.modeId == Mode.OPEN_GUI_CONTAINER.id) {
					    EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
					    serverPlayer.openGui(Ref.MODID, message.value, serverPlayer.world, (int)message.posX, (int)message.posY, (int)message.posZ);
				    } else if(message.modeId == Mode.SEND_LEVEL_TOAST.id) {
				    	SystemToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(), SystemToast.Type.TUTORIAL_HINT, new TextComponentTranslation(message.string), new TextComponentTranslation(message.string2, Faction.getFactionByID(message.string3).getLocalizedName(), FactionCap.FacStatType.byID(message.value).getLocalizedName()));
				    }
                }
            });
			return message.modeId == Mode.OPEN_GUI.id ? new MessageUIInteractServer(Mode.OPEN_GUI, message.value) : null; 
		}
	}
	
	public static enum Mode {
		NPC_TALK(i++),
		OPEN_GUI(i++),
		OPEN_GUI_CONTAINER(i++),
		SEND_LEVEL_TOAST(i++);
		
		private int id;
		private Mode(int id) {
			this.id = id;
		}
	}
}
