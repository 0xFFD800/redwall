package com.bob.redwall.common.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.bob.redwall.entity.capabilities.species.Species;
import com.bob.redwall.entity.capabilities.species.SpeciesCapProvider;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandSpecies extends CommandBase {
	@Override
	public String getName() {
		return "species";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.species.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 1 && args[0].length() > 0) {
			EntityPlayer player = getPlayer(server, sender, args[0]);
			if (player != null) {
				if (args.length >= 2 && args[1].length() > 0) {
					String s = args[1];
					Species species = Species.getByID(s);
					if (species == null)
						throw new CommandException("commands.species.failed.nospecies", new Object[] { args [1] });
					else if (species == player.getCapability(SpeciesCapProvider.SPECIES_CAP, null).get())
						sender.sendMessage(new TextComponentString(I18n.format("commands.species.failed.nochange", new Object[] { args[0], args[1] })));
					else {
						player.getCapability(SpeciesCapProvider.SPECIES_CAP, null).set(species);
						sender.sendMessage(new TextComponentString(I18n.format("commands.species.used", new Object[] { args[0], args[1] })));
					} 
				} else
					sender.sendMessage(new TextComponentString(I18n.format("commands.species.get", new Object[] { args[0], player.getCapability(SpeciesCapProvider.SPECIES_CAP, null).get().getID() })));
			} else
				throw new CommandException("commands.species.failed.noplayer", new Object[] { args[0] });
		} else
			throw new WrongUsageException("commands.species.usage", new Object[0]);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		Set<String> ids = Species.getIDs();
		List<String> l = new ArrayList<>();
		l.addAll(ids);
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : args.length == 2 ? l : Collections.<String>emptyList();
	}
}
