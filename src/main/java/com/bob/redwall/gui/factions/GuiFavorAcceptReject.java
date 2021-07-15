package com.bob.redwall.gui.factions;

import java.io.IOException;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.capabilities.factions.FactionCapProvider;
import com.bob.redwall.entity.npc.favors.Favor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFavorAcceptReject extends GuiScreen {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Ref.MODID, "textures/gui/favor.png");
	protected int xSize = 256;
	protected int ySize = 166;
	protected int guiLeft;
	protected int guiTop;
	private float oldMouseX;
	private float oldMouseY;
	protected final EntityPlayer player;
	protected final Favor favor;

	public GuiFavorAcceptReject(EntityPlayer player, Favor favor) {
		this.player = player;
		this.favor = favor;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButton(101, this.guiLeft + this.xSize / 2 - 90, this.guiTop + this.ySize + 10, 80, 20, "favor.accept"));
		this.buttonList.add(new GuiButton(102, this.guiLeft + this.xSize / 2 + 10, this.guiTop + this.ySize + 10, 80, 20, "favor.decline"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		GuiInventory.drawEntityOnScreen(i + 40, j + 82, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.favor.getGiver());

		this.drawCenteredString(this.fontRenderer, I18n.format("favor.story"), 111, 20, 4210752);
		this.drawString(this.fontRenderer, this.favor.getStory(), 75, 28, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("favor.conditions"), 200, 20, 4210752);
		this.drawString(this.fontRenderer, I18n.format("favor.timeLimit", this.favor.getTimeLimit() <= 0 ? "None" : formatTime(this.favor.getTimeLimit())), 164, 28, 4210752);
		for (int i1 = 0; i1 < this.favor.getConditions().size(); i1++)
			this.drawString(this.fontRenderer, this.favor.getConditions().get(i1).getText(), 164, 38 + i1 * 10, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("favor.failure"), 83, 97, 4210752);
		for (int i1 = 0; i1 < this.favor.getFailureRewards().size(); i1++)
			this.drawString(this.fontRenderer, this.favor.getFailureRewards().get(i1).getText(), 47, 106 + i1 * 10, 4210752);
		this.drawCenteredString(this.fontRenderer, I18n.format("favor.success"), 171, 97, 4210752);
		for (int i1 = 0; i1 < this.favor.getSuccessRewards().size(); i1++)
			this.drawString(this.fontRenderer, this.favor.getSuccessRewards().get(i1).getText(), 135, 106 + i1 * 10, 4210752);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.id == 101) {
			this.player.getCapability(FactionCapProvider.FACTION_CAP, null).getFavors().add(this.favor);
			this.onGuiClosed();
		} else if (button.id == 102) {
			this.onGuiClosed();
		}
	}

	public static String formatTime(long timeLimit) {
		long seconds = (timeLimit / 20) % 60;
		long minutes = (timeLimit / 120) % 60;
		long hours = timeLimit / 7200;
		return hours > 0 ? I18n.format("time.format.hours", hours, minutes, seconds) : minutes > 0 ? I18n.format("time.format.minutes", minutes, seconds) : I18n.format("time.format.seconds", seconds);
	}
}
