package com.bob.redwall.proxy;

import com.bob.redwall.Ref;
import com.bob.redwall.common.MessageSetCap;
import com.bob.redwall.common.MessageSyncCap;
import com.bob.redwall.common.MessageSyncSeason;
import com.bob.redwall.common.MessageUIInteract;
import com.bob.redwall.common.MessageUIInteractServer;
import com.bob.redwall.dimensions.ModDimensions;
import com.bob.redwall.dimensions.shared.rtg.RTG;
import com.bob.redwall.factions.Faction;
import com.bob.redwall.init.ArmorHandler;
import com.bob.redwall.init.BlockColorHandler;
import com.bob.redwall.init.BlockHandler;
import com.bob.redwall.init.CapabilityHandler;
import com.bob.redwall.init.EventHandler;
import com.bob.redwall.init.GuiHandler;
import com.bob.redwall.init.ItemColorHandler;
import com.bob.redwall.init.ItemHandler;
import com.bob.redwall.init.LootHandler;
import com.bob.redwall.init.SpeechHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(FMLInitializationEvent event) {
		int packetId = 0;
		Ref.NETWORK.registerMessage(MessageSyncSeason.Handler.class, MessageSyncSeason.class, packetId++, Side.CLIENT);
		Ref.NETWORK.registerMessage(MessageSetCap.Handler.class, MessageSetCap.class, packetId++, Side.SERVER);
		Ref.NETWORK.registerMessage(MessageSyncCap.Handler.class, MessageSyncCap.class, packetId++, Side.CLIENT);
		Ref.NETWORK.registerMessage(MessageUIInteract.Handler.class, MessageUIInteract.class, packetId++, Side.CLIENT);
		Ref.NETWORK.registerMessage(MessageUIInteractServer.Handler.class, MessageUIInteractServer.class, packetId++, Side.SERVER);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		ItemHandler.registerRenders();
		ArmorHandler.registerRenders();
		ItemColorHandler.init();
		BlockHandler.registerRenders();
		BlockColorHandler.init();
		ModDimensions.init();
		LootHandler.register();
		SpeechHandler.init();
		Faction.FacList.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(Ref.MODID, new GuiHandler());
		
		RTG.init(event);
		super.init(event);
	}
}
