package com.bob.redwall.gui.factions;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.bob.redwall.Ref;
import com.bob.redwall.entity.npc.favors.Favor;

import net.minecraft.client.gui.GuiButton;
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
	protected final EntityPlayer player;
	protected final List<Favor> favors;
	protected int selectedFavor = 0;

	public GuiFavor(EntityPlayer player, List<Favor> favors) {
		this.player = player;
		this.favors = favors;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		if (this.favors.size() > 1) {
			this.buttonList.add(new GuiButton(101, this.guiLeft + 3, this.guiTop - 30, 20, 20, "<"));
			this.buttonList.add(new GuiButton(102, this.guiLeft + this.xSize - 17, this.guiTop - 30, 20, 20, ">"));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		GuiInventory.drawEntityOnScreen(i + 40, j + 82, 30, Mouse.getX(), Mouse.getY(), this.favors.get(this.selectedFavor).getGiver());
		this.fontRenderer.drawString(I18n.format("favor.story", this.player.getName()), this.guiLeft + 111 - this.fontRenderer.getStringWidth(I18n.format("favor.story", this.player.getName())) / 2, this.guiTop + 15, 4210752);
		this.drawFittedString(this.favors.get(this.selectedFavor).getStory(), 80, this.guiLeft + 75, this.guiTop + 28);
		this.fontRenderer.drawString(I18n.format("favor.conditions"), this.guiLeft + 200 - this.fontRenderer.getStringWidth(I18n.format("favor.conditions")) / 2, this.guiTop + 15, 4210752);
		String limit = I18n.format("favor.timeLimit", this.favors.get(this.selectedFavor).getTimeLimit() <= 0 ? "None" : formatTime(this.favors.get(this.selectedFavor).getTimeLimit()));
		this.fontRenderer.drawString(limit, this.guiLeft + 128 - this.fontRenderer.getStringWidth(limit) / 2, this.guiTop - 10, 0xFFFFFF);
		for (int i1 = 0; i1 < this.favors.get(this.selectedFavor).getConditions().size(); i1++)
			this.drawFittedString(this.favors.get(this.selectedFavor).getConditions().get(i1).getText(), 80, this.guiLeft + 164, this.guiTop + 28 + i1 * 10);
		this.fontRenderer.drawString(I18n.format("favor.failure"), this.guiLeft + 68 - this.fontRenderer.getStringWidth(I18n.format("favor.failure")) / 2, this.guiTop + 92, 4210752);
		for (int i1 = 0; i1 < this.favors.get(this.selectedFavor).getFailureRewards().size(); i1++)
			this.fontRenderer.drawString(this.favors.get(this.selectedFavor).getFailureRewards().get(i1).getText(), this.guiLeft + 18, this.guiTop + 106 + i1 * 10, 4210752);
		this.fontRenderer.drawString(I18n.format("favor.success"), this.guiLeft + 186 - this.fontRenderer.getStringWidth(I18n.format("favor.success")) / 2, this.guiTop + 92, 4210752);
		for (int i1 = 0; i1 < this.favors.get(this.selectedFavor).getSuccessRewards().size(); i1++)
			this.fontRenderer.drawString(this.favors.get(this.selectedFavor).getSuccessRewards().get(i1).getText(), this.guiLeft + 135, this.guiTop + 106 + i1 * 10, 4210752);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.id == 101) this.selectedFavor = this.selectedFavor + 1 < this.favors.size() ? this.selectedFavor + 1 : 0;
		else if (button.id == 102) this.selectedFavor = this.selectedFavor - 1 >= 0 ? this.selectedFavor - 1 : this.favors.size() - 1;
	}
	
	public void drawFittedString(String string, int width, int x, int y) {
		while (!string.isEmpty()) {
			String s1 = this.fontRenderer.trimStringToWidth(string, width);
			this.fontRenderer.drawString(s1, x, y, 4210752);
			string = string.substring(Math.min(s1.length(), string.length()));
			y += this.fontRenderer.FONT_HEIGHT;
		}
	}

	public static String formatTime(long timeLimit) {
		long seconds = (timeLimit / 20) % 60;
		long minutes = (timeLimit / 120) % 60;
		long hours = timeLimit / 7200;
		return hours > 0 ? I18n.format("time.format.hours", hours, minutes, seconds) : minutes > 0 ? I18n.format("time.format.minutes", minutes, seconds) : I18n.format("time.format.seconds", seconds);
	}
}
