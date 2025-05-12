package net.dewteereeum.functionalfish.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FishtankScreen extends AbstractContainerScreen<FishtankMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(FunctionalFishMod.MOD_ID, "textures/gui/fishtank/fishtank_gui.png");

    public FishtankScreen(FishtankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        //this.inventoryLabelY = 10000;
        //this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(pGuiGraphics, x, y);
    }



    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(GUI_TEXTURE, x + 110, y + 56 - menu.getScaledProgress(), 176, 32 - menu.getScaledProgress(), 10, 32);
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int MouseX, int MouseY, float delta) {
        renderBackground(guiGraphics, MouseX, MouseY, delta);
        super.render(guiGraphics, MouseX, MouseY, delta);
        renderTooltip(guiGraphics, MouseX, MouseY);
    }

}
