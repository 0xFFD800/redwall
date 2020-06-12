package com.bob.redwall;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Ref {
    public static Logger LOGGER;
    public static final String MODID = "redwall";
    public static final String VERSION = "0.0.1a pre-release";
	public static final String CLIENT_PROXY = "com.bob.redwall.proxy.ClientProxy";
	public static final String SERVER_PROXY = "com.bob.redwall.proxy.CommonProxy";
	public static final String NETWORK_CHANNEL_NAME = "Redwall";
	public static SimpleNetworkWrapper NETWORK;
}
