package com.bob.redwall.init;

import com.bob.redwall.gui.cooking.ContainerCookingGeneric;
import com.bob.redwall.gui.cooking.GuiCookingGeneric;
import com.bob.redwall.gui.factions.GuiFactions;
import com.bob.redwall.gui.smelting.ContainerSmeltery;
import com.bob.redwall.gui.smelting.GuiSmeltery;
import com.bob.redwall.gui.smithing.ContainerSmithingGeneric;
import com.bob.redwall.gui.smithing.GuiSmithingGeneric;
import com.bob.redwall.tileentity.TileEntityCookingGeneric;
import com.bob.redwall.tileentity.TileEntitySmeltery;
import com.bob.redwall.tileentity.TileEntitySmithingGeneric;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	private static int i = 0;
	public static final int GUI_FACTIONS_ID = i++;
	public static final int GUI_SMITHING_GENERIC_ID = i++;
	public static final int GUI_SMELTERY_ID = i++;
	public static final int GUI_COOKING_GENERIC_ID = i++;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        
		if(ID == GuiHandler.GUI_FACTIONS_ID) return null;
		else if(ID == GuiHandler.GUI_SMITHING_GENERIC_ID) return new ContainerSmithingGeneric(player.inventory, world, pos, (TileEntitySmithingGeneric)te);
		else if(ID == GuiHandler.GUI_SMELTERY_ID) return new ContainerSmeltery(player.inventory, world, pos, (TileEntitySmeltery)te);
		else if(ID == GuiHandler.GUI_COOKING_GENERIC_ID) return new ContainerCookingGeneric(player.inventory, world, pos, (TileEntityCookingGeneric)te);
		else return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
		
		if(ID == GuiHandler.GUI_FACTIONS_ID) return new GuiFactions(player);
		else if(ID == GuiHandler.GUI_SMITHING_GENERIC_ID) return new GuiSmithingGeneric(player.inventory, world, pos, (TileEntitySmithingGeneric)te);
		else if(ID == GuiHandler.GUI_SMELTERY_ID) return new GuiSmeltery(player.inventory, world, pos, (TileEntitySmeltery)te);
		else if(ID == GuiHandler.GUI_COOKING_GENERIC_ID) return new GuiCookingGeneric(player.inventory, world, pos, (TileEntityCookingGeneric)te);
		else return null;
	}
}
