package com.bob.redwall.gui.factions;

import java.util.List;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.npc.favors.Favor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFavor extends GuiScreen {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Ref.MODID, "textures/gui/favor.png");
    protected int xSize = 256;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;
    private float oldMouseX;
    private float oldMouseY;
    protected final EntityPlayer player;
    protected final List<Favor> favors;
    protected int selectedFavor = 0;
    
	public GuiFavor(EntityPlayer player, List<Favor> favors) {
		this.player = player;
		this.favors = favors;
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(i + 40, j + 82, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.favors.get(this.selectedFavor).getGiver());
        
        this.fontRenderer.drawString(I18n.format("favor.story"), 111, 20, 4210752);
        this.fontRenderer.drawString(I18n.format("favor.conditions"), 200, 20, 4210752);
        this.fontRenderer.drawString(I18n.format("favor.failure"), 83, 97, 4210752);
        this.fontRenderer.drawString(I18n.format("favor.success"), 171, 97, 4210752);
    }
}
