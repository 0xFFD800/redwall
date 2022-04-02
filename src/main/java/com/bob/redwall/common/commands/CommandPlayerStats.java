package com.bob.redwall.common.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.entity.capabilities.agility.Agility;
import com.bob.redwall.entity.capabilities.agility.AgilityProvider;
import com.bob.redwall.entity.capabilities.speed.Speed;
import com.bob.redwall.entity.capabilities.speed.SpeedProvider;
import com.bob.redwall.entity.capabilities.strength.IPlayerStats;
import com.bob.redwall.entity.capabilities.strength.Strength;
import com.bob.redwall.entity.capabilities.strength.StrengthProvider;
import com.bob.redwall.entity.capabilities.vitality.Vitality;
import com.bob.redwall.entity.capabilities.vitality.VitalityProvider;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandPlayerStats extends CommandBase {
	@Override
	public String getName() {
		return "playerstats";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.playerstats.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 2 && args[0].length() > 0 && args[1].length() > 0) {
			EntityPlayer player = getPlayer(server, sender, args[0]);
			if (player != null) {
				String stat = args[1];
				String mode = args[2];
				IPlayerStats stats = stat.equals("strength") ? player.getCapability(StrengthProvider.STRENGTH_CAP, null) : stat.equals("speed") ? player.getCapability(SpeedProvider.SPEED_CAP, null) : stat.equals("vitality") ? player.getCapability(VitalityProvider.VITALITY_CAP, null) : stat.equals("agility") ? player.getCapability(AgilityProvider.AGILITY_CAP, null) : null;
				if (stats != null) {
					if (mode.equals("reset")) {
						stats.set(stat.equals("strength") ? Strength.DEFAULT_STRENGTH : stat.equals("speed") ? Speed.DEFAULT_SPEED : stat.equals("agility") ? Agility.DEFAULT_AGILITY : Vitality.DEFAULT_VITALITY);
						notifyCommandListener(sender, this, "commands.playerstats.reset", new Object[] { args[0], Strength.DEFAULT_STRENGTH });
					} else if (mode.equals("set")) {
						if (args.length >= 4 && args[3].length() > 0) {
							int amount = Integer.valueOf(args[3]);
							stats.set(amount);
							notifyCommandListener(sender, this, "commands.playerstats.set", new Object[] { args[0], args[3] });
						} else {
							throw new WrongUsageException("commands.playerstats.usage", new Object[0]);
						}
					} else if (mode.equals("add")) {
						if (args.length >= 4 && args[3].length() > 0) {
							int amount = Integer.valueOf(args[3]);
							stats.add(amount);
							notifyCommandListener(sender, this, "commands.playerstats.add", new Object[] { args[3], args[0] });
						} else {
							throw new WrongUsageException("commands.playerstats.usage", new Object[0]);
						}
					} else if (mode.equals("subtract")) {
						if (args.length >= 4 && args[3].length() > 0) {
							int amount = Integer.valueOf(args[3]);
							stats.subtract(amount);
							notifyCommandListener(sender, this, "commands.playerstats.subtract", new Object[] { args[3], args[0] });
						} else {
							throw new WrongUsageException("commands.playerstats.usage", new Object[0]);
						}
					} else if (mode.equals("get")) {
						notifyCommandListener(sender, this, "commands.playerstats.get", new Object[] { args[0], stats.get() });
					} else {
						throw new WrongUsageException("commands.playerstats.usage", new Object[0]);
					}
				} else {
					throw new WrongUsageException("commands.playerstats.usage", new Object[0]);
				}
			} else {
				throw new CommandException("commands.playerstats.failed.noplayer", new Object[] { args[0], args[1] });
			}
		} else {
			throw new WrongUsageException("commands.playerstats.usage", new Object[0]);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		List<String> modes = Lists.newArrayList();
		modes.add("reset");
		modes.add("set");
		modes.add("add");
		modes.add("subtract");
		modes.add("get");
		List<String> stats = Lists.newArrayList();
		stats.add("strength");
		stats.add("speed");
		stats.add("vitality");
		stats.add("agility");
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : args.length == 2 ? stats : args.length == 3 ? modes : Collections.<String>emptyList();
	}
}
