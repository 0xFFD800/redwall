package com.bob.redwall.common.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.bob.redwall.dimensions.redwall.EnumSeasons;
import com.bob.redwall.RedwallUtils;
import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

public class CommandSeason extends CommandBase {
	public static final List<String> VALID_ARGS = Lists.newArrayList();
	static {
		VALID_ARGS.add("spring_early");
		VALID_ARGS.add("spring");
		VALID_ARGS.add("spring_late");
		VALID_ARGS.add("summer_early");
		VALID_ARGS.add("summer");
		VALID_ARGS.add("summer_late");
		VALID_ARGS.add("autumn_early");
		VALID_ARGS.add("autumn");
		VALID_ARGS.add("autumn_late");
		VALID_ARGS.add("winter_early");
		VALID_ARGS.add("winter");
		VALID_ARGS.add("winter_late");
	}

	@Override
	public String getName() {
		return "season";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.season.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 1 && args[0].length() > 0) {
			if (VALID_ARGS.contains(args[0])) {
				EnumSeasons season = EnumSeasons.SPRING;
				if (args[0].equals("spring_early"))
					season = EnumSeasons.EARLY_SPRING;
				else if (args[0].equals("spring"))
					season = EnumSeasons.SPRING;
				else if (args[0].equals("spring_late"))
					season = EnumSeasons.LATE_SPRING;
				else if (args[0].equals("summer_early"))
					season = EnumSeasons.EARLY_SUMMER;
				else if (args[0].equals("summer"))
					season = EnumSeasons.SUMMER;
				else if (args[0].equals("summer_late"))
					season = EnumSeasons.LATE_SUMMER;
				else if (args[0].equals("autumn_early"))
					season = EnumSeasons.EARLY_FALL;
				else if (args[0].equals("autumn"))
					season = EnumSeasons.FALL;
				else if (args[0].equals("autumn_late"))
					season = EnumSeasons.LATE_FALL;
				else if (args[0].equals("winter_early"))
					season = EnumSeasons.EARLY_WINTER;
				else if (args[0].equals("winter"))
					season = EnumSeasons.WINTER;
				else if (args[0].equals("winter_late"))
					season = EnumSeasons.LATE_WINTER;

				for (WorldServer world : server.worlds)
					RedwallUtils.updateSeason(world, season);
				
				sender.sendMessage(new TextComponentString(I18n.format("commands.season.used", new Object[] { season.toString() })));
			} else {
				throw new WrongUsageException("commands.season.usage", new Object[0]);
			}
		} else {
			throw new WrongUsageException("commands.season.usage", new Object[0]);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return args.length == 1 ? VALID_ARGS : Collections.<String>emptyList();
	}
}
