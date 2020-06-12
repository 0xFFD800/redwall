package com.bob.redwall.factions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public abstract class Faction {
	private static final Map<String, Faction> FACTION_BY_ID = new HashMap<>();
	private final String id;
	private boolean alwaysHasContact = false;
	private boolean isVermin;
	private boolean hidden;
	
	public Faction(String id, boolean isVermin) {
		this.id = id;
		this.isVermin = isVermin;
		Faction.FACTION_BY_ID.put(id, this);
	}
	
	public Faction(String id, boolean isVermin, boolean alwaysHasContact) {
		this(id, isVermin);
		this.alwaysHasContact = alwaysHasContact;
	}
	
	/**
	 * For creating hidden factions only.
	 * @param id The id of the faction to be hidden.
	 */
	public Faction(String id) {
		this(id, false);
		this.hidden = true;
	}
	
	public String getLocalizedName() {
		return I18n.format("fac." + this.id + ".name");
	}
	
	public String getID() {
		return this.id;
	}
	
	public void playerContactFaction(EntityPlayer player) {
		IFactionCap cap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
		if(!this.hidden) cap.setPlayerContacted(this, true);
	}
	
	public void playerLeave(EntityPlayer player) {
		IFactionCap cap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
		if(!this.hidden) cap.setPlayerContacted(this, false);
	}
	
	public boolean playerHasContact(EntityPlayer player) {
		IFactionCap cap = player.getCapability(FactionCapProvider.FACTION_CAP, null);
		return !this.hidden && (this.alwaysHasContact || cap.getPlayerContacted(this));
	}
	
	public abstract FactionStatus getFactionStatus(Faction faction);
	
	public static Faction getFactionByID(String id) {
		if(Faction.FACTION_BY_ID.containsKey(id)) return Faction.FACTION_BY_ID.get(id);
		Ref.LOGGER.warn("WARNING: Tried to get unknown faction " + id + "!");
		return null;
	}
	
	public static Collection<Faction> getAllFactions() {
		return Faction.FACTION_BY_ID.values();
	}
	
	public static class FacList {
		public static final Faction GENERIC = new Faction("generic") {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				return FactionStatus.NEUTRAL;
			}
		};
		public static final Faction REDWALL = new Faction("redwall", false, true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == SALAMANDASTRON || faction == GUOSIM || faction == OTTERS_MOSSFLOWER || faction == WOODLANDERS || faction == RUDDARING) return FactionStatus.ALLIED;
				else if(faction == PEACE_ISLE || faction == NOONVALE || faction == NORTHLANDS) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction SALAMANDASTRON = new Faction("salamandastron", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == GUOSIM || faction == OTTERS_MOSSFLOWER || faction == ROGUE_CREW) return FactionStatus.ALLIED;
				else if(faction == NORTHLANDS || faction == GUORAF || faction == WOODLANDERS || faction == OTTERS_GREEN_ISLE || faction == RUDDARING) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction SOUTHSWARD = new Faction("southsward", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == GUORAF || faction == GUOSSSOM || faction == OTTERS_MOSSFLOWER) return FactionStatus.ALLIED;
				else if(faction == REDWALL ||  faction == SALAMANDASTRON) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction GUOSIM = new Faction("guosim", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == REDWALL || faction == SALAMANDASTRON || faction == WOODLANDERS || faction == GUORAF || faction == GUOSSSOM || faction == OTTERS_MOSSFLOWER) return FactionStatus.ALLIED;
				else if(faction == ROGUE_CREW || faction == NORTHLANDS) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction GUOSSSOM = new Faction("guosssom", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == SALAMANDASTRON || faction == WOODLANDERS || faction == GUORAF || faction == GUOSIM || faction == OTTERS_MOSSFLOWER) return FactionStatus.ALLIED;
				else if(faction == ROGUE_CREW || faction == NORTHLANDS || faction == REDWALL) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction GUORAF = new Faction("guoraf", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == WOODLANDERS || faction == GUOSIM || faction == SOUTHSWARD || faction == GUOSSSOM || faction == OTTERS_MOSSFLOWER) return FactionStatus.ALLIED;
				else if(faction == ROGUE_CREW || faction == NORTHLANDS || faction == REDWALL || faction == WOODLANDERS) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction OTTERS_MOSSFLOWER = new Faction("mossflowerOtters", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == REDWALL || faction == SALAMANDASTRON || faction == WOODLANDERS || faction == GUOSIM || faction == GUORAF || faction == GUOSSSOM || faction == OTTERS_GREEN_ISLE || faction == RUDDARING || faction == ROGUE_CREW) return FactionStatus.ALLIED;
				else if(faction == NORTHLANDS || faction == PEACE_ISLE) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction OTTERS_GREEN_ISLE = new Faction("greenIsleOtters", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == ROGUE_CREW || faction == OTTERS_MOSSFLOWER || faction == RUDDARING) return FactionStatus.ALLIED;
				else if(faction == REDWALL || faction == SALAMANDASTRON) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction RUDDARING = new Faction("ruddaring", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == ROGUE_CREW || faction == OTTERS_MOSSFLOWER || faction == OTTERS_GREEN_ISLE) return FactionStatus.ALLIED;
				else if(faction == REDWALL || faction == SALAMANDASTRON) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction WOODLANDERS = new Faction("mossflowerWoodlanders", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == REDWALL || faction == SALAMANDASTRON || faction == OTTERS_MOSSFLOWER || faction == GUOSIM || faction == GUORAF || faction == GUOSSSOM) return FactionStatus.ALLIED;
				else if(faction == SOUTHSWARD) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction NOONVALE = new Faction("noonvale", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == REDWALL || faction == NORTHLANDS || faction == NOONVALE) return FactionStatus.FRIENDLY;
				else if(faction == ROGUE_CREW || faction == SALAMANDASTRON || faction == GUORAF) return FactionStatus.SUSPICIOUS;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction ROGUE_CREW = new Faction("rogueCrew", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == SALAMANDASTRON || faction == OTTERS_MOSSFLOWER || faction == OTTERS_GREEN_ISLE || faction == GUORAF || faction == NORTHLANDS) return FactionStatus.ALLIED;
				else if(faction == REDWALL || faction == GUOSIM) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction PEACE_ISLE = new Faction("peaceIsle", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == REDWALL || faction == NORTHLANDS || faction == NOONVALE) return FactionStatus.FRIENDLY;
				else if(faction == ROGUE_CREW || faction == SALAMANDASTRON || faction == GUORAF) return FactionStatus.SUSPICIOUS;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction NORTHLANDS = new Faction("northlands", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(faction == SALAMANDASTRON || faction == OTTERS_MOSSFLOWER || faction == OTTERS_GREEN_ISLE || faction == GUORAF || faction == NORTHLANDS) return FactionStatus.ALLIED;
				else if(faction == REDWALL || faction == GUOSIM) return FactionStatus.FRIENDLY;
				else if(!faction.isVermin) return FactionStatus.NEUTRAL;
				else return FactionStatus.HOSTILE;
			}
		};
		public static final Faction VERMIN_MOSSFLOWER = new Faction("mossflowerVermin", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == MALKARISS || faction == MARL) return FactionStatus.ALLIED;
				else if(faction == BROWNRATS || faction == RAPSCALLIONS || faction == JUSKA) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction VERMIN_NORTHLANDS = new Faction("northlandsVermin", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == CORSAIRS || faction == SEARATS || faction == RIFTGARD) return FactionStatus.ALLIED;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction SEARATS = new Faction("searats", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == CORSAIRS || faction == RIFTGARD || faction == VERMIN_NORTHLANDS || faction == RAPSCALLIONS) return FactionStatus.ALLIED;
				else if(faction == IRGASH || faction == SAMPETRA || faction == RAVAGERS || faction == JUSKA) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction CORSAIRS = new Faction("corsairs", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == SEARATS || faction == SAMPETRA || faction == RIFTGARD || faction == RAPSCALLIONS) return FactionStatus.ALLIED;
				else if(faction == IRGASH || faction == RAVAGERS || faction == JUSKA) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction SAMPETRA = new Faction("sampetra", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == SEARATS || faction == CORSAIRS) return FactionStatus.ALLIED;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction RIFTGARD = new Faction("riftgard", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == CORSAIRS || faction == SEARATS || faction == VERMIN_NORTHLANDS) return FactionStatus.ALLIED;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction IRGASH = new Faction("irgash", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == RAPSCALLIONS) return FactionStatus.ALLIED;
				else if(faction == CORSAIRS || faction == SEARATS || faction == RIFTGARD) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction CATS_GREEN_ISLE = new Faction("greenIsleCats", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == CORSAIRS || faction == SEARATS) return FactionStatus.ALLIED;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction MARL = new Faction("marl", false) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == VERMIN_MOSSFLOWER || faction == MALKARISS) return FactionStatus.ALLIED;
				else if(faction == RAPSCALLIONS || faction == RAVAGERS) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction ICE_AND_SNOW = new Faction("iceAndSnow", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				return FactionStatus.HOSTILE;
			}
		};
		public static final Faction RAPSCALLIONS = new Faction("rapscallions", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == SEARATS || faction == CORSAIRS || faction == IRGASH) return FactionStatus.ALLIED;
				else if(faction == VERMIN_MOSSFLOWER || faction == RAVAGERS) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction MALKARISS = new Faction("malkariss", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == VERMIN_MOSSFLOWER || faction == MARL) return FactionStatus.ALLIED;
				else if(faction == RAVAGERS || faction == RAPSCALLIONS) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction DOOMWYTE = new Faction("doomwyte", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				return FactionStatus.HOSTILE;
			}
		};
		public static final Faction PAINTED_ONES = new Faction("paintedOnes", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				return FactionStatus.HOSTILE;
			}
		};
		public static final Faction JUSKA = new Faction("juska", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == RAVAGERS) return FactionStatus.ALLIED;
				else if(faction == VERMIN_MOSSFLOWER || faction == RAPSCALLIONS || faction == CORSAIRS || faction == SEARATS) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction FLITCHAYE = new Faction("flitchaye", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				return FactionStatus.HOSTILE;
			}
		};
		public static final Faction RAVAGERS = new Faction("ravagers", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == JUSKA) return FactionStatus.ALLIED;
				else if(faction == SEARATS || faction == CORSAIRS || faction == MARL || faction == RAPSCALLIONS || faction == MALKARISS) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		public static final Faction BROWNRATS = new Faction("brownrats", true) {
			@Override
			public FactionStatus getFactionStatus(Faction faction) {
				if(!faction.isVermin) return FactionStatus.HOSTILE;
				else if(faction == VERMIN_MOSSFLOWER) return FactionStatus.FRIENDLY;
				else return FactionStatus.SUSPICIOUS;
			}
		};
		
		public static void init() {
			Ref.LOGGER.info("Registering Factions");
		}
	}
	
	public static enum FactionStatus {
		ALLIED,
		FRIENDLY,
		NEUTRAL,
		SUSPICIOUS,
		HOSTILE;
	}
}
