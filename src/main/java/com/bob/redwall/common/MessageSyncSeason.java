package com.bob.redwall.common;

import java.util.Map.Entry;

import com.bob.redwall.dimensions.redwall.EnumSeasons;
import com.bob.redwall.entity.capabilities.season.ISeasonCap;
import com.bob.redwall.entity.capabilities.season.SeasonCapProvider;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MessageSyncSeason implements IMessage {
	private static int i = 0;
	private Mode mode;
	private String season;
	
	public MessageSyncSeason() { }
	
	public MessageSyncSeason(Mode mode, String season) {
		this.mode = mode;
		this.season = season;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.season = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.season);
	}

	public static class Handler implements IMessageHandler<MessageSyncSeason, IMessage> {
		@Override
		public IMessage onMessage(final MessageSyncSeason message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	if(message.mode == Mode.SEASON) {
	                	WorldClient world = Minecraft.getMinecraft().world;
	                	ISeasonCap cap = world.getCapability(SeasonCapProvider.SEASON_CAP, null);
	                	cap.setSeason(EnumSeasons.valueOf(message.season));
	                	Long2ObjectMap<Chunk> chunkMapping = ReflectionHelper.getPrivateValue(ChunkProviderClient.class, world.getChunkProvider(), "chunkMapping", "field_73236_b");
	                	for(Entry<Long, Chunk> entry : chunkMapping.entrySet()) {
	                		Chunk chunk = entry.getValue();
	                		for(int i = 0; i < 255; i += 16) {
	                    		BlockPos pos = chunk.getPos().getBlock(0, 0 + i, 0);
	                    		world.markAndNotifyBlock(pos, chunk, Blocks.AIR.getDefaultState(), chunk.getBlockState(pos), 11);
	                		}
	                	}
                	}
                }
            });
		    return null; // no response message
		}
	}
	
	public static enum Mode {
		SEASON(i++);
		
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
