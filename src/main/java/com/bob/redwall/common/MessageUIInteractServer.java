package com.bob.redwall.common;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.factions.Faction;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.RayTraceResult;
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
	
	public MessageUIInteractServer(Mode mode)  {
		this.modeId = mode.id;
		this.value = 0;
		this.string = "";
		this.string2 = "";
		this.string3 = "";
    }
	
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
			final EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServer().addScheduledTask(new Runnable() {
                @Override
                public void run() {
			    	if(message.modeId == Mode.NPC_TALK.id) {
	                    EntityAbstractNPC npc = (EntityAbstractNPC)player.world.getEntityByID(message.value);
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
					    player.openGui(Ref.MODID, message.value, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
				    } else if(message.modeId == Mode.OPEN_GUI_CONTAINER.id) {
					    player.openGui(Ref.MODID, message.value, player.world, (int)message.posX, (int)message.posY, (int)message.posZ);
				    } else if(message.modeId == Mode.SEND_LEVEL_TOAST.id) {
				    	SystemToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(), SystemToast.Type.TUTORIAL_HINT, new TextComponentTranslation(message.string), new TextComponentTranslation(message.string2, Faction.getFactionByID(message.string3).getLocalizedName(), FactionCap.FacStatType.byID(message.value).getLocalizedName()));
				    } else if(message.modeId == Mode.PERFORM_ATTACK.id) {
					    IAttacking attacking = player.getCapability(AttackingProvider.ATTACKING_CAP, null);
						if (attacking.get()) {
							attacking.set(false);
							float i = (float) player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

							label1: {
								RayTraceResult result = RedwallUtils.raytrace(player, i);
								if (result == null) break label1;
								Entity entity = result.entityHit;
								if (entity != null && !(entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow)) {
									RedwallUtils.doPlayerAttack(player, entity);
								}
							}
						}
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
		SEND_LEVEL_TOAST(i++),
		PERFORM_ATTACK(i++);
		
		private int id;
		private Mode(int id) {
			this.id = id;
		}
	}
}
