package com.bob.redwall.dimensions.shared.rtg.api.util;

import org.apache.logging.log4j.Level;

import com.bob.redwall.dimensions.shared.rtg.api.RTGAPI;

import net.minecraftforge.fml.common.FMLLog;


public class Logger {
    @SuppressWarnings("deprecation")
	public static void debug(String format, Object... data) {
        if (RTGAPI.config().ENABLE_DEBUGGING.get()) {
            FMLLog.log(Level.WARN, "[RTG-DEBUG] " + format, data);
        }
    }

    @SuppressWarnings("deprecation")
	public static void info(String format, Object... data) {
        FMLLog.log(Level.INFO, "[RTG-INFO] " + format, data);
    }

    @SuppressWarnings("deprecation")
	public static void warn(String format, Object... data) {
        FMLLog.log(Level.WARN, "[RTG-WARN] " + format, data);
    }

    @SuppressWarnings("deprecation")
	public static void error(String format, Object... data) {
        FMLLog.log(Level.ERROR, "[RTG-ERROR] " + format, data);
    }

    @SuppressWarnings("deprecation")
	public static void fatal(String format, Object... data) {
        FMLLog.log(Level.FATAL, "[RTG-FATAL] " + format, data);
    }
}