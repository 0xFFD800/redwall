package com.bob.redwall.common;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.booleancap.attacking.AttackingProvider;
import com.bob.redwall.entity.capabilities.booleancap.attacking.IAttacking;
import com.bob.redwall.entity.capabilities.booleancap.defending.DefendingProvider;
import com.bob.redwall.entity.capabilities.booleancap.defending.IDefending;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
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

	public MessageSetCap(){}
	
	public MessageSetCap(boolean value, int type, Mode mode) {
	    this.value = value;
	    this.type = type;
	    this.mode = mode;
	    this.f = 0;
	}
	
	public MessageSetCap(float f, int type, Mode mode) {
	    this.f = f;
	    this.type = type;
	    this.mode = mode;
	    this.value = false;
	}
	
	public MessageSetCap(Mode mode) {
		this.mode = mode;
		this.f = 0;
		this.type = 0;
		this.value = false;
	}
	
	@Override 
	public void toBytes(ByteBuf buf) {
	    ByteBufUtils.writeVarInt(buf, this.value ? 1 : 0, 5);
	    ByteBufUtils.writeVarInt(buf, this.type, 5);
	    ByteBufUtils.writeVarInt(buf, (int)(this.f * 100.0F), 5);
	    ByteBufUtils.writeVarInt(buf, this.mode.getId(), 5);
	}
	
	@Override 
	public void fromBytes(ByteBuf buf) {
	    this.value = ByteBufUtils.readVarInt(buf, 5) == 1;
	    this.type = ByteBufUtils.readVarInt(buf, 5);
	    this.f = ((float) ByteBufUtils.readVarInt(buf, 5)) / 100.0F;
	    this.mode = Mode.getById(ByteBufUtils.readVarInt(buf, 5));
	}
	
	public static class Handler implements IMessageHandler<MessageSetCap, IMessage> {
	    @Override 
	    public IMessage onMessage(final MessageSetCap message, MessageContext ctx) {
		    final EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			serverPlayer.getServer().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	if(message.mode == Mode.ATTACKING) {
				    	IAttacking attacking = serverPlayer.getCapability(AttackingProvider.ATTACKING_CAP, null);
				    	attacking.set(message.value);
				    	attacking.setMode(message.type);
                	} else if(message.mode == Mode.DEFENDING) {
				    	IDefending defending = serverPlayer.getCapability(DefendingProvider.DEFENDING_CAP, null);
				    	defending.set(message.value);
				    	defending.setMode(message.type);
				    	if(message.type == 2) {
				    		serverPlayer.setActiveHand(EnumHand.OFF_HAND);
				    	}
				    	
				    	if(!message.value) {
				    		serverPlayer.resetActiveHand();
				    	}
                	} else if(message.mode == Mode.REQUEST_FACTION_UPDATE) {
        				RedwallUtils.updatePlayerFactionStats(serverPlayer);
                	}
                }
			});
		    return null;
	    }
	}
	
	public static enum Mode {
		ATTACKING(i++), 
		DEFENDING(i++),
		REQUEST_FACTION_UPDATE(i++);
		
		private final int id;
		private Mode(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}

		public static Mode getById(int id) {
			for(int a = 0; a < Mode.values().length; a++) {
				if(Mode.values()[a].id == id) {
					return Mode.values()[a];
				}
			}
			
			return null;
		}
	}
}
