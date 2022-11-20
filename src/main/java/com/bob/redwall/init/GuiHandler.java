package com.bob.redwall.init;

import com.bob.redwall.RedwallUtils;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.npc.EntityAbstractNPC;
import com.bob.redwall.entity.npc.favors.Favor;
import com.bob.redwall.gui.brewing.ContainerBrewingGuosim;
import com.bob.redwall.gui.brewing.ContainerBrewingRedwall;
import com.bob.redwall.gui.brewing.ContainerBrewingVerminMossflower;
import com.bob.redwall.gui.brewing.GuiBrewingGuosim;
import com.bob.redwall.gui.brewing.GuiBrewingRedwall;
import com.bob.redwall.gui.brewing.GuiBrewingVerminMossflower;
import com.bob.redwall.gui.cooking.ContainerCookingGeneric;
import com.bob.redwall.gui.cooking.GuiCookingGeneric;
import com.bob.redwall.gui.factions.GuiFactions;
import com.bob.redwall.gui.factions.GuiFavor;
import com.bob.redwall.gui.factions.GuiFavorAcceptReject;
import com.bob.redwall.gui.skills.GuiSkills;
import com.bob.redwall.gui.smelting.ContainerSmeltery;
import com.bob.redwall.gui.smelting.GuiSmeltery;
import com.bob.redwall.gui.smithing.ContainerSmithingGeneric;
import com.bob.redwall.gui.smithing.GuiSmithingGeneric;
import com.bob.redwall.gui.smithing.redwall.ContainerSmithingRedwall;
import com.bob.redwall.gui.smithing.redwall.GuiSmithingRedwall;
import com.bob.redwall.gui.trading.ContainerTrading;
import com.bob.redwall.gui.trading.GuiTrading;
import com.bob.redwall.tileentity.TileEntityBrewingGuosim;
import com.bob.redwall.tileentity.TileEntityBrewingRedwall;
import com.bob.redwall.tileentity.TileEntityBrewingVerminMossflower;
import com.bob.redwall.tileentity.TileEntityCookingGeneric;
import com.bob.redwall.tileentity.TileEntitySmeltery;
import com.bob.redwall.tileentity.TileEntitySmithingGeneric;
import com.bob.redwall.tileentity.TileEntitySmithingRedwall;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	private static int i = 0;
	public static final int GUI_FACTIONS_ID = i++;
	public static final int GUI_SMITHING_GENERIC_ID = i++;
	public static final int GUI_SMELTERY_ID = i++;
	public static final int GUI_COOKING_GENERIC_ID = i++;
	public static final int GUI_SMITHING_REDWALL_ID = i++;
	public static final int GUI_BREWING_REDWALL_ID = i++;
	public static final int GUI_BREWING_GUOSIM_ID = i++;
	public static final int GUI_BREWING_VERMIN_MOSSFLOWER_ID = i++;
	public static final int GUI_SKILLS_ID = i++;
	public static final int GUI_FAVOR_ID = i++;
	public static final int GUI_FAVOR_ACCEPT_REJECT_ID = i++;
	public static final int GUI_TRADING_ID = i++;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        Entity entity = RedwallUtils.raytrace(player, 100.0F).entityHit;
        
		if(ID == GuiHandler.GUI_FACTIONS_ID) return null;
		else if(ID == GuiHandler.GUI_SMITHING_GENERIC_ID) return new ContainerSmithingGeneric(player.inventory, world, pos, (TileEntitySmithingGeneric)te);
		else if(ID == GuiHandler.GUI_SMELTERY_ID) return new ContainerSmeltery(player.inventory, world, pos, (TileEntitySmeltery)te);
		else if(ID == GuiHandler.GUI_COOKING_GENERIC_ID) return new ContainerCookingGeneric(player.inventory, world, pos, (TileEntityCookingGeneric)te);
		else if(ID == GuiHandler.GUI_SMITHING_REDWALL_ID) return new ContainerSmithingRedwall(player.inventory, world, pos, (TileEntitySmithingRedwall)te);
		else if(ID == GuiHandler.GUI_BREWING_REDWALL_ID) return new ContainerBrewingRedwall(player.inventory, world, pos, (TileEntityBrewingRedwall)te);
		else if(ID == GuiHandler.GUI_BREWING_GUOSIM_ID) return new ContainerBrewingGuosim(player.inventory, world, pos, (TileEntityBrewingGuosim)te);
		else if(ID == GuiHandler.GUI_BREWING_VERMIN_MOSSFLOWER_ID) return new ContainerBrewingVerminMossflower(player.inventory, world, pos, (TileEntityBrewingVerminMossflower)te);
		else if(ID == GuiHandler.GUI_SKILLS_ID) return null;
		else if(ID == GuiHandler.GUI_FAVOR_ID) return null;
		else if(ID == GuiHandler.GUI_FAVOR_ACCEPT_REJECT_ID) return null;
		else if(ID == GuiHandler.GUI_TRADING_ID) return new ContainerTrading(player, entity != null && entity instanceof EntityAbstractNPC ? (EntityAbstractNPC)entity : world.getEntitiesWithinAABB(EntityAbstractNPC.class, new AxisAlignedBB(pos)).get(0));
		else return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        Entity entity = Minecraft.getMinecraft().pointedEntity;
		Favor favor = entity instanceof EntityAbstractNPC ? ((EntityAbstractNPC)entity).getFavor() : null;
		
		if(ID == GuiHandler.GUI_FACTIONS_ID) return new GuiFactions(player);
		else if(ID == GuiHandler.GUI_SMITHING_GENERIC_ID) return new GuiSmithingGeneric(player.inventory, world, pos, (TileEntitySmithingGeneric)te);
		else if(ID == GuiHandler.GUI_SMELTERY_ID) return new GuiSmeltery(player.inventory, world, pos, (TileEntitySmeltery)te);
		else if(ID == GuiHandler.GUI_COOKING_GENERIC_ID) return new GuiCookingGeneric(player.inventory, world, pos, (TileEntityCookingGeneric)te);
		else if(ID == GuiHandler.GUI_SMITHING_REDWALL_ID) return new GuiSmithingRedwall(player.inventory, world, pos, (TileEntitySmithingRedwall)te);
		else if(ID == GuiHandler.GUI_BREWING_REDWALL_ID) return new GuiBrewingRedwall(player.inventory, world, pos, (TileEntityBrewingRedwall)te);
		else if(ID == GuiHandler.GUI_BREWING_GUOSIM_ID) return new GuiBrewingGuosim(player.inventory, world, pos, (TileEntityBrewingGuosim)te);
		else if(ID == GuiHandler.GUI_BREWING_VERMIN_MOSSFLOWER_ID) return new GuiBrewingVerminMossflower(player.inventory, world, pos, (TileEntityBrewingVerminMossflower)te);
		else if(ID == GuiHandler.GUI_SKILLS_ID) return new GuiSkills(player);
		else if(ID == GuiHandler.GUI_FAVOR_ID) return player.getCapability(FactionCapProvider.FACTION_CAP, null).getFavors().isEmpty() ? null : new GuiFavor(player, player.getCapability(FactionCapProvider.FACTION_CAP, null).getFavors());
		else if(ID == GuiHandler.GUI_FAVOR_ACCEPT_REJECT_ID && favor != null) return new GuiFavorAcceptReject(player, favor);
		else if(ID == GuiHandler.GUI_TRADING_ID) return new GuiTrading(new ContainerTrading(player, entity != null && entity instanceof EntityAbstractNPC ? (EntityAbstractNPC)entity : world.getEntitiesWithinAABB(EntityAbstractNPC.class, new AxisAlignedBB(pos)).get(0)));
		else return null;
	}
}
