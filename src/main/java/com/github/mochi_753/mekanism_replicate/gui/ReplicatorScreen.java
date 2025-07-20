package com.github.mochi_753.mekanism_replicate.gui;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ReplicatorScreen extends AbstractContainerScreen<ReplicatorMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MekanismReplicate.MOD_ID, "textures/gui/replicator_gui.png");
    private static final ResourceLocation BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MekanismReplicate.MOD_ID, "textures/gui/replicator_bar.png");

    public ReplicatorScreen(ReplicatorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        minecraft.getTextureManager().bindForSetup(BAR_TEXTURE);
        guiGraphics.blit(BAR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // プログレスバーの幅を計算
        int progressWidth = (int) ((menu.getProgress() / (float) menu.getMaxProgress()) * 30);

        // プログレスバー描画
        guiGraphics.blit(BAR_TEXTURE, leftPos + 45, topPos + 40, 0, 0, progressWidth, 7);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}