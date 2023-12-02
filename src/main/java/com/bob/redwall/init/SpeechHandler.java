package com.bob.redwall.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.bob.redwall.Ref;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpeechHandler {
	public static final List<String> GENERIC_FRIENDLY = Lists.newArrayList();
	public static final List<String> GENERIC_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> GENERIC_HOSTILE = Lists.newArrayList();
	public static final List<String> GENERIC_NAMES_M = Lists.newArrayList();
	public static final List<String> GENERIC_NAMES_F = Lists.newArrayList();

	public static final List<String> COLLECT_METALS_EVIL = Lists.newArrayList();
	public static final List<String> COLLECT_METALS_GOOD = Lists.newArrayList();

	public static final List<String> REDWALL_MOUSE_FRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_MOUSE_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_MOUSE_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_MOUSE_M = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_MOUSE_F = Lists.newArrayList();

	public static final List<String> REDWALL_SQUIRREL_FRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_SQUIRREL_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_SQUIRREL_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_SQUIRREL_M = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_SQUIRREL_F = Lists.newArrayList();

	public static final List<String> REDWALL_MOLE_FRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_MOLE_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_MOLE_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_MOLE_M = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_MOLE_F = Lists.newArrayList();

	public static final List<String> WOODLANDER_MOUSE_FRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_MOUSE_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_MOUSE_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_MOUSE_M = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_MOUSE_F = Lists.newArrayList();

	public static final List<String> WOODLANDER_SQUIRREL_FRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_SQUIRREL_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_SQUIRREL_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_SQUIRREL_M = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_SQUIRREL_F = Lists.newArrayList();

	public static final List<String> WOODLANDER_MOLE_FRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_MOLE_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_MOLE_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_MOLE_M = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_MOLE_F = Lists.newArrayList();

	public static final List<String> MOSSFLOWER_OTTER_FRIENDLY = Lists.newArrayList();
	public static final List<String> MOSSFLOWER_OTTER_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> MOSSFLOWER_OTTER_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_MOSSFLOWER_OTTER_M = Lists.newArrayList();
	public static final List<String> NAMES_MOSSFLOWER_OTTER_F = Lists.newArrayList();

	public static final List<String> WOODLANDER_HARE_FRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_HARE_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> WOODLANDER_HARE_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_HARE_M = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_HARE_F = Lists.newArrayList();
	public static final List<String> NAMES_WOODLANDER_HARE_LAST = Lists.newArrayList();

	public static final List<String> MOSSFLOWER_RAT_FRIENDLY = Lists.newArrayList();
	public static final List<String> MOSSFLOWER_RAT_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> MOSSFLOWER_RAT_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_MOSSFLOWER_RAT_M = Lists.newArrayList();
	public static final List<String> NAMES_MOSSFLOWER_RAT_F = Lists.newArrayList();
	public static final List<String> NAMES_MOSSFLOWER_FERRET_M = Lists.newArrayList();
	public static final List<String> NAMES_MOSSFLOWER_FERRET_F = Lists.newArrayList();

	public static final List<String> GUOSIM_SHREW_FRIENDLY = Lists.newArrayList();
	public static final List<String> GUOSIM_SHREW_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> GUOSIM_SHREW_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_GUOSIM_SHREW_M = Lists.newArrayList();
	public static final List<String> NAMES_GUOSIM_SHREW_F = Lists.newArrayList();

	public static final List<String> REDWALL_SHREW_FRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_SHREW_UNFRIENDLY = Lists.newArrayList();
	public static final List<String> REDWALL_SHREW_HOSTILE = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_SHREW_M = Lists.newArrayList();
	public static final List<String> NAMES_REDWALL_SHREW_F = Lists.newArrayList();

	public static void init() {
		String path = "speech/" + Minecraft.getMinecraft().gameSettings.language;
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/generic_friendly.speechbank"), GENERIC_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/generic_unfriendly.speechbank"), GENERIC_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/generic_hostile.speechbank"), GENERIC_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/names_m.speechbank"), GENERIC_NAMES_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/names_f.speechbank"), GENERIC_NAMES_F);
		
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/collect_metals_evil.speechbank"), COLLECT_METALS_EVIL);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/generic/collect_metals_good.speechbank"), COLLECT_METALS_GOOD);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mouse/friendly.speechbank"), REDWALL_MOUSE_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mouse/unfriendly.speechbank"), REDWALL_MOUSE_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mouse/hostile.speechbank"), REDWALL_MOUSE_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mouse/names_m.speechbank"), NAMES_REDWALL_MOUSE_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mouse/names_f.speechbank"), NAMES_REDWALL_MOUSE_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/squirrel/friendly.speechbank"), REDWALL_SQUIRREL_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/squirrel/unfriendly.speechbank"), REDWALL_SQUIRREL_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/squirrel/hostile.speechbank"), REDWALL_SQUIRREL_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/squirrel/names_m.speechbank"), NAMES_REDWALL_SQUIRREL_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/squirrel/names_f.speechbank"), NAMES_REDWALL_SQUIRREL_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mole/friendly.speechbank"), REDWALL_MOLE_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mole/unfriendly.speechbank"), REDWALL_MOLE_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mole/hostile.speechbank"), REDWALL_MOLE_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mole/names_m.speechbank"), NAMES_REDWALL_MOLE_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/mole/names_f.speechbank"), NAMES_REDWALL_MOLE_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mouse/friendly.speechbank"), WOODLANDER_MOUSE_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mouse/unfriendly.speechbank"), WOODLANDER_MOUSE_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mouse/hostile.speechbank"), WOODLANDER_MOUSE_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mouse/names_m.speechbank"), NAMES_WOODLANDER_MOUSE_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mouse/names_f.speechbank"), NAMES_WOODLANDER_MOUSE_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/squirrel/friendly.speechbank"), WOODLANDER_SQUIRREL_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/squirrel/unfriendly.speechbank"), WOODLANDER_SQUIRREL_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/squirrel/hostile.speechbank"), WOODLANDER_SQUIRREL_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/squirrel/names_m.speechbank"), NAMES_WOODLANDER_SQUIRREL_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/squirrel/names_f.speechbank"), NAMES_WOODLANDER_SQUIRREL_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mole/friendly.speechbank"), WOODLANDER_MOLE_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mole/unfriendly.speechbank"), WOODLANDER_MOLE_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mole/hostile.speechbank"), WOODLANDER_MOLE_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mole/names_m.speechbank"), NAMES_WOODLANDER_MOLE_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/mole/names_f.speechbank"), NAMES_WOODLANDER_MOLE_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/hare/friendly.speechbank"), WOODLANDER_HARE_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/hare/unfriendly.speechbank"), WOODLANDER_HARE_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/hare/hostile.speechbank"), WOODLANDER_HARE_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/hare/names_m.speechbank"), NAMES_WOODLANDER_HARE_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/hare/names_f.speechbank"), NAMES_WOODLANDER_HARE_F);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/woodlander/hare/names_last.speechbank"), NAMES_WOODLANDER_HARE_LAST);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_otters/otter/friendly.speechbank"), MOSSFLOWER_OTTER_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_otters/otter/unfriendly.speechbank"), MOSSFLOWER_OTTER_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_otters/otter/hostile.speechbank"), MOSSFLOWER_OTTER_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_otters/otter/names_m.speechbank"), NAMES_MOSSFLOWER_OTTER_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_otters/otter/names_f.speechbank"), NAMES_MOSSFLOWER_OTTER_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/rat/friendly.speechbank"), MOSSFLOWER_RAT_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/rat/unfriendly.speechbank"), MOSSFLOWER_RAT_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/rat/hostile.speechbank"), MOSSFLOWER_RAT_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/rat/names_m.speechbank"), NAMES_MOSSFLOWER_RAT_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/rat/names_f.speechbank"), NAMES_MOSSFLOWER_RAT_F);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/ferret/names_m.speechbank"), NAMES_MOSSFLOWER_FERRET_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/mossflower_vermin/ferret/names_f.speechbank"), NAMES_MOSSFLOWER_FERRET_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/guosim/shrew/friendly.speechbank"), GUOSIM_SHREW_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/guosim/shrew/unfriendly.speechbank"), GUOSIM_SHREW_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/guosim/shrew/hostile.speechbank"), GUOSIM_SHREW_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/guosim/shrew/names_m.speechbank"), NAMES_GUOSIM_SHREW_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/guosim/shrew/names_f.speechbank"), NAMES_GUOSIM_SHREW_F);

		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/shrew/friendly.speechbank"), REDWALL_SHREW_FRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/shrew/unfriendly.speechbank"), REDWALL_SHREW_UNFRIENDLY);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/shrew/hostile.speechbank"), REDWALL_SHREW_HOSTILE);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/shrew/names_m.speechbank"), NAMES_REDWALL_SHREW_M);
		initializeSpeechbank(new ResourceLocation(Ref.MODID, path + "/redwall/shrew/names_f.speechbank"), NAMES_REDWALL_SHREW_F);
	}

	public static void initializeSpeechbank(ResourceLocation resourceLoc, List<String> speechbank) {
		try {
			BufferedReader file = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(resourceLoc).getInputStream()));
			String line = file.readLine();
			while (!StringUtils.isEmpty(line)) {
				if (line.startsWith("#")) {
					line = file.readLine();
					continue;
				}

				speechbank.add(line);
				line = file.readLine();
			}
		} catch (IOException e) {
			Logger.getLogger(SpeechHandler.class.getName()).log(Level.SEVERE, "Error initializing NPC Speechbank!", e);
		}
	}
}
