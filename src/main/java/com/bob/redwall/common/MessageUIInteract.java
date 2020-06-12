package com.bob.redwall.common;

import com.bob.redwall.entity.npc.EntityAbstractNPC;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUIInteract implements IMessage {
	private int value;
	
	public MessageUIInteract(){}
	
	public MessageUIInteract(int value) {
		this.value = value;
	}
	
	@Override 
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.value, 5);
	}
	
	@Override 
	public void fromBytes(ByteBuf buf) {
		this.value = ByteBufUtils.readVarInt(buf, 5);
	}
	
	public static class Handler implements IMessageHandler<MessageUIInteract, IMessage> {
	    @Override 
	    public IMessage onMessage(final MessageUIInteract message, MessageContext ctx) {
	    	Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
			    	World world = Minecraft.getMinecraft().world;
			    	
					EntityAbstractNPC npc = (EntityAbstractNPC) world.getEntityByID(message.value);
					npc.talk(Minecraft.getMinecraft().player);
				}
	    	});
		    return null;
	    }
	}
}
