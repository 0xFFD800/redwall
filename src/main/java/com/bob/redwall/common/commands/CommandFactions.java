package com.bob.redwall.common.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.Faction;
import com.bob.redwall.entity.capabilities.factions.FactionCap;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.capabilities.factions.IFactionCap;
import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandFactions extends CommandBase {
	public static final List<String> VALID_ARGS = Lists.newArrayList();
	static {
		VALID_ARGS.add("loyalty");
		VALID_ARGS.add("fightSkill");
		VALID_ARGS.add("smithSkill");
		VALID_ARGS.add("farmSkill");
		VALID_ARGS.add("brewSkill");
		VALID_ARGS.add("cookSkill");
		VALID_ARGS.add("scoffSkill");
	}

	@Override
	public String getName() {
		return "factions";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.factions.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 4 && args[0].length() > 0) {
			EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
			Faction fac = Faction.getFactionByID(args[1]);
			IFactionCap cap = entityplayer.getCapability(FactionCapProvider.FACTION_CAP, null);
			if (fac != null) {
				if (args[2].equals("contact")) {
					if (args[3].equals("true"))
						fac.playerContactFaction(entityplayer);
					else if (args[3].equals("false"))
						fac.playerLeave(entityplayer);
					else if (args[3].equals("get"))
						sender.sendMessage(new TextComponentString(I18n.format("commands.factions.getContact", entityplayer.getName(), fac.getID(), cap.getPlayerContacted(fac))));
					else
						throw new WrongUsageException("commands.factions.usage", new Object[0]);
				} else if (args[2].equals("stats") && args.length >= 5) {
					if (VALID_ARGS.contains(args[3])) {
						FactionCap.FacStatType type = FactionCap.FacStatType.LOYALTY;
						switch (args[3]) {
						case "fightSkill":
							type = FactionCap.FacStatType.FIGHT;
							break;
						case "smithSkill":
							type = FactionCap.FacStatType.SMITH;
							break;
						case "farmSkill":
							type = FactionCap.FacStatType.FARM;
							break;
						case "brewSkill":
							type = FactionCap.FacStatType.BREW;
							break;
						case "cookSkill":
							type = FactionCap.FacStatType.COOK;
							break;
						case "scoffSkill":
							type = FactionCap.FacStatType.SCOFF;
							break;
						case "loyalty":
						default:
							break;
						}

						switch (args[4]) {
						case "set":
							cap.set(fac, type, Float.valueOf(args[5]), true);
							break;
						case "get":
							sender.sendMessage(new TextComponentString(I18n.format("commands.factions.get", entityplayer.getName(), args[3], fac.getID(), cap.get(fac, type))));
							break;
						case "add":
							cap.set(fac, type, cap.get(fac, type) + Float.valueOf(args[5]), true);
							break;
						case "subtract":
							cap.set(fac, type, cap.get(fac, type) - Float.valueOf(args[5]), true);
							break;
						case "reset":
							cap.set(fac, type, 0, true);
							break;
						default:
							throw new WrongUsageException("commands.factions.usage", new Object[0]);
						}
					} else {
						throw new WrongUsageException("commands.factions.usage", new Object[0]);
					}
				} else {
					throw new WrongUsageException("commands.factions.usage", new Object[0]);
				}

				if (entityplayer instanceof EntityPlayerMP)
					RedwallUtils.updatePlayerFactionStats((EntityPlayerMP) entityplayer);
				sender.sendMessage(new TextComponentString(I18n.format("commands.factions.used", entityplayer.getName())));
			} else {
				throw new WrongUsageException("commands.factions.usage", new Object[0]);
			}
		} else {
			throw new WrongUsageException("commands.factions.usage", new Object[0]);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Collections.<String>emptyList();
	}
}
